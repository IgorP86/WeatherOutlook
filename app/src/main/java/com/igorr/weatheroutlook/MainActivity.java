package com.igorr.weatheroutlook;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.CitiesRU;
import network.WeatherFetcher;
import share.ActivityVKShare;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_in_Main)
    Toolbar toolbar;
    @BindView(R.id.parentPanel)
    ConstraintLayout parentPanel;
    @BindView(R.id.btn_share)
    Button btnShare;

    private static final int RE_WHAT_IS_CITY = 1;
    private static final int RE_SHARE = 2;
    private static final String DEFAULT_CITY_ID = "524901";
    //дефолтное - Москва
    private String representedCityID = DEFAULT_CITY_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Загрузить ID городов
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
                .putExtra(getString(R.string.str_city_name), representedCityID), RE_SHARE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode) {
                    case RE_WHAT_IS_CITY:
                        //Заменить представлемый город, обновить UI
                        representedCityID = String.valueOf(data.getIntExtra(getString(R.string.str_city_name), 511565));
                        updateData(representedCityID);
                        break;
                    case RE_SHARE:
                        Snackbar.make(parentPanel, R.string.share_successfully, Snackbar.LENGTH_LONG).show();
                        break;
                }
                break;
            case RESULT_CANCELED:
                Snackbar.make(parentPanel, R.string.share_canceled, Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    public void updateData(String cityID) {
        //Заблокировать кнопку "поделиться"
        btnShare.setEnabled(false);
        new WeatherFetcher(this, cityID).getData();
    }
}