package presenters;

import android.view.View;

import com.bumptech.glide.Glide;
import com.igorr.weatheroutlook.R;

import java.text.SimpleDateFormat;

import model.CitiesRU;
import model.ForecastResponseSchema;
import model.ResponseSchema;

public class ForecastPresenter extends Presenter<ForecastResponseSchema> {
    private int actualPos;
    public ForecastPresenter(View viewContainer) {
        super(viewContainer);
    }

    @Override
    public void fillData(ForecastResponseSchema re, Object... position) {
        //Текущая позиция адаптера
        this.actualPos = (Integer) position[0];
        //Название города
        tvCityName.setText(CitiesRU.findCityById(re.getCity().getCityId()));

        //время последнего обновления
        tvTemperatureGroup[0].setText(String.valueOf(re.getList()[actualPos].getLastResponseData()));

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
        tvAdditionData[2].setText(new SimpleDateFormat("HH:mm:ss").format(re.getList()[0].getSys().getSunrise()));
        tvAdditionData[3].setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(re.getList()[actualPos].getSys().getSunset()));
    }
}
