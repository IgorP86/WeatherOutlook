package model;

import com.google.gson.annotations.SerializedName;

public class ForecastResponseSchema extends ResponseSchema{


    private City city;
    @SerializedName("cnt")
    private long cnt;
    private String country;

    public static class City {
        @SerializedName("id")
        public long cityId;
        @SerializedName("name")
        private String cityName;
        @SerializedName("coord")
        private Coordinates coordinates;
        private String country;
    }
}
