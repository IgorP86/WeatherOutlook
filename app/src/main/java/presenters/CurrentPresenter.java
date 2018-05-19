package presenters;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
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
import model.ForecastResponseSchema;
import model.ResponseSchema;

public class CurrentPresenter extends Presenter<CurrentWeatherSchema> {
    public CurrentPresenter(View viewContainer) {
        super(viewContainer);
    }

    @Override
    public void fillData(CurrentWeatherSchema re, Object... drawables) {
        //Название города
        tvCityName.setText(CitiesRU.findCityById(re.getCityId()));

        //время последнего обновления
        tvTemperatureGroup[0].setText(String.valueOf(re.getLastResponseData()));

        //Изобржение неба
        imageSky.setImageDrawable((Drawable) drawables[0]);

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
}