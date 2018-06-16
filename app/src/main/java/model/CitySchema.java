package model;

public class CitySchema {

    private String cityId;
    private String cityName;
    private String country;

    public CitySchema(String cityId, String cityName, String country) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

}
