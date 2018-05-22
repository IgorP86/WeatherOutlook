package com.igorr.weatheroutlook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igorr.weatheroutlook.adapters.RecyclerForecast;

import model.ForecastResponseSchema;
import network.FetchingForecastWeather;

public class FragmentForecastOnFewDays extends Fragment implements Updatable {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateContent();
    }

    public void setupAdapter(ForecastResponseSchema re) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_forecast);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerForecast(re, this));
    }

    @Override
    public void updateContent() {
        new FetchingForecastWeather(this).getDataFromNetwork();
    }
}