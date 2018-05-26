package network;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

public class NetworkStateData extends LiveData<ConnectivityManager> {
    private ConnectivityManager connectManager;
    private ConnectivityManager.OnNetworkActiveListener networkActiveListener = new ConnectivityManager.OnNetworkActiveListener() {
        @Override
        public void onNetworkActive() {
            if (connectManager.getActiveNetworkInfo() != null && connectManager.getActiveNetworkInfo().isConnected()) {


            }
        }
    };

    LocationManager lm;
    public NetworkStateData(Context context) {
        connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    protected void onActive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectManager.addDefaultNetworkActiveListener(networkActiveListener);
        }


    }

    @Override
    protected void onInactive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectManager.removeDefaultNetworkActiveListener(networkActiveListener);
        }
    }
/*    @Override
    public void run() {
        while (!isInterrupted()) {
            if (connectManager.getActiveNetworkInfo() == null) {
                while (true) {
                    try {
                        sleep(2000);
                        Log.d("SENTINEL", "НЕТ СЕТИ");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (connectManager.getActiveNetworkInfo() != null) {
                        List<Fragment> fragmentList = activity.getSupportFragmentManager().getFragments();
                        Log.d("SENTINEL", String.format("ЕСТЬ СЕТЬ, фрагментов: %s", fragmentList.size()));
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                ((MainActionListener) activity).updateUI();
                            }
                        });
                        break;
                    }
                }
            }
        }
        if (isInterrupted())
            Log.d("SENTINEL", "поток прерван?");
    }*/
}