package network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.igorr.weatheroutlook.MainActionListener;
import com.igorr.weatheroutlook.Updatable;

import java.util.List;

import static java.lang.Thread.sleep;

public class SentinelConnection extends Thread {
    private AppCompatActivity activity;
    private ConnectivityManager connectManager;

    public SentinelConnection(AppCompatActivity appCompatActivity) {
        this.activity = appCompatActivity;
        connectManager = (ConnectivityManager) appCompatActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        activity.getSupportFragmentManager();
    }

    @Override
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
                        ((MainActionListener)activity).updateUI();
                        break;
                    }
                }
            }
        }
        if (isInterrupted())
            Log.d("SENTINEL", "поток прерван?");
    }
}