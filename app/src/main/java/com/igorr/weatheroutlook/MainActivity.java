package com.igorr.weatheroutlook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.igorr.weatheroutlook.adapters.MyPagerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.CitiesRU;
import network.SentinelConnection;
import share.ActivityVKShare;


public class MainActivity extends AppCompatActivity implements MainActionListener {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_pager)
    TabLayout tabLayout;

    private static SentinelConnection sentinel;
    //request code
    private static final int RE_SHARE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sentinel = new SentinelConnection(this);
        sentinel.start();
        //Загрузить данные по городам
        CitiesRU.getInstance();
        //Подключить ViewPager
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager, true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("sentinel", "выход из потока?");
        sentinel.interrupt();
    }

    @Override
    public void updateUI() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment f : fragmentList) {
            if (f instanceof Updatable) {
                ((Updatable) f).updateContent();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.on_main_aktivity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_VKshare:
                startActivityForResult(ActivityVKShare.getInstance(this), RE_SHARE);
                return true;
            case R.id.btn_cities_selector:
                new FragmentCitiesSelector().show(getSupportFragmentManager(), "Dialog");
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
                Snackbar.make(this.viewPager, R.string.str_share_successfully, Snackbar.LENGTH_LONG).show();
                break;
            case RESULT_CANCELED:
                Snackbar.make(this.viewPager, R.string.str_share_canceled, Snackbar.LENGTH_LONG).show();
                break;
        }
    }
}