package network;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.igorr.weatheroutlook.FragmentForecastOnFewDays;

import model.ForecastResponseSchema;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastWeather extends WeatherFetcher<ForecastResponseSchema> {

    private FragmentForecastOnFewDays uiFragment;

    public ForecastWeather(String cityID, Fragment uiFragment) {
        super(cityID);
        this.uiFragment = (FragmentForecastOnFewDays) uiFragment;
    }

    @Override
    String setRequestType() {
        return TYPE_FORECAST;
    }

    @Override
    Callback<ForecastResponseSchema> setRetrofitCallback() {
        return new Callback<ForecastResponseSchema>() {
            @Override
            public void onResponse(@NonNull Call<ForecastResponseSchema> call, @NonNull Response<ForecastResponseSchema> response) {
                if (response.isSuccessful())
                    uiFragment.setupAdapter(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ForecastResponseSchema> call, @NonNull Throwable t) {

            }
        };
    }
}
