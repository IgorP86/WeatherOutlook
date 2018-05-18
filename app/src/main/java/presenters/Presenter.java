package presenters;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.igorr.weatheroutlook.R;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import model.CitiesRU;
import model.CurrentWeatherSchema;
import model.ResponseSchema;

public class Presenter implements PresentData<CurrentWeatherSchema> {
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

    public Presenter(Fragment container) {
        try {
            this.unbinder = ButterKnife.bind(this, container.getView());
        }    catch (NullPointerException npe){
            Log.e(this.getClass().getName(),npe.getMessage()+npe.getCause());
        }
    }

    @Override
    public void fillData(CurrentWeatherSchema re, Drawable... drawables) {
        //Название города
        tvCityName.setText(CitiesRU.findCityById(re.getCityId()));

        //время последнего обновления
        tvTemperatureGroup[0].setText(String.valueOf(re.getLastResponseData()));

        //Изобржение неба
        imageSky.setImageDrawable(drawables[0]);

        //Направление ветра
        imageWindDirect.setBackgroundResource(R.drawable.ic_arrow_upward_black_24dp);
        imageWindDirect.setRotation(180 + re.getWind().getDegrees());

        //температура
        float temp = re.getMain().getTemperature();
        tvTemperatureGroup[1].setText(temp <= 0 ?
                String.format("%s \u00B0C", temp) : String.format("+%s \u00B0C", temp));

        tvWeatherConditions[0].setText(re.getWeather()[0].getMainGroupWeatherParams());
        tvWeatherConditions[1].setText(String.format("%s м/с", String.valueOf(re.getWind().getSpeed())));

        tvAdditionData[0].setText(String.valueOf(re.getMain().getPressure() * ResponseSchema.K_PRESSURE));
        tvAdditionData[1].setText(String.valueOf(re.getMain().getHumidity()));
        tvAdditionData[2].setText(new SimpleDateFormat("HH:mm:ss").format(re.getSys().getSunrise()));
        tvAdditionData[3].setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(re.getSys().getSunset()));
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        unbinder.unbind();
    }
}