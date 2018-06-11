package model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

public class CurrentWeatherViewModel extends AndroidViewModel {
    private String TEST_string;
    private CurrentWeatherSchema currentWeatherSchema;


    public CurrentWeatherViewModel(@NonNull Application application) {
        super(application);
    }



    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void setTEST_string(String TEST_string) {
        this.TEST_string = TEST_string;
    }

    public String getTEST_string() {
        return TEST_string;
    }
}
