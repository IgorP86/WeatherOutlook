package com.igorr.weatheroutlook;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

public class Preferences {
    private static final String CITY_ID = "city_id";
    private static final int DEFAULT_CITY_ID = 511565;

    public static void setPreferableCity(Context context, Integer id){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putInt(CITY_ID, id)
                .apply();
    }

    @NonNull
    public static String getPreferableCity(Context context){
        return String.valueOf(PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(CITY_ID,DEFAULT_CITY_ID));
    }
}