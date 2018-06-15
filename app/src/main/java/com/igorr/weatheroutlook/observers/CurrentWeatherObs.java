package com.igorr.weatheroutlook.observers;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import model.CurrentWeatherViewModel;
import presenters.CurrentWeatherPresenter;

public class CurrentWeatherObs implements LifecycleObserver {
    private Fragment fragment;
    private View view;
    private String MSG_ERROR_IN_RECEIVING_DATA = "Нет соединения, данные не найдены";
    private CurrentWeatherViewModel viewModel;

    public CurrentWeatherObs(Fragment fragment) {
        this.fragment = fragment;
        view = fragment.getView();
        if (fragment.getActivity() != null) {
            ViewModelStore modelStore = fragment.getActivity().getViewModelStore();
            viewModel = new ViewModelProvider(modelStore,
                    ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.getActivity().getApplication())
            ).get(CurrentWeatherViewModel.class);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void subscribeToUpdate() {
        Log.d("Snackbar", "subscribeToUpdate");
        if (fragment.getActivity() != null) {
            Log.d("viewModel", viewModel.toString());

            viewModel.getLoaderLiveData().observe(fragment, currentWeatherSchema -> {
                if (currentWeatherSchema != null) {
                    new CurrentWeatherPresenter(view).fillData(currentWeatherSchema, fragment);
                } else {
                    Log.d("NO_DATA", MSG_ERROR_IN_RECEIVING_DATA);
                    if (fragment.getView() != null) {
                        Snackbar.make(fragment.getView(), MSG_ERROR_IN_RECEIVING_DATA, Snackbar.LENGTH_LONG).show();
                        Log.d("Snackbar", MSG_ERROR_IN_RECEIVING_DATA);
                    }
                }
            });
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void releaseRes() {
        Log.d("Snackbar", "Lifecycle.Event.ON_PAUSE");
        viewModel.getLoaderLiveData().removeObservers(fragment);
    }
}