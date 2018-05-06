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
import network.WeatherFetcher;
import oauth.share.ActivityVKShare;


public class MainActivity extends AppCompatActivity {
    private static final int RE_WHAT_IS_CITY = 1;
    private static final int RE_OAUTH = 2;
    private static final String DEFAULT_CITY_ID = "524901";

    @BindView(R.id.toolbar_in_Main)
    Toolbar toolbar;
    private String representedCityID = DEFAULT_CITY_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        CitiesRU.getInstance();
        updateData(DEFAULT_CITY_ID);
    }

    @OnClick(R.id.btn_to_cities_selector)
    protected void toCitiesSelector() {
        startActivityForResult(new Intent(this, ActivitySelector.class), RE_WHAT_IS_CITY);
    }

    @OnClick(R.id.btn_share)
    protected void shareInVK() {
        startActivityForResult(new Intent(this, ActivityVKShare.class)
                .putExtra(getString(R.string.str_city_name), representedCityID), RE_OAUTH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RE_WHAT_IS_CITY && resultCode == RESULT_OK) {
            representedCityID = String.valueOf(data.getIntExtra(getString(R.string.str_city_name), 511565));
            updateData(representedCityID);
            Log.d("SIZE", "город " + data.getIntExtra(getString(R.string.str_city_name), 0));
        }
    }

    public void updateData(String cityID) {
        new WeatherFetcher(this, cityID).getData();
    }
}