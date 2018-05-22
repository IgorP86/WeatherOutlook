package model;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CitiesRU {

    private static List<City> citiesRu = new ArrayList<>();
    private static CitiesRU citiesRUSingle;

    public static CitiesRU getInstance() {
        if (citiesRUSingle == null) {
            return citiesRUSingle = new CitiesRU();
        } else
            return citiesRUSingle;
    }

    private CitiesRU() {
        if (citiesRu != null)
            citiesRu.clear();

        citiesRu.add(new City("511565", "Пенза", "RU"));
        citiesRu.add(new City("520555", "Нижний Новгород", "RU"));
        citiesRu.add(new City("2013364", "Владимр",  "RU"));
        citiesRu.add(new City("498817", "Санкт Петербург", "RU"));
        citiesRu.add(new City("551487", "Казань", "RU"));
        citiesRu.add(new City("2013348", "Владивосток", "RU"));
        citiesRu.add(new City("524901", "Москва", "RU"));
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


    public static class City {
        private String cityId;
        private String cityName;
        /**
         * имя города на английском
         */
        private String country;

        public City(String cityId, String cityName, String country) {
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
}
