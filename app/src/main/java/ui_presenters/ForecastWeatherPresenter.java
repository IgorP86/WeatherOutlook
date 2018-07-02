package ui_presenters;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.igorr.weatheroutlook.FragmentForecastOnFewDays;
import com.igorr.weatheroutlook.R;

import java.text.SimpleDateFormat;

import weather_data.ForecastResponseSchema;
import weather_data.ResponseSchema;

import static weather_network.WeatherFetcher.BASE_API_URL;
import static weather_network.WeatherFetcher.IMAGE;
import static weather_network.WeatherFetcher.PNG;

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
        Drawable d = findFlag(re.getCity().getCountry());
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        tvCityName.setCompoundDrawables(d, null, null, null);
        //Название города
        tvCityName.setText(re.getCity().getCityName());

        //время последнего обновления
        tvTemperatureGroup[0].setText(new SimpleDateFormat("dd-MM HH:mm").format(re.getList()[actualPos].getLastResponseData() * 1000));

        //Изобржение неба
        Glide.with(fragment)
                .load(BASE_API_URL + IMAGE + re.getList()[actualPos].getWeather()[0].getIcon() + PNG)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        resource.setBounds(0, 0, 115, 115);
                        tvWeatherConditions[0].setCompoundDrawables(resource, null, null, null);
                    }
                });
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
