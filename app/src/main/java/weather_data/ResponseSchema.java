package weather_data;

import com.google.gson.annotations.SerializedName;


public abstract class ResponseSchema {
    public static final float K_PRESSURE = 0.750063755419211f;  //Коэф. для перевода давления в мм.рт.ст

    public static class Coordinates {
        @SerializedName("lon")
        private float longitude;
        @SerializedName("lat")
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
        private int id;                         //id Weather condition
        @SerializedName("main")
        private String mainWeatherCondition;  //rain, Snow, Extreme etc.
        @SerializedName("description")
        private String addWeatherCondition;        //description Weather condition within the group
        private String icon;                    //Weather icon id


        public Weather(int id, String mainWeatherCondition, String addWeatherCondition, String icon) {
            this.id = id;
            this.mainWeatherCondition = mainWeatherCondition;
            this.addWeatherCondition = addWeatherCondition;
            this.icon = icon;
        }

        public Integer getId() {
            return id;
        }

        public String getMainWeatherCondition() {
            return mainWeatherCondition;
        }

        public String getAddWeatherCondition() {
            return addWeatherCondition;
        }

        public String getIcon() {
            return icon;
        }
    }

    public static class Main {
        @SerializedName("temp")
        private float temperature;  //Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        private float pressure;     //Atmospheric pressure (on the sea level, if there is no seaLevel or grndLevel data), hPa
        private int humidity;       //Humidity, %
        @SerializedName("temp_min")
        private float tempMin;      //Minimum temperature at the moment. This is deviation from current temperature that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        @SerializedName("temp_max")
        private float tempMax;      //Maximum temperature at the moment. This is deviation from current temperature that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        @SerializedName("sea_level")
        private float seaLevel;     //Atmospheric pressure on the sea level, hPa
        @SerializedName("grnd_level")
        private float grndLevel;    //Atmospheric pressure on the ground level, hPa

        public Main(float temperature, float pressure, int humidity, float tempMin, float tempMax,
                    float seaLevel, float grndLevel) {
            this.temperature = temperature;
            this.pressure = pressure;
            this.humidity = humidity;
            this.tempMin = tempMin;
            this.tempMax = tempMax;
            this.seaLevel = seaLevel;
            this.grndLevel = grndLevel;
        }

        public float getTemperature() {
            return temperature;
        }

        public float getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public float getTempMin() {
            return tempMin;
        }

        public float getTempMax() {
            return tempMax;
        }

        public float getSeaLevel() {
            return seaLevel;
        }

        public float getGrndLevel() {
            return grndLevel;
        }
    }

    public static class Clouds {
        @SerializedName("all")
        private int cloudness;

        public Clouds(int cloudness) {
            this.cloudness = cloudness;
        }

        public int getCloudness() {
            return cloudness;
        }
    }

    public static class Wind {
        private float speed;    //Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
        @SerializedName("deg")
        private float degrees;    //Wind direction, degrees (meteorological)

        public Wind(float speed, float degrees) {
            this.speed = speed;
            this.degrees = degrees;
        }

        public float getSpeed() {
            return speed;
        }

        public float getDegrees() {
            return degrees;
        }
    }

    public static class Sys {
        private String message; //Internal parameter
        private String country;  // Country code (GB, JP etc.)
        private long sunrise;   //sunrise time, unix, UTC
        private long sunset;    //Sunset time, unix, UTC

        public Sys(String message, String country, long sunrise, long sunset) {
            this.message = message;
            this.country = country;
            this.sunrise = sunrise;
            this.sunset = sunset;
        }

        public String getMessage() {
            return message;
        }

        public String getCountry() {
            return country;
        }

        public long getSunrise() {
            return sunrise;
        }

        public long getSunset() {
            return sunset;
        }
    }

    public static class Rain {
        @SerializedName("3h")
        private float rain;
    }

    public static class Snow {
        @SerializedName("3h")
        private int snow;
    }
}