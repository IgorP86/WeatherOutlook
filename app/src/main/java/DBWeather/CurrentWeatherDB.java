package DBWeather;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import model.CurrentWeatherSchema;
import network.LoaderLiveData;

import static com.igorr.weatheroutlook.Preferences.getPreferableCityLong;


@Database(entities = {CurrentWeatherSchema.class}, exportSchema = false, version = 1)
public abstract class CurrentWeatherDB extends RoomDatabase {

    public abstract WeatherDAO weatherDao();

    private static CurrentWeatherDB dbInstance;

    public static CurrentWeatherDB getDB(Context context) {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                    CurrentWeatherDB.class, CurrentWeatherSchema.DB_CURRENT_WEATHER).build();
        }
        return dbInstance;
    }
}