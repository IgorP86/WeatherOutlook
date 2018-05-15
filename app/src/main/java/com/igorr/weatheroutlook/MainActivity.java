package com.igorr.weatheroutlook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.CitiesRU;
import network.WeatherFetcher;
import share.ActivityVKShare;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_pager)
    TabLayout tabLayout;

    public Menu menu;
    //Константы запросов
    private static final int RE_WHAT_IS_CITY = 1;
    private static final int RE_SHARE = 2;
    private static final String DEFAULT_CITY_ID = "524901";

    //представляемый город: дефолтное - Москва
    private String representedCityID = DEFAULT_CITY_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Загрузить ID городов
        CitiesRU.getInstance();

        //Обернуть фрагменты в List

        //Подключить ViewPager
        viewPager.setAdapter(new FragmentStatePagerAdapter() {
            @Override
            public Fragment getItem(int position) {
                return null;
            }

            @Override
            public int getCount() {
                return 0;
            }
        });
        tabLayout.setupWithViewPager(viewPager,true);
       // updateData(DEFAULT_CITY_ID);
        Log.d("ORDER","onCreate");
    }

    @Override
    protected void onResume() {
        Log.d("ORDER","onResume");
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("MENU", "create");
        this.menu = menu;
        getMenuInflater().inflate(R.menu.on_main_aktivity_menu, menu);

        Log.d("ORDER","onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d("ORDER","onPrepareOptionsMenu");
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
                startActivityForResult(new Intent(this, FragmentCitiesSelector.class), RE_WHAT_IS_CITY);
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
                        updateData(representedCityID);
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

    public void updateData(String cityID) {
        //Заблокировать кнопку "поделиться"
    //    MenuItem item = menu.getItem(R.id.menu_group_share);
//
        new WeatherFetcher(this, cityID).getData();
    }
}