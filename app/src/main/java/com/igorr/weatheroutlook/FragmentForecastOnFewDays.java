package com.igorr.weatheroutlook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.igorr.weatheroutlook.adapters.RecyclerForecastAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import weather_data.ForecastResponseSchema;
import weather_network.FetchingForecastWeather;

public class FragmentForecastOnFewDays extends Fragment {
    @BindView(R.id.recycler_view_forecast)
    RecyclerView recyclerView;
    private View view;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forecast, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupAdapter();
        new FetchingForecastWeather(this).getDataFromNetwork();
    }

    public void setupAdapter() {
        recyclerView = view.findViewById(R.id.recycler_view_forecast);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerForecastAdapter(this));
        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                Log.d("onFling",String.format("velocityX %d ,velocityY %d", velocityX, velocityY));
                return false;
            }
        });
    }
}