package weather_data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import weather_network.LoaderLiveData;

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
