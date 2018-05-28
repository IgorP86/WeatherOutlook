package com.igorr.weatheroutlook;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import DBWeather.WeatherDB;
import model.CurrentWeatherSchema;
import network.FetchingCurrentWeather;
import presenters.CurrentPresenter;
import presenters.PresentData;

public class FragmentCurrentWeather extends Fragment implements Updatable {
    private View view;
    private String TAG_FR_CUR_WEATHER = "FR_CUR_WEATHER";
    private String MSG_GET_FROM_DB = "Полученно с БД";
    private String MSG_GET_VIA_NET = "Загружено по сети";
    private String MSG_ERROR_IN_RECEIVING_DATA = "Нет соединения, данные не найдены";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_current_weather, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateContent();
    }

    @Override
    public void updateContent() {
        //Получить город из настроек
        long cityID = Preferences.getPreferableCityLong(getContext());

        //Тест загрузки с БД
        WeatherDB db = Room.databaseBuilder(getContext().getApplicationContext(),
                WeatherDB.class, CurrentWeatherSchema.DB_CURRENT_WEATHER)
                .allowMainThreadQueries()
                .build();

        //проверить дату последней записи
        long lastData = db.weatherDao().queryGetLastResponseDataForCity(cityID);
        long presentData = new Date().getTime();

        ConnectivityManager cm = ((ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = cm != null ? cm.getActiveNetworkInfo() : null;

        if ((float) (presentData - lastData * 1000) / 3600000 > 0.0) {
            if (networkInfo != null && networkInfo.isConnected()) {
                new FetchingCurrentWeather(this, getContext().getApplicationContext()).getDataFromNetwork();
                Log.d(TAG_FR_CUR_WEATHER, MSG_GET_VIA_NET);
            } else {
                getFromDB(db, cityID);
            }
        } else {
            getFromDB(db, cityID);
        }
    }

    private void getFromDB(WeatherDB db, long cityID) {
        CurrentWeatherSchema currentWeather = db.weatherDao().queryGetCurrentWeatherForCity(cityID);

        if (currentWeather != null) {
            PresentData<CurrentWeatherSchema> presenter = new CurrentPresenter(this.getView());
            presenter.fillData(currentWeather, this);
            Log.d(TAG_FR_CUR_WEATHER, MSG_GET_FROM_DB);
        } else {
            Snackbar.make(view, MSG_ERROR_IN_RECEIVING_DATA, Snackbar.LENGTH_LONG).show();
        }
    }
}