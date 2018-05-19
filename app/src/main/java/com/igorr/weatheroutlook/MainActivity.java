package com.igorr.weatheroutlook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.igorr.weatheroutlook.adapters.MyPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.CitiesRU;
import network.CurrentWeather;
import network.ForecastWeather;
import share.ActivityVKShare;


public class MainActivity extends AppCompatActivity implements MainActionListener {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_pager)
    TabLayout tabLayout;

    public Menu menu;
    //Константы запросов
    private static final int RE_WHAT_IS_CITY = 1;
    private static final int RE_SHARE = 2;
    private static final String DEFAULT_CITY_ID = "511565";

    //Константы используемых фрагментов
    public static final String CURRENT = "current";

    //представляемый город: дефолтное - Москва
    private String representedCityID = DEFAULT_CITY_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Загрузить ID городов
        CitiesRU.getInstance();

        //Подключить ViewPager
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager, true);
        // updateData(DEFAULT_CITY_ID);
        Log.d("ORDER", "onCreate");
    }

    @Override
    protected void onResume() {
        Log.d("ORDER", "onResume");
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("MENU", "create");
        this.menu = menu;
        getMenuInflater().inflate(R.menu.on_main_aktivity_menu, menu);

        Log.d("ORDER", "onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d("ORDER", "onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_VKshare:
                startActivityForResult(new Intent(this, ActivityVKShare.class)
                        .putExtra(getString(R.string.str_city_name), representedCityID), RE_SHARE);
                return true;
            case R.id.btn_cities_selector:
                //startActivityForResult(new Intent(this, FragmentCitiesSelector.class), RE_WHAT_IS_CITY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                        //updateData();
                        break;
                    case RE_SHARE:
                        // Snackbar.make(parentPanel, R.string.str_share_successfully, Snackbar.LENGTH_LONG).show();
                        break;
                }
                break;
            case RESULT_CANCELED:
                //        Snackbar.make(parentPanel, R.string.str_share_canceled, Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void updateData(Fragment uiContainer, String UI_ID) {
        switch (UI_ID){
            case FragmentCurrentWeather.UI_ID:
                new CurrentWeather(representedCityID, uiContainer).getDataFromNetwork();
                break;
            case FragmentForecastOnFewDays.UI_ID:
                new ForecastWeather(representedCityID, uiContainer).getDataFromNetwork();
                break;
        }
    }
}