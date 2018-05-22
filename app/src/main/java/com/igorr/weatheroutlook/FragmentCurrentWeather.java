package com.igorr.weatheroutlook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        new FetchingCurrentWeather(this).getDataFromNetwork();
    }
}
