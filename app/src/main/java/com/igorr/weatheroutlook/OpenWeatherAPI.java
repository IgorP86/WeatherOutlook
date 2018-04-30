package com.igorr.weatheroutlook;

import model.ResponseSchema;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OpenWeatherAPI {
    // /data/2.5/weather? id=511565&appid=f45d2f1d63b29740ba59f4999f158e77&units=metric
    @GET("data/2.5/weather")
    Call<ResponseSchema> getResponse(
            @Query("id") String idCity,
            @Query("appid") String appID,
            @Query("units") String tempFormat);
    @GET("data/2.5/weather")

    Call<Byte> getRespons();
}
