package com.igorr.weatheroutlook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.CitiesRU;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_WHAT_IS_CITY = 1;

    @BindView(R.id.toolbar_in_Main)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        updateData("511565");
        CitiesRU.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.btn_to_cities_selector)
    protected void toCitiesSelector() {
        startActivityForResult(new Intent(this, ActivitySelector.class), REQUEST_WHAT_IS_CITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_WHAT_IS_CITY && resultCode == RESULT_OK) {
            updateData(String.valueOf(data.getIntExtra(getString(R.string.str_city_name), 511565)));
            Log.d("SIZE", "город "+data.getIntExtra(getString(R.string.str_city_name),0));
        }
    }

    public void updateData(String cityID) {
        new WeatherFetcher(this, cityID).getData();
    }
}