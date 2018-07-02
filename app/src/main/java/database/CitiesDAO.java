package database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;

import cities_data.CitySchema;
import weather_data.CurrentWeatherSchema;

import static cities_data.CitySchema.DB_CITIES;
import static weather_data.CurrentWeatherSchema.DB_CURRENT_WEATHER;

@Dao
public interface CitiesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CitySchema citySchema);

    @Query("SELECT * FROM " + DB_CITIES)
    CitySchema[] queryGetAllCities();

    @Query("SELECT * FROM " + DB_CITIES+ " WHERE cityName =:name")
    CitySchema queryGetCityByName(String name);
}
