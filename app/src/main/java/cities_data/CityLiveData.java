package cities_data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

public class CityLiveData extends LiveData<CitySchema> {
    private static CityLiveData singleton;

    public static CityLiveData getInstance() {
        if (singleton == null)
            singleton = new CityLiveData();
        return singleton;
    }

    private CityLiveData() {
    }

    public void transferData(@Nullable CitySchema fofTransfer){
        postValue(fofTransfer);
    }
}
