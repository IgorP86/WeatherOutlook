package model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import network.NetworkPermitLiveData;

public class NetworkViewModel extends AndroidViewModel {
    public NetworkViewModel(@NonNull Application application) {
        super(application);
    }

    public NetworkPermitLiveData getPermitLiveData() {
        return NetworkPermitLiveData.getInstance(getApplication());
    }
}
