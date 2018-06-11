package network;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;

import com.igorr.weatheroutlook.Preferences;

import java.util.Date;

import DBWeather.CurrentWeatherDB;
import DBWeather.DBHelper;
import model.CurrentWeatherSchema;

public class LoaderLiveData extends LiveData<CurrentWeatherSchema> {
    private static LoaderLiveData singleton;
    private Context context;
    private DBHelper dbHelper;

    public static LoaderLiveData getInstance(Context context) {
        if (singleton == null)
            singleton = new LoaderLiveData(context.getApplicationContext());
        return singleton;
    }

    private LoaderLiveData(Context context) {
        this.context = context;
    }

    public void showDataAndInsertInDB(CurrentWeatherSchema toTransfer) {
        postValue(toTransfer);
        //занести их в БД
        dbHelper.execute(DBHelper.ACTION.INSERT_NOTE, toTransfer);
    }

    /**
     * @param toTransfer данные, которые должны быть переданны наблюдателям
     */
    public void notifyObservers(CurrentWeatherSchema toTransfer) {
        postValue(toTransfer);
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
        //  dbHelper = null;
        // context = null;
    }

    private void getData() {
        new Task().execute(Preferences.getPreferableCityLong(context));
    }

    private class Task extends AsyncTask<Long, Void, Long> {

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
}