package presenters;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.igorr.weatheroutlook.R;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import model.ResponseSchema;

public abstract class Presenter<T extends ResponseSchema> implements PresentData<T> {
    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindViews({R.id.tv_updated_data, R.id.tv_temperature})
    TextView[] tvTemperatureGroup;
    @BindViews({R.id.tv_weatherCondition, R.id.tv_wind_speed})
    TextView[] tvWeatherConditions;
    @BindViews({R.id.tv_pressure, R.id.tv_humidity, R.id.tv_sunrise, R.id.tv_sunset})
    TextView[] tvAdditionData;
    @BindView(R.id.img_sky)
    ImageView imageSky;
    @BindView(R.id.img_wind_direction)
    ImageView imageWindDirect;

    private Unbinder unbinder;

    public ImageView getImageSky() {
        return imageSky;
    }

    public Presenter(View viewContainer) {
        try {
            this.unbinder = ButterKnife.bind(this, viewContainer);
        }    catch (NullPointerException npe){
            Log.e(this.getClass().getName(),npe.getMessage()+npe.getCause());
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        unbinder.unbind();
    }
}
