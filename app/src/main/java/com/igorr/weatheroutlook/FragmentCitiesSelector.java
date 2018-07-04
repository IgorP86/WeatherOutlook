package com.igorr.weatheroutlook;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cities_data.CitiesViewModel;
import cities_data.CitySchema;

public class FragmentCitiesSelector extends Fragment {

    private View view;
    private AppCompatActivity parent;
    private Unbinder unbinder;
    private CitiesViewModel citiesViewModel;
    private static final long DEFAULT_CITY_ID = 511565L;
    private static final String INPUT_MISTAKE = "Населеного пункта с таким именем не существует";
    private static final String UNKNOWN_MISTAKE = "Неизвестная ошибка!";
    private static final String LIST_UPDATED = "Данные в списке городов обновлены";


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
        citiesViewModel = new ViewModelProvider(parent.getViewModelStore(), new ViewModelProvider.AndroidViewModelFactory(parent.getApplication()))
                .get(CitiesViewModel.class);
        searchView.setOnQueryTextListener(citiesViewModel);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //Запомнить выбор и вернуться на главный экран
            Preferences.setPreferableCity(getContext(),
                    radioGroup.getCheckedRadioButtonId() != -1 ? radioGroup.getCheckedRadioButtonId() : DEFAULT_CITY_ID);
            parent.getSupportFragmentManager().popBackStack();
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Подписаться на изменения в списке
        citiesViewModel.getListUpdatedLive().observe(this, (newCitiesList) -> {
            radioGroup.removeAllViews();
            if (newCitiesList != null && newCitiesList.size() != 0) {
                textViewSearchRes.setVisibility(View.INVISIBLE);
                makeRadioGroup(newCitiesList);
            } else {
                textViewSearchRes.setVisibility(View.VISIBLE);
            }
        });

        //Еще одна liveData
        citiesViewModel.getDataReceivedLive().observe(this, (citySchema) -> {
            Toast message = Toast.makeText(getContext()
                    , citySchema != null ?
                            (citiesViewModel.initialization() ? LIST_UPDATED : UNKNOWN_MISTAKE) : INPUT_MISTAKE
                    , Toast.LENGTH_LONG);
            message.setGravity(Gravity.CENTER, 0, 0);
            message.show();
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        citiesViewModel.getDataReceivedLive().removeObservers(this);
        citiesViewModel.getListUpdatedLive().removeObservers(this);
    }

    private void makeRadioGroup(List<CitySchema> newCitiesList) {
        int i = 0;
        for (CitySchema c : newCitiesList) {
            RadioButton rb = new RadioButton(getContext());
            rb.setText(c.getCityName());
            rb.setId(c.getPrimaryKey());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                rb.setTextAppearance(R.style.MyStyle);
            }
            radioGroup.addView(rb);
            Log.d("NUMBER", String.valueOf(i++));
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.fab_add_new_city)
    protected void onFabClick() {
        new DialogAddNewCity().show(getChildFragmentManager(), "");
    }
}