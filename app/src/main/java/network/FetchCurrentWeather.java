package network;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.igorr.weatheroutlook.FragmentCurrentWeather;

import model.CurrentWeatherSchema;
import presenters.CurrentPresenter;
import presenters.PresentData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchCurrentWeather extends WeatherFetcher<CurrentWeatherSchema> {
    private PresentData<CurrentWeatherSchema> presentData;
    private FragmentCurrentWeather uiContainer;

    public FetchCurrentWeather(Fragment uiContainer) {
        super(uiContainer.getContext());
        this.uiContainer = (FragmentCurrentWeather) uiContainer;
        presentData = new CurrentPresenter(uiContainer.getView());
    }

    @Override
    String setRequestType() {
        return TYPE_CURRENT;
    }

    @Override
    Callback<CurrentWeatherSchema> setRetrofitCallback() {
        return new Callback<CurrentWeatherSchema>() {
            @Override
            public void onResponse(@NonNull Call<CurrentWeatherSchema> call, @NonNull Response<CurrentWeatherSchema> response) {
                if (response.isSuccessful()) {
                    presentData.fillData(response.body(), uiContainer);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentWeatherSchema> call, @NonNull Throwable t) {
                Log.i("GET_CURRENT", "ERROR: " + t.getMessage());

            }
        };
    }
}