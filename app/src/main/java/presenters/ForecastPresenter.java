package presenters;

import android.support.v4.app.Fragment;
import android.view.View;

import com.bumptech.glide.Glide;
import com.igorr.weatheroutlook.R;

import java.text.SimpleDateFormat;

import model.CitiesRU;
import model.ForecastResponseSchema;
import model.ResponseSchema;

import static network.WeatherFetcher.BASE_API_URL;
import static network.WeatherFetcher.IMAGE;
import static network.WeatherFetcher.PNG;

public class ForecastPresenter extends Presenter<ForecastResponseSchema> {
    public ForecastPresenter(View viewContainer) {
        super(viewContainer);
    }

    @Override
    public void fillData(ForecastResponseSchema re, Object... additionData) {
        //Разбор дополнений
        //Текущая позиция адаптера
        int actualPos = (Integer) additionData[0];
        Fragment fragment = (Fragment) additionData[1];

        //Название города
        tvCityName.setText(CitiesRU.findCityById(re.getCity().getCityId()));

        //время последнего обновления
        tvTemperatureGroup[0].setText(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(re.getList()[actualPos].getLastResponseData()*1000));

        //Изобржение неба
        Glide.with(fragment)
                .load(BASE_API_URL + IMAGE + re.getList()[actualPos].getWeather()[0].getIcon() + PNG)
                .into(getImageSky());

        //Направление ветра
        imageWindDirect.setBackgroundResource(R.drawable.ic_arrow_upward_black_24dp);
        imageWindDirect.setRotation(180 + re.getList()[actualPos].getWind().getDegrees());

        //температура
        float temp = re.getList()[actualPos].getMain().getTemperature();
        tvTemperatureGroup[1].setText(temp <= 0 ?
                String.format("%s \u00B0C", temp) : String.format("+%s \u00B0C", temp));

        tvWeatherConditions[0].setText(re.getList()[actualPos].getWeather()[0].getMainGroupWeatherParams());
        tvWeatherConditions[1].setText(String.format("%s м/с", String.valueOf(re.getList()[actualPos].getWind().getSpeed())));

        tvAdditionData[0].setText(String.valueOf(re.getList()[actualPos].getMain().getPressure() * ResponseSchema.K_PRESSURE));
        tvAdditionData[1].setText(String.valueOf(re.getList()[actualPos].getMain().getHumidity()));
        tvAdditionData[2].setText(new SimpleDateFormat("HH:mm").format(re.getList()[actualPos].getSys().getSunrise()*1000));
        tvAdditionData[3].setText(new SimpleDateFormat("HH:mm").format(re.getList()[actualPos].getSys().getSunset()*1000));
    }
}
