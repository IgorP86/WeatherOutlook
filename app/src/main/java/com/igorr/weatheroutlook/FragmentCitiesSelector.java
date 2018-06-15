package com.igorr.weatheroutlook;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import model.CitiesViewModel;
import model.CitySchema;

public class FragmentCitiesSelector extends Fragment {

    private View view;
    private AppCompatActivity parent;
    private Unbinder unbinder;
    private CitiesViewModel citiesViewModel;
    private static final long DEFAULT_CITY_ID = 511565L;

    @BindView(R.id.toolbar_selector_fragment)
    Toolbar toolbar;
    @BindView(R.id.r_group)
    RadioGroup radioGroup;
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.tv_search_result)
    TextView textViewSearchRes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_cities_selector, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        parent = (AppCompatActivity) getActivity();

        if (parent != null) {
            parent.setSupportActionBar(toolbar);
            ActionBar actionBar = parent.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
        setHasOptionsMenu(true);
        citiesViewModel = new ViewModelProvider(parent.getViewModelStore(), new ViewModelProvider.NewInstanceFactory())
                .get(CitiesViewModel.class);
        searchView.setOnQueryTextListener(citiesViewModel);

        //Загрузить весь список полученный из БД
        makeRadioGroup(citiesViewModel.getAllAvailableCities());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //Запомнить выбор и вернуться на главный экран
            Preferences.setPreferableCity(getContext(),
                    radioGroup.getCheckedRadioButtonId() != -1 ? radioGroup.getCheckedRadioButtonId() : DEFAULT_CITY_ID);
            Log.d("Snackbar", String.valueOf(parent.getSupportFragmentManager().getFragments().size()));
            parent.getSupportFragmentManager().popBackStack();
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        //Подписаться на изменения в списке
        citiesViewModel.getCityListLiveData().observe(this, (newCitiesList) -> {
            radioGroup.removeAllViews();
            if (newCitiesList != null && newCitiesList.size() != 0) {
                textViewSearchRes.setVisibility(View.INVISIBLE);
                makeRadioGroup(newCitiesList);
            } else {
                textViewSearchRes.setVisibility(View.VISIBLE);
            }
        });
    }

    private void makeRadioGroup(List<CitySchema> newCitiesList) {
        for (CitySchema c : newCitiesList) {
            RadioButton rb = new RadioButton(getContext());
            rb.setText(c.getCityName());
            rb.setId(Integer.parseInt(c.getCityId()));
            radioGroup.addView(rb);
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.fab_add_new_city)
    protected void onFabClick() {
        new DialogAddNewCity().show(getChildFragmentManager(),"");
    }
}





