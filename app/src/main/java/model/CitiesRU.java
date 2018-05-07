package model;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CitiesRU {

    private static List<City> citiesRu = new ArrayList<>();
    private static CitiesRU citiesRUSingle;

    public static CitiesRU getInstance() {
        if (citiesRUSingle == null) {
            citiesRUSingle = new CitiesRU();
            return citiesRUSingle;
        } else
            return citiesRUSingle;
    }

    private CitiesRU() {
        if (citiesRu != null)
            citiesRu.clear();

        citiesRu.add(new City("511565", "Пенза", "Penza", "RU"));
        citiesRu.add(new City("520555", "Нижний Новгород", "Nizhniy Novgorod", "RU"));
        citiesRu.add(new City("2013364", "Владимр", "Vladimir", "RU"));
        citiesRu.add(new City("498817", "Санкт_Петербург", "Saint Petersburg", "RU"));
        citiesRu.add(new City("551487", "Казань", "Kazan", "RU"));
        citiesRu.add(new City("2013348", "Владивосток", "Vladivostok", "RU"));
        citiesRu.add(new City("524901", "Москва", "Moscow", "RU"));
    }

    public static List<City> getCitiesRu() {
        return citiesRu;
    }

    @Nullable
    public static String findCityById(long cityId) {
        for (City c : citiesRu) {
            if (Long.parseLong(c.cityId) == cityId) {
                return c.cityName;
            }
        }
        return null;
    }

    @Nullable
    public static String getAlterName(String cityName) {
        for (City c : citiesRu) {
            if (c.getCityName().equals(cityName)) {
                return c.alternateName;
            }
        }
        return null;
    }

    public static class City {
        private String cityId;
        private String cityName;
        /**
         * имя города на английском
         */
        private String alternateName;
        private String country;

        public City(String cityId, String cityName, String alternateName, String country) {
            this.cityId = cityId;
            this.cityName = cityName;
            this.alternateName = alternateName;
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
}
