package com.igorr.weatheroutlook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public class FragmentCitiesSelector extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        ActionBar actionBar = getSupportActionBar();
        RadioGroup radioGroup = findViewById(R.id.r_group);

        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        for (City c : getCitiesRu()) {
            RadioButton rb = new RadioButton(this);
            rb.setText(c.getCityName());
            rb.setId(Integer.parseInt(c.getCityId()));
            radioGroup.addView(rb);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Добавить в ответ ID города
                setResult(RESULT_OK, new Intent().putExtra(getString(R.string.str_city_name), checkedId));
            }
        });*/
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_cities_selector, container, false);
    }



}