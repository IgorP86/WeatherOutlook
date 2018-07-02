package com.igorr.weatheroutlook.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

import com.igorr.weatheroutlook.R;

import ui_presenters.ForecastWeatherPresenter;
import ui_presenters.PresentData;
import weather_data.ForecastResponseSchema;

public class RecyclerForecastAdapter extends RecyclerView.Adapter<ForecastHolder> {
    private ForecastResponseSchema responseSchema;
    private Fragment fragment;

    public void updateSchema(ForecastResponseSchema responseSchema) {
        this.responseSchema = responseSchema;
        notifyDataSetChanged();
    }

    public RecyclerForecastAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TestAdapter", "onCreateViewHolder");
        return new ForecastHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_day_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastHolder holder, int position) {
        holder.getPresenter().fillData(responseSchema, position, fragment);
    }

    @Override
    public int getItemCount() {
        return responseSchema != null ? responseSchema.getList().length : 0;
    }

    @Override
    public void onViewRecycled(@NonNull ForecastHolder holder) {
        super.onViewRecycled(holder);
        Log.d("TestAdapter", "onViewRecycled");
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ForecastHolder holder) {
        super.onViewAttachedToWindow(holder);
        Log.d("TestAdapter", "onViewAttachedToWindow");
    }

}

class ForecastHolder extends RecyclerView.ViewHolder {
    private PresentData<ForecastResponseSchema> presenter;
    private View view;
    private ViewPropertyAnimator propertyAnimator;

    public PresentData<ForecastResponseSchema> getPresenter() {
        return presenter;
    }

    public ForecastHolder(View itemView) {
        super(itemView);
        presenter = new ForecastWeatherPresenter(itemView);
        view = itemView;
        propertyAnimator = view.animate();
    }

    public View getView() {
        return view;
    }

    public ViewPropertyAnimator getPropertyAnimator() {
        return propertyAnimator;
    }
}