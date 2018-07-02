package cities_data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import database.AppDataBase;

public class CitiesViewModel extends AndroidViewModel implements SearchView.OnQueryTextListener {
    private List<CitySchema> allAvailableCities = new ArrayList<>();
    private List<CitySchema> temp = new ArrayList<>();
    private MutableLiveData<List<CitySchema>> listUpdatedLive = new MutableLiveData<>();
    private CityLiveData dataReceivedLive = CityLiveData.getInstance();

    public CityLiveData getDataReceivedLive() {
        return dataReceivedLive;
    }

    public CitiesViewModel(Application application) {
        super(application);
        Toast.makeText(getApplication()
                , initialization() ? "Список городов загружен" : "Неизвестная ошибка!"
                , Toast.LENGTH_LONG)
                .show();
    }

    public boolean initialization() {
        if (allAvailableCities.size() != 0)
            allAvailableCities.clear();
        allAvailableCities.addAll(Arrays.asList(AppDataBase.getDB(getApplication()).citiesDAO().queryGetAllCities()));
        listUpdatedLive.setValue(allAvailableCities);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        temp.clear();
        for (CitySchema citySchema : allAvailableCities) {
            if (citySchema.getCityName().toLowerCase().startsWith(newText.toLowerCase())) {
                temp.add(citySchema);
            }
        }
        listUpdatedLive.setValue(temp);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public MutableLiveData<List<CitySchema>> getListUpdatedLive() {
        return listUpdatedLive;
    }
}