package database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import cities_data.CitySchema;
import weather_data.CurrentWeatherSchema;

@Database(entities = {CurrentWeatherSchema.class, CitySchema.class}, exportSchema = false, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract WeatherDAO weatherDao();

    public abstract CitiesDAO citiesDAO();

    private static AppDataBase dbInstance;

    public static AppDataBase getDB(Context context) {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDataBase.class, CurrentWeatherSchema.DB_CURRENT_WEATHER)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
        }
        return dbInstance;
    }
}