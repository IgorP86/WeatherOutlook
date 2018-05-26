package DBWeather;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import model.CurrentWeatherSchema;

import static model.CurrentWeatherSchema.DB_CURRENT_WEATHER;

@Dao
public interface WeatherDAO {
    @Query("SELECT * FROM " + DB_CURRENT_WEATHER + " WHERE cityId =:cityID")
    CurrentWeatherSchema queryGetCurrentWeatherForCity(long cityID);

    @Query("SELECT lastResponseData FROM "+ DB_CURRENT_WEATHER+" WHERE cityId =:cityID")
    long queryGetLastResponseDataForCity(long cityID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CurrentWeatherSchema currentWeatherSchema);
}
