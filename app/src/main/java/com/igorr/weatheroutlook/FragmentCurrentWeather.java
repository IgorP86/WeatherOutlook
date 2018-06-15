package com.igorr.weatheroutlook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igorr.weatheroutlook.observers.CurrentWeatherObs;

public class FragmentCurrentWeather extends Fragment {
    CurrentWeatherObs observer;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_weather, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observer = new CurrentWeatherObs(this);
        getLifecycle().addObserver(observer);
        Log.d("Snackbar","FragmentCurrentWeather onActivityCreated");
    }

    @Override
    public void onDestroyView() {
        getLifecycle().removeObserver(observer);
        Log.d("Snackbar","FragmentCurrentWeather onDestroyView");
        super.onDestroyView();
    }
}