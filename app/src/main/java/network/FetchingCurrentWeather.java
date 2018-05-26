package network;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.igorr.weatheroutlook.FragmentCurrentWeather;

import DBWeather.WeatherDB;
import model.CurrentWeatherSchema;
import presenters.CurrentPresenter;
import presenters.PresentData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchingCurrentWeather extends WeatherFetcher<CurrentWeatherSchema> {
    private PresentData<CurrentWeatherSchema> presentData;
    private WeatherDB weatherDB;
    private FragmentCurrentWeather uiContainer;


    public FetchingCurrentWeather(Fragment uiContainer, Context appContext) {
        super(uiContainer.getContext());
        this.uiContainer = (FragmentCurrentWeather) uiContainer;
        presentData = new CurrentPresenter(uiContainer.getView());
        weatherDB = Room.databaseBuilder(appContext.getApplicationContext(),
                WeatherDB.class, CurrentWeatherSchema.DB_CURRENT_WEATHER)
                .allowMainThreadQueries()
                .build();
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
                    //отобразить полученные данные
                    presentData.fillData(response.body(), uiContainer);
                    //занести их в БД
                    weatherDB.weatherDao().insert(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentWeatherSchema> call, @NonNull Throwable t) {
                Log.i("GET_CURRENT", "ERROR: " + t.getMessage());

            }
        };
    }
}