package presenters;

import android.support.v4.app.Fragment;
import android.view.View;

import com.bumptech.glide.Glide;
import com.igorr.weatheroutlook.R;

import java.text.SimpleDateFormat;

import model.ForecastResponseSchema;
import model.ResponseSchema;

import static network.WeatherFetcher.BASE_API_URL;
import static network.WeatherFetcher.IMAGE;
import static network.WeatherFetcher.PNG;

public class ForecastWeatherPresenter extends Presenter<ForecastResponseSchema> {
    public ForecastWeatherPresenter(View viewContainer) {
        super(viewContainer);
    }

    @Override
    public void fillData(ForecastResponseSchema re, Object... additionData) {
        //Разбор дополнений
        //Текущая позиция адаптера
        int actualPos = (Integer) additionData[0];
        Fragment fragment = (Fragment) additionData[1];
        //Флаг
        imageFlag.setImageDrawable(findFlag(re.getCity().getCountry()));
        //Название города
        tvCityName.setText(re.getCity().getCityName());

        //время последнего обновления
        tvTemperatureGroup[0].setText(new SimpleDateFormat("dd-MM HH:mm").format(re.getList()[actualPos].getLastResponseData()*1000));

        //Изобржение неба
        Glide.with(fragment)
                .load(BASE_API_URL + IMAGE + re.getList()[actualPos].getWeather()[0].getIcon() + PNG)
                .into(imageSky);

        //Направление ветра
        imageWindDirect.setBackgroundResource(R.drawable.ic_arrow_upward_black_24dp);
        imageWindDirect.setRotation(180 + re.getList()[actualPos].getWind().getDegrees());

        //температура
        float temp = re.getList()[actualPos].getMain().getTemperature();
        tvTemperatureGroup[1].setText(temp <= 0 ?
                String.format("%s \u00B0C", temp) : String.format("+%s \u00B0C", temp));

        tvWeatherConditions[0].setText(re.getList()[actualPos].getWeather()[0].getMainWeatherCondition());
        tvWeatherConditions[1].setText(String.format("%s м/с", String.valueOf(re.getList()[actualPos].getWind().getSpeed())));

        tvAdditionData[0].setText(String.valueOf(re.getList()[actualPos].getMain().getPressure() * ResponseSchema.K_PRESSURE));
        tvAdditionData[1].setText(String.valueOf(re.getList()[actualPos].getMain().getHumidity()));
        //Скрыть неиспользуемые поля
        tvAdditionData[2].setVisibility(View.GONE);
        tvAdditionData[3].setVisibility(View.GONE);
        labels[0].setVisibility(View.GONE);
        labels[1].setVisibility(View.GONE);
    }
}
