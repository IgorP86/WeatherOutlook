package presenters;

import android.app.Activity;
import android.widget.TextView;

import com.igorr.weatheroutlook.R;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import model.ResponseSchema;

import static model.ResponseSchema.Builder;
import static model.ResponseSchema.Coordinates;
import static model.ResponseSchema.MainData;
import static model.ResponseSchema.Sys;
import static model.ResponseSchema.Weather;
import static model.ResponseSchema.Wind;

public class Presenter implements PresentData {
    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindViews({R.id.tv_updated_data, R.id.tv_temperature})
    TextView[] tvTemperatureGroup;
    @BindViews({R.id.tv_weatherCondition, R.id.tv_wind_speed})
    TextView[] tvWeatherConditions;
    @BindViews({R.id.tv_pressure, R.id.tv_humidity, R.id.tv_sunrise, R.id.tv_sunset})
    TextView[] tvAdditionData;

    private ResponseSchema response1;
    private Unbinder unbinder;

    public Presenter(Activity activity) {
        this.unbinder = ButterKnife.bind(this, activity);
        stub();
    }

    private void stub() {
        response1 = new Builder()
                .addCoordinates(new Coordinates(10, 20))
                .addWeather(new Weather(1, "Солнечно", "", ""))
                .addCityId(12235)
                .addWind(new Wind(12, "Слабый ветер", 60))
                .addSys(new Sys(1, 0, "msg", 3, 3251215250155L, 3251215256155L))
                .addMainData(new MainData[]{new MainData(-20, 760, 50, 0, 0, 0, 0)})
                .addTimeOfDataCalc(120218)
                .build();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        unbinder.unbind();
    }

    @Override
    public void fillData() {
        tvCityName.setText(String.valueOf(response1.getCityId()));

        tvTemperatureGroup[0].setText(String.valueOf(response1.getTimeOfDataCalc()));
        tvTemperatureGroup[1].setText(response1.getMainData()[0].getTemp() <= 0 ?
                String.format("%s C", response1.getMainData()[0].getTemp()) :
                String.format("+%s C", response1.getMainData()[0].getTemp()));

        tvWeatherConditions[0].setText(response1.getWeather().getMainGroupWeatherParams());
        tvWeatherConditions[1].setText(String.format("%s, %s м/с", response1.getWind().getType(),
                String.valueOf(response1.getWind().getSpeed())));

        tvAdditionData[0].setText(String.valueOf(response1.getMainData()[0].getPressure()));
        tvAdditionData[1].setText(String.valueOf(response1.getMainData()[0].getHumidity()));
        tvAdditionData[2].setText(new SimpleDateFormat("HH:mm:ss").format(response1.getSys().getSunrise()));
        tvAdditionData[3].setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(response1.getSys().getSunset()));
    }
}