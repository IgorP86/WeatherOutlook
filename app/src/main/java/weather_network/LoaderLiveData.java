package weather_network;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;

import com.igorr.weatheroutlook.Preferences;

import java.lang.ref.WeakReference;
import java.util.Date;

import database.AppDataBase;
import database.DBHelper;
import weather_data.CurrentWeatherSchema;

public class LoaderLiveData extends LiveData<CurrentWeatherSchema> {
    private WeakReference<Context> context;
    private DBHelper dbHelper;

    private static LoaderLiveData singleton;

    public static LoaderLiveData getInstance(Context context) {
        if (singleton == null)
            singleton = new LoaderLiveData(context.getApplicationContext());
        return singleton;
    }

    private LoaderLiveData(Context context) {
        this.context = new WeakReference<>(context);
    }

    public void showDataAndInsertInDB(CurrentWeatherSchema forTransfer) {
        if (forTransfer != null) {
            postValue(forTransfer);
            //занести их в БД
            dbHelper.execute(DBHelper.ACTION.INSERT_NOTE, forTransfer);
        }
    }

    /**
     * @param forTransfer данные, которые должны быть переданны наблюдателям
     */
    private void notifyObservers(CurrentWeatherSchema forTransfer) {
        setValue(forTransfer);
    }

    @Override
    protected void onActive() {
        super.onActive();
        dbHelper = new DBHelper(context.get(), new Handler(msg -> {
            notifyObservers((CurrentWeatherSchema) msg.obj);
            return false;
        }));
        dbHelper.start();
        getData();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        dbHelper.quit();
    }

    private void getData() {
        new Task(context.get(), dbHelper).execute(Preferences.getPreferableCityLong(context.get()));
    }
}

class Task extends AsyncTask<Long, Void, Long> {
    private WeakReference<Context> context;
    private DBHelper dbHelper;

    Task(Context context, DBHelper dbHelper) {
        this.context = new WeakReference<>(context);
        this.dbHelper = dbHelper;
    }

    @Override
    protected Long doInBackground(Long... cityID) {
        return AppDataBase.getDB(context.get()).weatherDao().queryGetLastResponseDataForCity(cityID[0]);
    }

    @Override
    protected void onPostExecute(Long lastData) {
        super.onPostExecute(lastData);
        long presentData = new Date().getTime();

        if ((float) (presentData / 1000 - lastData) / 3600 >= 0.0) {
            ConnectivityManager cm = ((ConnectivityManager) context.get().getSystemService(Context.CONNECTIVITY_SERVICE));
            NetworkInfo networkInfo = cm != null ? cm.getActiveNetworkInfo() : null;

            if (networkInfo != null && networkInfo.isConnected()) {
                new FetchingCurrentWeather(context.get()).getDataFromNetwork();
            } else {
                dbHelper.execute(DBHelper.ACTION.GET_CURRENT_WEATHER);
            }
        } else {
            dbHelper.execute(DBHelper.ACTION.GET_CURRENT_WEATHER);
        }
    }
}