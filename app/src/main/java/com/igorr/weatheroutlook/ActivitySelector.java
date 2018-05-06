package com.igorr.weatheroutlook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.ButterKnife;

import static model.CitiesRU.*;

public class ActivitySelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_selector);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
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
        });
    }
}