package DBWeather;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import model.CurrentWeatherSchema;

public interface WeatherDAO {
    @Query("SELECT * FROM "+ CurrentWeatherSchema.DB_CURRENT_WEATHER)
    List<CurrentWeatherSchema> get_TEST_weather();

    @Insert
    void insert(CurrentWeatherSchema currentWeatherSchema);

    @Update
    void update(CurrentWeatherSchema currentWeatherSchema);

    @Delete
    void delete(CurrentWeatherSchema currentWeatherSchema);
}
