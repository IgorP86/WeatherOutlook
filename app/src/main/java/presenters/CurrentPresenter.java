package presenters;

import android.support.v4.app.Fragment;
import android.view.View;

import com.bumptech.glide.Glide;
import com.igorr.weatheroutlook.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

import model.CitiesRU;
import model.CurrentWeatherSchema;
import model.ResponseSchema;

import static network.WeatherFetcher.BASE_API_URL;
import static network.WeatherFetcher.IMAGE;
import static network.WeatherFetcher.PNG;

public class CurrentPresenter extends Presenter<CurrentWeatherSchema> {
    public CurrentPresenter(View viewContainer) {
        super(viewContainer);
    }

    @Override
    public void fillData(CurrentWeatherSchema re, Object... addition) {
        Fragment fragment = (Fragment) addition[0];
        //Название города
        tvCityName.setText(CitiesRU.findCityById(re.getCityId()));

        //время последнего обновления
        tvTemperatureGroup[0].setText(new java.text.SimpleDateFormat("HH:mm").format(re.getLastResponseData()*1000));
        //Изобржение неба
        Glide.with(fragment)
                .load(BASE_API_URL + IMAGE + re.getWeather()[0].getIcon() + PNG)
                .into(getImageSky());

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

        tvAdditionData[2].setText(new SimpleDateFormat("HH:mm").format(re.getSys().getSunrise()*1000));
        tvAdditionData[3].setText(new SimpleDateFormat("HH:mm").format(re.getSys().getSunset()*1000));
    }
}