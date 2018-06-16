package com.igorr.weatheroutlook;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class DialogAddNewCity extends DialogFragment {
    private View viewDialog;
    private static final String OK_BUTTON = "Искать";
    private static final String CANCEL_BUTTON = "Отмена";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Log.d("Dialog", "onCreateDialog");

        viewDialog = LayoutInflater.from(getContext()).inflate(R.layout.dialog_find_city, null);

        return new AlertDialog.Builder(getContext())
                .setView(viewDialog)
                .setTitle(R.string.str_select_city)
                .setCancelable(true)
                .setPositiveButton(OK_BUTTON, (dialog, which) -> {

                })
                .setNegativeButton(CANCEL_BUTTON, (dialog, which) -> {

                })
                .create();
    }
}
