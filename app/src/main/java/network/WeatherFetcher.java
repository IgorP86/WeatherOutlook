package network;

import android.content.Context;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.igorr.weatheroutlook.Preferences;

import model.ResponseSchema;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class WeatherFetcher<T extends ResponseSchema>{
    protected String cityID;

    //query params
    protected static final String APP_ID = "f45d2f1d63b29740ba59f4999f158e77";
    protected static final String[] LIMIT = {"1", "2", "3", "4", "5"};
    public static final String PNG = ".png";

    enum MeasureUnits {
        CELSIUS("metric"), FAHRENHEIT("imperial");
        protected String unit;

        MeasureUnits(String unit) {
            this.unit = unit;
        }
    }

    //url's
    public static final String BASE_API_URL = "http://api.openweathermap.org/";
    public static final String IMAGE = "img/w/";
    public static final String TYPE_CURRENT = "weather";
    public static final String TYPE_FORECAST = "forecast";

    abstract Callback<T> setRetrofitCallback();

    /**
     * @return TYPE_CURRENT || TYPE_FORECAST
     */
    abstract String setRequestType();

    public WeatherFetcher(Context context) {
        //Город брать из настроек SharedPref
        this.cityID = Preferences.getPreferableCity(context);
    }

    public void getDataFromNetwork() {
        //создать Gson, настроить стратегию десереализации
        //Для примера удалю из ответа несколько полей
        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(
                new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getName().equals("cod") || f.getName().equals("base");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create();

        //получить экземпляр Retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        //получить экземпляр интерфейса
        OpenWeatherAPI openWeatherAPI = retrofit.create(OpenWeatherAPI.class);
        //Получение параметров
        Callback retrofitCallback = setRetrofitCallback();
        String requestType = setRequestType();

        if (retrofitCallback != null) {
            switch (requestType) {
                case TYPE_CURRENT:
                    openWeatherAPI.getCurrentWeather(cityID, APP_ID, MeasureUnits.CELSIUS.unit)
                            .enqueue(retrofitCallback);
                    break;
                case TYPE_FORECAST:
                    openWeatherAPI.getForecastWeather(cityID, APP_ID, MeasureUnits.CELSIUS.unit, "10")
                            .enqueue(retrofitCallback);
                    break;
            }
        }
    }
}