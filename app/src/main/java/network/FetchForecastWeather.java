package network;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.igorr.weatheroutlook.FragmentForecastOnFewDays;

import model.ForecastResponseSchema;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchForecastWeather extends WeatherFetcher<ForecastResponseSchema> {
    private FragmentForecastOnFewDays uiFragment;

    public FetchForecastWeather(Fragment uiFragment) {
        super(uiFragment.getContext());
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
                Log.i("GET FORECAST", "ERROR: " + t.getMessage());
            }
        };
    }
}