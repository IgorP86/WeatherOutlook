package network;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.igorr.weatheroutlook.Preferences;

import java.util.Date;

import DBWeather.CurrentWeatherDB;
import DBWeather.DBHelper;
import model.CurrentWeatherSchema;

public class LoaderLiveData extends LiveData<CurrentWeatherSchema> {

    private Context context;
    private DBHelper dbHelper;

    private static LoaderLiveData singleton;

    public static LoaderLiveData getInstance(Context context) {
        if (singleton == null)
            singleton = new LoaderLiveData(context.getApplicationContext());
        return singleton;
    }

    private LoaderLiveData(Context context) {
        this.context = context;
    }

    public void showDataAndInsertInDB(CurrentWeatherSchema forTransfer) {
        postValue(forTransfer);
        //занести их в БД
        dbHelper.execute(DBHelper.ACTION.INSERT_NOTE, forTransfer);
    }

    /**
     * @param forTransfer данные, которые должны быть переданны наблюдателям
     */
    public void notifyObservers(CurrentWeatherSchema forTransfer) {
        Log.d("Snackbar", "notifyObservers");
        postValue(forTransfer);
    }

    @Override
    protected void onActive() {
        super.onActive();
        dbHelper = new DBHelper(context, new Handler());
        dbHelper.start();
        getData();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        dbHelper.quit();
    }

    private void getData() {
        new Task(context, dbHelper).execute(Preferences.getPreferableCityLong(context));
    }


}

class Task extends AsyncTask<Long, Void, Long> {
    private Context context;
    private DBHelper dbHelper;

    public Task(Context context, DBHelper dbHelper) {
        this.context = context.getApplicationContext();
        this.dbHelper = dbHelper;
    }

    @Override
    protected Long doInBackground(Long... cityID) {
        return CurrentWeatherDB.getDB(context).weatherDao().queryGetLastResponseDataForCity(cityID[0]);
    }

    @Override
    protected void onPostExecute(Long lastData) {
        super.onPostExecute(lastData);
        long presentData = new Date().getTime();

        if ((float) (presentData / 1000 - lastData) / 3600 >= 0.0) {
            ConnectivityManager cm = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
            NetworkInfo networkInfo = cm != null ? cm.getActiveNetworkInfo() : null;

            if (networkInfo != null && networkInfo.isConnected()) {
                new FetchingCurrentWeather(context).getDataFromNetwork();
            } else {
                dbHelper.execute(DBHelper.ACTION.GET_CURRENT_WEATHER);
            }
        } else {
            dbHelper.execute(DBHelper.ACTION.GET_CURRENT_WEATHER);
        }
    }
}