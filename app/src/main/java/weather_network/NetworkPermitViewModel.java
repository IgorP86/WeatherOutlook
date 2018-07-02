package weather_network;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

public class NetworkPermitViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> permitLiveData;
    private ConnectivityManager connectivityManager;

    public NetworkPermitViewModel(@NonNull Application application) {
        super(application);
        permitLiveData = new MutableLiveData<>();
        connectivityManager = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        //Фильтры получателя
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //Регистрирую получателя
        getApplication().registerReceiver(new NetworkStateChangeReceiver(), intentFilter);
    }

    public MutableLiveData<Boolean> getPermitLiveData() {
        return permitLiveData;
    }

    class NetworkStateChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE)
                permitLiveData.setValue(true);
        }
    }
}

