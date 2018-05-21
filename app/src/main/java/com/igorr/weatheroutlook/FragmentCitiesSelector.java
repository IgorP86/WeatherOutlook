package com.igorr.weatheroutlook;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import model.CitiesRU;

public class FragmentCitiesSelector extends DialogFragment {
    private static final String DIALOG_TITLE = "Выберите город";
    private static final String OK_BUTTON = "Запомнить выбор";

    private MainActionListener parentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            parentListener = (MainActionListener) context;
        } catch (Exception e) {
            Log.d("CardView", "onAttach" + e.toString());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity parent = getActivity();

        View thisDialog = LayoutInflater.from(parent).inflate(R.layout.dialog_cities_selector, null);

        final RadioGroup radioGroup = thisDialog.findViewById(R.id.r_group);

        for (CitiesRU.City c : CitiesRU.getCitiesRu()) {
            RadioButton rb = new RadioButton(getContext());
            rb.setText(c.getCityName());
            rb.setId(Integer.parseInt(c.getCityId()));
            radioGroup.addView(rb);
        }

        return new AlertDialog.Builder(parent)
                .setView(thisDialog)
                .setTitle(DIALOG_TITLE)
                .setIcon(getResources().getDrawable(R.drawable.ic_search_black_24dp))
                .setPositiveButton(OK_BUTTON, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Сохранить в настройках выбор города
                        int selectedID = radioGroup.getCheckedRadioButtonId();
                        Preferences.setPreferableCity(getContext(), selectedID);
                        parentListener.updateUI();
                    }
                })
                .create();
    }
}