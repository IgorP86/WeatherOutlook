package DBWeather;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import model.CurrentWeatherSchema;

@Database(entities = {CurrentWeatherSchema.class}, exportSchema = false, version = 1)
public abstract class WeatherDB extends RoomDatabase {

    private static WeatherDB dbInstance;

    public abstract WeatherDAO weatherDao();


}
