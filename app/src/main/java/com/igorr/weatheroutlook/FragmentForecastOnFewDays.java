package com.igorr.weatheroutlook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igorr.weatheroutlook.adapters.RecyclerForecast;

import model.ForecastResponseSchema;

public class FragmentForecastOnFewDays extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    private MainActionListener parentActionListener;
    public static final String UI_ID = "forecast";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            parentActionListener = (MainActionListener) context;
        }catch (Exception e){
            Log.d("CardView", "onAttach" + e.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("PAGER", "FragmentForecastOnFewDays onResume");
        parentActionListener.updateData(this, UI_ID);
    }

    public void setupAdapter(ForecastResponseSchema re){
        recyclerView = view.findViewById(R.id.rv_forecast);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerForecast(re, this));
    }
}
