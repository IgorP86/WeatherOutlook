package ui_presenters;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.igorr.weatheroutlook.R;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import weather_data.ResponseSchema;

public abstract class Presenter<T extends ResponseSchema> implements PresentData<T> {
    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindViews({R.id.tv_updated_data, R.id.tv_temperature})
    TextView[] tvTemperatureGroup;
    @BindViews({R.id.tv_weatherCondition, R.id.tv_wind_speed})
    TextView[] tvWeatherConditions;
    @BindViews({R.id.tv_pressure, R.id.tv_humidity, R.id.tv_sunrise, R.id.tv_sunset})
    TextView[] tvAdditionData;
    @BindView(R.id.img_wind_direction)
    ImageView imageWindDirect;
    @BindViews({R.id.label_sunrise, R.id.label_sunset})
    TextView[] labels;

    private Unbinder unbinder;
    private final View view;

    public Presenter(View viewContainer) {
        view = viewContainer;
        try {
            unbinder = ButterKnife.bind(this, viewContainer);
        } catch (NullPointerException npe) {
            Log.e(getClass().getName(), npe.getMessage() + npe.getCause());
        }
    }

    protected Drawable findFlag(String country) {
        switch (country) {
            case "RU":
                return view.getResources().getDrawable(R.mipmap.ic_flag_ru);
            case "GB":
                return view.getResources().getDrawable(R.drawable.ic_launcher_background);
            default:
                return view.getResources().getDrawable(R.drawable.ic_launcher_background);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        unbinder.unbind();
        super.finalize();
    }
}
