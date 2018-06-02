package DBWeather;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import model.CurrentWeatherSchema;

@Database(entities = {CurrentWeatherSchema.class}, exportSchema = false, version = 1)
public abstract class CurrentWeatherDB extends RoomDatabase {

    private static CurrentWeatherDB dbInstance;

    public abstract WeatherDAO weatherDao();

    private static CurrentWeatherDB currentWeatherDB;

    public static CurrentWeatherDB getDB(Context context) {
        if (currentWeatherDB == null) {
            currentWeatherDB = Room.databaseBuilder(context,
                    CurrentWeatherDB.class, CurrentWeatherSchema.DB_CURRENT_WEATHER)
                    .allowMainThreadQueries()
                    .build();
        }
        return currentWeatherDB;
    }
}
