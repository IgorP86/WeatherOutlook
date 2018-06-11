package model;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;

public class MyViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static MyViewModelFactory instance;
    private Application application;

    private MyViewModelFactory(Application application) {
        this.application = application;
    }

    public static MyViewModelFactory getFactory(Application application) {
        if (instance == null) {
            return instance = new MyViewModelFactory(application);
        } else
            return instance;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == CurrentWeatherViewModel.class) {
            try {
                return modelClass.getConstructor(Application.class).newInstance(application);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return super.create(modelClass);
    }
}
