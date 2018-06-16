package com.igorr.weatheroutlook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import share.ActivityVKShare;


public class MainActivity extends AppCompatActivity implements FragmentChangeListener {
    //request code
    private static final int RE_SHARE = 2;
    private static final ChangeTo defaultFragment = ChangeTo.MAIN_FRAGMENT;
    private static final String TAG_MAIN_FRAGMENT = "main";
    private static final String TAG_CITIES_SELECTOR = "selector";
    private static final String TAG_KEY = "tag_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_host);

        //Восстановить фрагемент
        if (savedInstanceState != null) {
            if (savedInstanceState.getString(TAG_KEY) != null) {
                getSupportFragmentManager().beginTransaction().show(
                        getSupportFragmentManager().findFragmentByTag(savedInstanceState.getString(TAG_KEY))
                ).commit();
            }
        } else changeScreen(defaultFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            if (f.isVisible()) {
                outState.putString(TAG_KEY, f.getTag());
                break;
            }
        }
    }

    @Override
    public void changeScreen(ChangeTo changeTo) {
        switch (changeTo) {
            case MAIN_FRAGMENT:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_host, new MainFragment(), TAG_MAIN_FRAGMENT)
                        .commit();
                break;
            case CITIES_SELECTOR:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_host, new FragmentCitiesSelector(), TAG_CITIES_SELECTOR)
                        .addToBackStack(null)
                        .commit();
                break;
            case SHARE_ON_VKONTAKTE:
                startActivityForResult(ActivityVKShare.getInstance(this), RE_SHARE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_OK:
                Snackbar.make(findViewById(R.id.frame_host), R.string.str_share_successfully, Snackbar.LENGTH_LONG).show();
                break;
            case RESULT_CANCELED:
                Snackbar.make(findViewById(R.id.frame_host), R.string.str_share_canceled, Snackbar.LENGTH_LONG).show();
                break;
        }
    }
}