package com.igorr.weatheroutlook.observers;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import model.CurrentWeatherSchema;
import model.TEST_ViewModel;
import network.LoaderLiveData;
import presenters.CurrentPresenter;

public class CurrentWeatherObserver implements LifecycleObserver {
    private Fragment fragment;
    private View view;
    private String MSG_ERROR_IN_RECEIVING_DATA = "Нет соединения, данные не найдены";

    public CurrentWeatherObserver(Fragment fragment) {
        this.fragment = fragment;
        view = fragment.getView();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void subscribeToUpdate() {

        TEST_ViewModel vm = new ViewModelProvider(fragment.getActivity().getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.getActivity().getApplication())
        ).get(TEST_ViewModel.class);

        Log.d("ViewMODEL", vm.toString());
        if (vm.getTEST_string() == null) {
            Log.d("ViewMODEL", "null");
            vm.setTEST_string("s");
        } else {
            Log.d("ViewMODEL", vm.getTEST_string());
            vm.setTEST_string("d");
        }

        LoaderLiveData.getInstance(fragment.getContext()).observe(fragment, new Observer<CurrentWeatherSchema>() {
            @Override
            public void onChanged(@Nullable CurrentWeatherSchema currentWeatherSchema) {
                if (currentWeatherSchema != null) {
                    new CurrentPresenter(view).fillData(currentWeatherSchema, fragment);
                } else {
                    Snackbar.make(view, MSG_ERROR_IN_RECEIVING_DATA, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}