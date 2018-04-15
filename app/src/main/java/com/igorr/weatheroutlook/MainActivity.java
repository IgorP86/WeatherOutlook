package com.igorr.weatheroutlook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import presenters.PresentData;
import presenters.Presenter;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PresentData p = new Presenter(this);
        p.fillData();

    }
}
