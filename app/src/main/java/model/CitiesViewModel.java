package model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class CitiesViewModel extends ViewModel implements SearchView.OnQueryTextListener {
    private List<CitySchema> allAvailableCities = new ArrayList<>();
    private List<CitySchema> temp = new ArrayList<>();
    private MutableLiveData<List<CitySchema>> cityListLiveData = new MutableLiveData<>();

    public List<CitySchema> getAllAvailableCities() {
        return allAvailableCities;
    }

    public CitiesViewModel() {
        if (allAvailableCities.size() != 0)
            allAvailableCities.clear();

        allAvailableCities.add(new CitySchema("511565", "Пенза", "RU"));
        allAvailableCities.add(new CitySchema("520555", "Нижний Новгород", "RU"));
        allAvailableCities.add(new CitySchema("2013364", "Владимр", "RU"));
        allAvailableCities.add(new CitySchema("498817", "Санкт Петербург", "RU"));
        allAvailableCities.add(new CitySchema("551487", "Казань", "RU"));
        allAvailableCities.add(new CitySchema("5255", "Нижний Ломов", "RU"));
        allAvailableCities.add(new CitySchema("2013348", "Владивосток", "RU"));
        allAvailableCities.add(new CitySchema("524901", "Москва", "RU"));
        allAvailableCities.add(new CitySchema("520", "Нижний Тагил", "RU"));
        allAvailableCities.add(new CitySchema("2643743", "Лондон", "GB"));
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        temp.clear();
        for (CitySchema citySchema : allAvailableCities) {
            if (citySchema.getCityName().toLowerCase().startsWith(newText.toLowerCase())) {
                temp.add(citySchema);
            }
        }
        cityListLiveData.setValue(temp);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public MutableLiveData<List<CitySchema>> getCityListLiveData() {
        return cityListLiveData;
    }


}
