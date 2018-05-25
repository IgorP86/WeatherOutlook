package DBWeather.type.converters;

import android.arch.persistence.room.TypeConverter;

import java.lang.reflect.Field;

import model.ResponseSchema;

public class WeatherConverter {
    Field field;

    @TypeConverter
    public String fromWeatherArr(ResponseSchema.Weather[] weather){
        return weather[0].

        //return hobbies.stream().collect(Collectors.joining(","));
    }
}
