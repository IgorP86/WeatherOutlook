package model;

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
        this.sys = b.sys;
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

        public Builder addTimeOfDataCalc(long timeOfDataCalc) {
            this.timeOfDataCalc = timeOfDataCalc;
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

        public float getTemp() {
            return temp;
        }

        public int getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public float getTemp_min() {
            return temp_min;
        }

        public float getTemp_max() {
            return temp_max;
        }

        public int getSea_level() {
            return sea_level;
        }

        public int getGrnd_level() {
            return grnd_level;
        }
    }

    public static class Wind {
        private float speed;    //Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
        private int deg;    //Wind direction, degrees (meteorological)
        private String type;

        public Wind(float speed, String type, int deg) {
            this.speed = speed;
            this.deg = deg;
            this.type = type;
        }

        public float getSpeed() {
            return speed;
        }

        public int getDeg() {
            return deg;
        }

        public String getType() {
            return type;
        }
    }

    public static class Sys {
        private int type;      //Internal parameter
        private int id;     // Internal parameter
        private String message; //Internal parameter
        private int country;   // Country code (GB, JP etc.)
        private long sunrise;   //sunrise time, unix, UTC
        private long sunset;    //Sunset time, unix, UTC

        public Sys(int type, int id, String message, int country, long sunrise, long sunset) {
            this.type = type;
            this.id = id;
            this.message = message;
            this.country = country;
            this.sunrise = sunrise;
            this.sunset = sunset;
        }

        public int getType() {
            return type;
        }

        public int getId() {
            return id;
        }

        public String getMessage() {
            return message;
        }

        public int getCountry() {
            return country;
        }

        public long getSunrise() {
            return sunrise;
        }

        public long getSunset() {
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