package DBWeather.type.converters;

import android.arch.persistence.room.TypeConverter;

import model.ResponseSchema;

public class WeatherConverter {
    @TypeConverter
    public String fromWeatherArr(ResponseSchema.Weather[] weather) {
        return String.format("%s;%s", weather[0].getMainWeatherCondition(), weather[0].getIcon());
    }

    @TypeConverter
    public ResponseSchema.Weather[] toWeatherArr(String data) {
        String[] params = data.split(";");
        return new ResponseSchema.Weather[]{new ResponseSchema.Weather(0, params[0], null, params[1])};
    }
}
