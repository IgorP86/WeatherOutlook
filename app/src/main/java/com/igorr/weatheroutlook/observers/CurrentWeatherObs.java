package com.igorr.weatheroutlook.observers;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import ui_presenters.CurrentWeatherPresenter;
import weather_data.CurrentWeatherSchema;
import weather_data.CurrentWeatherViewModel;

public class CurrentWeatherObs implements LifecycleObserver {
    private Fragment fragment;
    private View view;
    private CurrentWeatherViewModel viewModel;
    private Observer<CurrentWeatherSchema> observer;
    private String MSG_ERROR_IN_RECEIVING_DATA = "Нет соединения, данные не найдены";

    public CurrentWeatherObs(Fragment fragment) {
        this.fragment = fragment;
        view = fragment.getView();

        if (fragment.getActivity() != null) {
            ViewModelStore modelStore = fragment.getActivity().getViewModelStore();
            viewModel = new ViewModelProvider(modelStore,
                    ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.getActivity().getApplication())
            ).get(CurrentWeatherViewModel.class);
        }

        observer = schema -> {
            if (schema != null) {
                new CurrentWeatherPresenter(view).fillData(schema, fragment);
            } else {
                Toast t = Toast.makeText(fragment.getContext(), MSG_ERROR_IN_RECEIVING_DATA, Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
            }
        };
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void subscribeToUpdate() {
        if (fragment.getActivity() != null) {
            viewModel.getLoaderLiveData().observe(fragment, observer);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void releaseRes() {
        viewModel.getLoaderLiveData().removeObservers(fragment);
        viewModel.getLoaderLiveData().removeObserver(observer);
    }
}