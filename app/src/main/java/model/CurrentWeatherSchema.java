package model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = CurrentWeatherSchema.DB_CURRENT_WEATHER)
public class CurrentWeatherSchema extends ResponseSchema {
    @Ignore
    public static final String DB_CURRENT_WEATHER = "dbCurrentWeather";

    @PrimaryKey
    @SerializedName("id")
    private long cityId;                //City ID
    @Ignore
    @SerializedName("coord")
    private Coordinates coordinates;
    private Weather[] weather;
    private Main main;
    private Wind wind;
    private Sys sys;
    private String base;                //Internal parameter
    private Clouds clouds;              //all Cloudiness, %
    private Rain rain;                  //volume for the last 3 hours
    private Snow snow;                   //volume for the last 3 hours
    @SerializedName("dt")
    private long lastResponseData;        //Time of data calculation, unix, UTC
    @Ignore
    @SerializedName("name")
    private String cityName;            //City cityName
    @Ignore
    private byte cod;                   //Internal parameter

    public Weather[] getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Sys getSys() {
        return sys;
    }

    public String getBase() {
        return base;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Rain getRain() {
        return rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public long getLastResponseData() {
        return lastResponseData;
    }

    public long getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public byte getCod() {
        return cod;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

}
