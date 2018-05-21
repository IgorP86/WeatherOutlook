package com.igorr.weatheroutlook.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.igorr.weatheroutlook.FragmentCurrentWeather;
import com.igorr.weatheroutlook.FragmentForecastOnFewDays;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private String[] PAGE_TITLES = {"Погода сейчас", "Прогноз погоды"};

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<>();
        setupPages();
    }

    private void setupPages() {
        //ЗАдать набор фрагментов
        fragments.add(new FragmentCurrentWeather());
        fragments.add(new FragmentForecastOnFewDays());
    }

    @Override
    public void startUpdate(ViewGroup container) {
        Log.d("PAGER","startUpdate");
        super.startUpdate(container);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return PAGE_TITLES[position];
    }
}
