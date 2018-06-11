package com.igorr.weatheroutlook;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import model.CitiesRU;

public class FragmentCitiesSelector extends DialogFragment {
    private static final String DIALOG_TITLE = "Выберите город";
    private static final String OK_BUTTON = "Запомнить выбор";

    private MainActionListener parentListener;
    private View viewDialog;
    private Activity parent;

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

        Log.d("Dialog","onCreateDialog");
        parent = getActivity();
        viewDialog = LayoutInflater.from(parent).inflate(R.layout.dialog_cities_selector, null);

        final RadioGroup radioGroup = viewDialog.findViewById(R.id.r_group);
        for (CitiesRU.City c : CitiesRU.getCitiesRu()) {
            RadioButton rb = new RadioButton(getContext());
            rb.setText(c.getCityName());
            rb.setId(Integer.parseInt(c.getCityId()));
            radioGroup.addView(rb);
        }

        return new AlertDialog.Builder(parent)
                .setView(viewDialog)
                .setTitle(R.string.str_select_city)
                .setPositiveButton(OK_BUTTON, (dialog, which) -> {
                    //Сохранить в настройках выбор города
                    long selectedID = radioGroup.getCheckedRadioButtonId();
                    Preferences.setPreferableCity(getContext(), selectedID);
                    parentListener.updateUI();
                })
                .create();
    }
}