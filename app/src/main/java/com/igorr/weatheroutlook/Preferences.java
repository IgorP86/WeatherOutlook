package com.igorr.weatheroutlook;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

public class Preferences {
    private static final String CITY_ID = "city_id";
    private static final long DEFAULT_CITY_ID = 511565L;
    public static void setPreferableCity(Context context, long value){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putLong(CITY_ID, value)
                .apply();
    }

    @NonNull
    public static String getPreferableCityStr(Context context){
        return String.valueOf(PreferenceManager.getDefaultSharedPreferences(context)
                .getLong(CITY_ID,DEFAULT_CITY_ID));
    }

    public static long getPreferableCityLong(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(CITY_ID,DEFAULT_CITY_ID);
    }
}