package network;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;

public class NetworkPermitLiveData extends LiveData<Boolean> {
    private ConnectivityManager connectivityManager;
    private static NetworkPermitLiveData singleton;

    public static NetworkPermitLiveData getInstance(Context context){
        if(singleton==null){
            return new NetworkPermitLiveData(context);
        }
        return singleton;
    }

    //Листнер, если вдруг появится сеть (появляется даже тогда, когда вроде бы и не пропадала)
    private NetworkPermitLiveData(Context context) {
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.addDefaultNetworkActiveListener(new ConnectivityManager.OnNetworkActiveListener() {
                @Override
                public void onNetworkActive() {
                    postValue(true);
                    Log.d("NETWORK", "сеть появилась");
                }
            });
        }
    }
}
