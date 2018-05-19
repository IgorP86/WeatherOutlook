package com.igorr.weatheroutlook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentCurrentWeather extends Fragment {
    private MainActionListener parentActionListener;
    public static final String UI_ID = "current";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            parentActionListener = (MainActionListener) context;
        }catch (Exception e){
            Log.d("CardView", "onAttach" + e.toString());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_current_weather, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("PAGER", "FragmentCurrentWeather onResume");
        parentActionListener.updateData(this,UI_ID);
    }
}
