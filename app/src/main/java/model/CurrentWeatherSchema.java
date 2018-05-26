package model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import DBWeather.type.converters.MainConverter;
import DBWeather.type.converters.SysConverter;
import DBWeather.type.converters.WeatherConverter;
import DBWeather.type.converters.WindConverter;

@Entity(tableName = CurrentWeatherSchema.DB_CURRENT_WEATHER)
public class CurrentWeatherSchema extends ResponseSchema {
    @Ignore
    public static final String DB_CURRENT_WEATHER = "dbCurrentWeather";

    @PrimaryKey
    @SerializedName("id")
    private long cityId;
    @TypeConverters({WeatherConverter.class})
    private Weather[] weather;
    @TypeConverters({MainConverter.class})
    private Main main;
    @TypeConverters({WindConverter.class})
    private Wind wind;
    @TypeConverters({SysConverter.class})
    private Sys sys;
    @SerializedName("dt")
    private long lastResponseData;        //Time of data calculation, unix, UTC
    @Ignore
    @SerializedName("coord")
    private Coordinates coordinates;
    @Ignore
    private String base;                //Internal parameter
    @Ignore
    private Clouds clouds;
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

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public void setLastResponseData(long lastResponseData) {
        this.lastResponseData = lastResponseData;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }
}

