package weather_network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.igorr.weatheroutlook.Preferences;

import weather_data.CurrentWeatherSchema;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchingCurrentWeather extends WeatherFetcher<CurrentWeatherSchema> {
    private Context context;
    public FetchingCurrentWeather(Context context) {
        super(Preferences.getPreferableCityStr(context));
        this.context = context;
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
                    LoaderLiveData.getInstance(context).showDataAndInsertInDB(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<CurrentWeatherSchema> call, @NonNull Throwable t) {
                Log.i("lastData", "ERROR: " + t.getMessage());
            }
        };
    }
}