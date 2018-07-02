package weather_data;

import com.google.gson.annotations.SerializedName;


public class ForecastResponseSchema extends ResponseSchema{

    private City city;
    private long cnt;
    private List[] list;

    public City getCity() {
        return city;
    }

    public long getCnt() {
        return cnt;
    }

    public List[] getList() {
        return list;
    }

    public static class List{
        @SerializedName("dt")
        private long lastResponseData;        //Time of data calculation, unix, UTC
        private Main main;
        private Weather[] weather;
        private Clouds clouds;
        private Wind wind;
        private Rain rain;
        private Sys sys;
        private String dt_txt;

        public long getLastResponseData() {
            return lastResponseData;
        }

        public Main getMain() {
            return main;
        }

        public Weather[] getWeather() {
            return weather;
        }

        public Clouds getClouds() {
            return clouds;
        }

        public Wind getWind() {
            return wind;
        }

        public Rain getRain() {
            return rain;
        }

        public Sys getSys() {
            return sys;
        }

        public String getDt_txt() {
            return dt_txt;
        }
    }

    public static class City {
        @SerializedName("id")
        private long cityId;
        @SerializedName("name")
        private String cityName;
        @SerializedName("coord")
        private Coordinates coordinates;
        private String country;

        public long getCityId() {
            return cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public Coordinates getCoordinates() {
            return coordinates;
        }

        public String getCountry() {
            return country;
        }
    }
}
