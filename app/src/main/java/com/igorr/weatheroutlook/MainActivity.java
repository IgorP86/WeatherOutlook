package com.igorr.weatheroutlook;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProvider.AndroidViewModelFactory;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import model.MyViewModelFactory;
import model.TEST_ViewModel;
import network.NetworkPermitLiveData;
import share.ActivityVKShare;


public class MainActivity extends AppCompatActivity implements MainActionListener, LifecycleObserver {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_pager)
    TabLayout tabLayout;

    //request code
    private static final int RE_SHARE = 2;
    TEST_ViewModel vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Тест ViewModel
        vm = new ViewModelProvider(getViewModelStore(),
                AndroidViewModelFactory.getInstance(getApplication())
        ).get(TEST_ViewModel.class);

        //vm = MyViewModelFactory.getFactory(getApplication()).create(TEST_ViewModel.class);
        Log.d("ViewMODEL", vm.toString());

        if (vm.getTEST_string() == null) {
            Log.d("ViewMODEL", "null");
            vm.setTEST_string("s");
        } else {
            Log.d("ViewMODEL", vm.getTEST_string());
        }

        NetworkPermitLiveData permitNetwork = NetworkPermitLiveData.getInstance(this);
        permitNetwork.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null && aBoolean) {
                    updateUI();
                    Log.d("NETWORK", "данные обновлены");
                }
            }
        });
        //Загрузить данные по городам
        CitiesRU.getInstance();
        //Подключить ViewPager
        updateUI();

    }

    @Override
    public void updateUI() {
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager, true);
        //Устаревший код обновления UI
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment f : fragmentList) {
            if (f != null && f instanceof Updatable && f.isVisible()) {
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