package model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import network.FetchingCurrentWeather;
import network.LoaderLiveData;

public class CurrentWeatherViewModel extends AndroidViewModel {

    public CurrentWeatherViewModel(@NonNull Application application) {
        super(application);
    }

    public LoaderLiveData getLoaderLiveData() {
        return LoaderLiveData.getInstance(getApplication());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
