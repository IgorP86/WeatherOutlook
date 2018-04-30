package com.igorr.weatheroutlook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import model.CitiesRU;

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
            rb.setText(c.getName());
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