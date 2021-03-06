package weather_network;

import weather_data.CurrentWeatherSchema;
import weather_data.ForecastResponseSchema;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface OpenWeatherWebservice {

    @GET("data/2.5/weather")
    Call<CurrentWeatherSchema> getCurrentWeather(
            @Query("id") String cityID,
            @Query("appid") String appID,
            @Query("units") String tempFormat);

    @GET("data/2.5/forecast")
    Call<ForecastResponseSchema> getForecastWeather(
            @Query("id") String cityID,
            @Query("appid") String appID,
            @Query("units") String tempFormat,
            @Query("cnt") String count);
}