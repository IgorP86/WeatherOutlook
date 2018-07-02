package ui_presenters;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.igorr.weatheroutlook.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

import weather_data.CurrentWeatherSchema;
import weather_data.ResponseSchema;

import static weather_network.WeatherFetcher.BASE_API_URL;
import static weather_network.WeatherFetcher.IMAGE;
import static weather_network.WeatherFetcher.PNG;

public class CurrentWeatherPresenter extends Presenter<CurrentWeatherSchema> {
    public CurrentWeatherPresenter(View viewContainer) {
        super(viewContainer);
    }

    @Override
    public void fillData(CurrentWeatherSchema re, Object... addition) {
        Fragment fragment = (Fragment) addition[0];

        //Изобржение неба
        Glide.with(fragment)
                .load(BASE_API_URL + IMAGE + re.getWeather()[0].getIcon() + PNG)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        resource.setBounds(0, 0, 115, 115);
                        tvWeatherConditions[0].setCompoundDrawables(resource, null, null, null);
                    }
                });

        //Флаг страны и название города
        Drawable d = findFlag(re.getSys().getCountry());
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        tvCityName.setCompoundDrawables(d, null, null, null);
        tvCityName.setText(re.getCityName());

        //время последнего обновления
        tvTemperatureGroup[0].setText(new SimpleDateFormat("HH:mm").format(re.getLastResponseData() * 1000));
        //температура
        float temp = re.getMain().getTemperature();
        tvTemperatureGroup[1].setText(temp <= 0 ?
                String.format(Locale.ENGLISH, "%.0f \u00B0C", temp) : String.format(Locale.ENGLISH, "+%.0f \u00B0C", temp));

        //Направление ветра
        imageWindDirect.setImageDrawable(fragment.getResources().getDrawable(R.drawable.ic_arrow_upward_black_24dp));
        imageWindDirect.setRotation(180 + re.getWind().getDegrees());

        //Ясно-неясно
        tvWeatherConditions[0].setText(re.getWeather()[0].getMainWeatherCondition());
        tvWeatherConditions[1].setText(String.format(Locale.ENGLISH, "%s м/с", String.valueOf(re.getWind().getSpeed())));

        tvAdditionData[0].setText(String.valueOf(re.getMain().getPressure() * ResponseSchema.K_PRESSURE));
        tvAdditionData[1].setText(String.valueOf(re.getMain().getHumidity()));
        tvAdditionData[2].setText(new SimpleDateFormat("HH:mm").format(re.getSys().getSunrise() * 1000));
        tvAdditionData[3].setText(new SimpleDateFormat("HH:mm").format(re.getSys().getSunset() * 1000));
    }
}