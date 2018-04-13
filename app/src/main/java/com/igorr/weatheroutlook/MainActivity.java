package com.igorr.weatheroutlook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import model.ResponseSchema;

import static model.ResponseSchema.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ResponseSchema response1 = new Builder()
                .addCoordinates(new Coordinates(10, 20))
                .addWeather(new Weather(1, "", "", ""))
                .addCityId(12235)
                .build();

        ResponseSchema response2 = new Builder().build();

        float la = response1.getCoordinates().getLatitude();
        float lo = response1.getCoordinates().getLongitude();

    }
}
