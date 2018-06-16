package com.igorr.weatheroutlook;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.igorr.weatheroutlook.adapters.MyPagerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import model.NetworkViewModel;

public class MainFragment extends Fragment {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_pager)
    TabLayout tabLayout;
    private AppCompatActivity parent;
    private Unbinder unbinder;
    private View view;
    private FragmentChangeListener fragmentChangeListener;
    private NetworkViewModel viewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentChangeListener = (FragmentChangeListener) context;
        } catch (Exception e) {
            Log.d("", "onAttach" + e.toString());
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        parent = (AppCompatActivity) getActivity();
        if (parent != null) {
            parent.setSupportActionBar(parent.findViewById(R.id.toolbar_main_fragment));
            ActionBar actionBar = parent.getSupportActionBar();
            if (actionBar != null)
                actionBar.setTitle("название");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel = new ViewModelProvider(parent.getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(parent.getApplication())
        ).get(NetworkViewModel.class);

        viewModel.getPermitLiveData().observe(this, (aBoolean) -> {
            if (aBoolean != null && aBoolean) {
                updateUI();
            }
        });
        updateUI();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.getPermitLiveData().removeObservers(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.on_main_aktivity_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_VKshare:
                fragmentChangeListener.changeScreen(FragmentChangeListener.ChangeTo.SHARE_ON_VKONTAKTE);
                return true;
            case R.id.btn_cities_selector:
                fragmentChangeListener.changeScreen(FragmentChangeListener.ChangeTo.CITIES_SELECTOR);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        Log.d("Snackbar", " updateUI()");
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager, true);

        //Устаревший код обновления UI
        List<Fragment> fragmentList = parent.getSupportFragmentManager().getFragments();
        for (Fragment f : fragmentList) {
            if (f != null && f instanceof Updatable && f.isVisible()) {
                ((Updatable) f).updateContent();
            }
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
