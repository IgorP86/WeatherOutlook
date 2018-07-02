package database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import weather_data.CurrentWeatherSchema;

import static weather_data.CurrentWeatherSchema.DB_CURRENT_WEATHER;

@Dao
public interface WeatherDAO {
    @Query("SELECT * FROM " + DB_CURRENT_WEATHER + " WHERE cityId =:cityID")
    CurrentWeatherSchema queryGetCurrentWeatherForCity(long cityID);

    @Query("SELECT lastResponseData FROM "+ DB_CURRENT_WEATHER+" WHERE cityId =:cityID")
    long queryGetLastResponseDataForCity(long cityID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CurrentWeatherSchema currentWeatherSchema);
}
