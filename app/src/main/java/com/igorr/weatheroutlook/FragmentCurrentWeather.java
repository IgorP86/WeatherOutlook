package com.igorr.weatheroutlook;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import DBWeather.WeatherDB;
import model.CurrentWeatherSchema;
import network.FetchingCurrentWeather;

public class FragmentCurrentWeather extends Fragment implements Updatable {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_weather, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateContent();
    }

    @Override
    public void updateContent() {
        new FetchingCurrentWeather(this, Room.databaseBuilder(getContext().getApplicationContext(),
                WeatherDB.class, CurrentWeatherSchema.DB_CURRENT_WEATHER).build()).getDataFromNetwork();
    }
}
