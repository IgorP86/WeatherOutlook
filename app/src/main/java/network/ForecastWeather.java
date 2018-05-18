package network;

import android.support.annotation.NonNull;

import model.ForecastResponseSchema;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastWeather extends WeatherFetcher<ForecastResponseSchema> {

    public ForecastWeather(String cityID) {
        super(cityID);
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
                responseSchema = response.body();
            }

            @Override
            public void onFailure(@NonNull Call<ForecastResponseSchema> call, @NonNull Throwable t) {

            }
        };
    }
}
