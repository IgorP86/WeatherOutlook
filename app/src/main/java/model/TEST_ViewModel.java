package model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

public class TEST_ViewModel extends AndroidViewModel {
    private String TEST_string;

    public TEST_ViewModel(@NonNull Application application) {
        super(application);
    }

    public void setTEST_string(String TEST_string) {
        this.TEST_string = TEST_string;
    }

    public String getTEST_string() {
        return TEST_string;
    }
}