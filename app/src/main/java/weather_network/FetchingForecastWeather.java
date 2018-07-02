package weather_network;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.igorr.weatheroutlook.FragmentForecastOnFewDays;
import com.igorr.weatheroutlook.Preferences;
import com.igorr.weatheroutlook.adapters.RecyclerForecastAdapter;

import weather_data.ForecastResponseSchema;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchingForecastWeather extends WeatherFetcher<ForecastResponseSchema> {
    private FragmentForecastOnFewDays uiFragment;

    public FetchingForecastWeather(Fragment uiFragment) {
        super(Preferences.getPreferableCityStr(uiFragment.getContext()));
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
                    ((RecyclerForecastAdapter) uiFragment.getRecyclerView().getAdapter()).updateSchema(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ForecastResponseSchema> call, @NonNull Throwable t) {
            }
        };
    }
}