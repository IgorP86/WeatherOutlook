package com.igorr.weatheroutlook.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.igorr.weatheroutlook.R;

import model.ForecastResponseSchema;
import presenters.ForecastPresenter;
import presenters.PresentData;

import static network.WeatherFetcher.BASE_URL;
import static network.WeatherFetcher.IMAGE;
import static network.WeatherFetcher.PNG;

public class RecyclerForecast extends RecyclerView.Adapter<ForecastHolder> {
    private ForecastResponseSchema responseSchema;
    private Fragment fragment;

    public RecyclerForecast(ForecastResponseSchema responseSchema, Fragment fragment) {
        this.responseSchema = responseSchema;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ForecastHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_day_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastHolder holder, int position) {
        holder.getPresenter().fillData(responseSchema, position);
        //Изобржение неба
        Glide.with(fragment)
                .load(BASE_URL + IMAGE + responseSchema.getList()[position].getWeather()[0].getIcon() + PNG)
                .into(((ForecastPresenter)holder.getPresenter()).getImageSky());
    }

    @Override
    public int getItemCount() {
        return responseSchema.getList().length;
    }
}

class ForecastHolder extends RecyclerView.ViewHolder {
    private PresentData<ForecastResponseSchema> presenter;

    public PresentData getPresenter() {
        return presenter;
    }

    public ForecastHolder(View itemView) {
        super(itemView);
        presenter = new ForecastPresenter(itemView);
    }
}