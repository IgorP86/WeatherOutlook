package com.igorr.weatheroutlook;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import cities_geocoding_network.Repository;

public class DialogAddNewCity extends DialogFragment {
    private static final String OK_BUTTON = "Искать";
    private static final String CANCEL_BUTTON = "Отмена";
    private static final String TITLE = "Искать в Google";
    private EditText cityName;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View viewDialog = LayoutInflater.from(getContext()).inflate(R.layout.dialog_find_city, null);
        cityName = viewDialog.findViewById(R.id.search_edit_text);

        return new AlertDialog.Builder(getActivity())
                .setView(viewDialog)
                .setTitle(TITLE)
                .setCancelable(true)
                .setPositiveButton(OK_BUTTON, (dialog, which) ->
                        //Тут нужно вернуться в вызывающую активити с сообщением cityName.getText().toString()
                        Repository.findCityByName(cityName.getText().toString(), getContext()))
                .setNegativeButton(CANCEL_BUTTON, (dialog, which) -> {

                })
                .create();
    }
}