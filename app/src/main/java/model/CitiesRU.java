package model;

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
        citiesRu.add(new City("511565", "Пенза"));
        citiesRu.add(new City("520555", "Нижний Новгород"));
        citiesRu.add(new City("2013364", "Владимр"));
        citiesRu.add(new City("498817", "Санкт_Петербург"));
        citiesRu.add(new City("551487", "Казань"));
        citiesRu.add(new City("2013348", "Владивосток"));
    }

    public static List<City> getCitiesRu() {
        return citiesRu;
    }

    public static String findCityById(long cityId) {
        for (City c : citiesRu) {
            if (Long.parseLong(c.cityId) == cityId) {
                return c.name;
            }
        }
        return null;
    }

    public static class City {
        private String cityId;
        private String name;

        private City(String cityId, String name) {
            this.cityId = cityId;
            this.name = name;
        }

        public String getCityId() {
            return cityId;
        }

        public String getName() {
            return name;
        }
    }
}
