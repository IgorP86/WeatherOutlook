package model;

import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import org.jetbrains.annotations.NotNull;

public class ResponseSchema {

    private Coordinates coordinates;
    private Weather weather;
    private MainData[] mainData;
    private Wind wind;
    private Sys sys;
    private String base;                //Internal parameter
    private int clouds;                 //all Cloudiness, %
    private int rain;                   //volume for the last 3 hours
    private int snow;                   //volume for the last 3 hours
    private long timeOfDataCalc;        //Time of data calculation, unix, UTC
    private long cityId;                //City ID
    private String name;                //City name
    private byte cod;                   //Internal parameter

    private ResponseSchema(@NotNull Builder b) {
        this.coordinates = b.coordinates;
        this.weather = b.weather;
        this.mainData = b.mainData;
        this.wind = b.wind;
        this.base = b.base;
        this.clouds = b.clouds;
        this.rain = b.rain;
        this.snow = b.snow;
        this.timeOfDataCalc = b.timeOfDataCalc;
        this.cityId = b.cityId;
        this.name = b.name;
        this.cod = b.cod;
    }

    public static class Builder {
        private Coordinates coordinates;
        private Weather weather;
        private MainData[] mainData;
        private Wind wind;
        private Sys sys;
        private String base;
        private int clouds;
        private int rain;
        private int snow;
        private long timeOfDataCalc;
        private long cityId;
        private String name;
        private byte cod;

        public Builder addCoordinates(Coordinates c) {
            coordinates = c;
            return this;
        }

        public Builder addWeather(Weather w) {
            weather = w;
            return this;
        }

        public Builder addMainData(MainData[] md) {
            mainData = md;
            return this;
        }

        public Builder addWind(Wind w) {
            wind = w;
            return this;
        }

        public Builder addSys(Sys s) {
            sys = s;
            return this;
        }

        public Builder addBase(String base) {
            this.base = base;
            return this;
        }

        public Builder addClouds(int clouds) {
            this.clouds = clouds;
            return this;
        }

        public Builder addRain(int rain) {
            this.rain = rain;
            return this;
        }

        public Builder addSnow(int snow) {
            this.snow = snow;
            return this;
        }

        public Builder addCityId(long cityId) {
            this.cityId = cityId;
            return this;
        }

        public Builder addCod(byte cod) {
            this.cod = cod;
            return this;
        }

        public ResponseSchema build() {
            return new ResponseSchema(this);
        }
    }

    public static class Coordinates {
        private float longitude;
        private float latitude;

        public Coordinates(float longitude, float latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public float getLongitude() {
            return longitude;
        }

        public float getLatitude() {
            return latitude;
        }
    }

    public static class Weather {
        private Integer id;                     //id Weather condition
        private String mainGroupWeatherParams;  //Rain, Snow, Extreme etc.
        private String weatherCondition;        //description Weather condition within the group
        private String icon;                    //Weather icon id


        public Weather(int id, String mainGroupWeatherParams, String weatherCondition, String icon) {
            this.id = id;
            this.mainGroupWeatherParams = mainGroupWeatherParams;
            this.weatherCondition = weatherCondition;
            this.icon = icon;
        }

        public Integer getId() {
            return id;
        }

        public String getMainGroupWeatherParams() {
            return mainGroupWeatherParams;
        }

        public String getWeatherCondition() {
            return weatherCondition;
        }

        public String getIcon() {
            return icon;
        }
    }

    public static class MainData {
        private Float temp;         //Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        private Integer pressure;   //Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
        private Integer humidity;   //Humidity, %
        private Float temp_min;     //Minimum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        private Float temp_max;     //Maximum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        private Integer sea_level;  //Atmospheric pressure on the sea level, hPa
        private Integer grnd_level; //Atmospheric pressure on the ground level, hPa

        public MainData(float temp, int pressure, int humidity, float temp_min, float temp_max,
                        int sea_level, int grnd_level) {
            this.temp = temp;
            this.pressure = pressure;
            this.humidity = humidity;
            this.temp_min = temp_min;
            this.temp_max = temp_max;
            this.sea_level = sea_level;
            this.grnd_level = grnd_level;
        }

        public Float getTemp() {
            return temp;
        }

        public Integer getPressure() {
            return pressure;
        }

        public Integer getHumidity() {
            return humidity;
        }

        public Float getTemp_min() {
            return temp_min;
        }

        public Float getTemp_max() {
            return temp_max;
        }

        public Integer getSea_level() {
            return sea_level;
        }

        public Integer getGrnd_level() {
            return grnd_level;
        }
    }

    public static class Wind {
        private Float speed;    //Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
        private Integer deg;    //Wind direction, degrees (meteorological)

        public Wind(float speed, int deg) {
            this.speed = speed;
            this.deg = deg;
        }

        public Float getSpeed() {
            return speed;
        }

        public Integer getDeg() {
            return deg;
        }
    }

    public static class Sys {
        private Byte type;      //Internal parameter
        private Integer id;     // Internal parameter
        private String message; //Internal parameter
        private Byte country;   // Country code (GB, JP etc.)
        private Long sunrise;   //sunrise time, unix, UTC
        private Long sunset;    //Sunset time, unix, UTC

        public Sys(Byte type, Integer id, String message, Byte country, Long sunrise, Long sunset) {
            this.type = type;
            this.id = id;
            this.message = message;
            this.country = country;
            this.sunrise = sunrise;
            this.sunset = sunset;
        }

        public Byte getType() {
            return type;
        }

        public Integer getId() {
            return id;
        }

        public String getMessage() {
            return message;
        }

        public Byte getCountry() {
            return country;
        }

        public Long getSunrise() {
            return sunrise;
        }

        public Long getSunset() {
            return sunset;
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Weather getWeather() {
        return weather;
    }

    public MainData[] getMainData() {
        return mainData;
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

    public Integer getClouds() {
        return clouds;
    }

    public Integer getRain() {
        return rain;
    }

    public Integer getSnow() {
        return snow;
    }

    public Long getTimeOfDataCalc() {
        return timeOfDataCalc;
    }

    public Long getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

    public Byte getCod() {
        return cod;
    }

}