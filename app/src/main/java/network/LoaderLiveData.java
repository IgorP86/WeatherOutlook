package network;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;

import com.igorr.weatheroutlook.Preferences;

import java.util.Date;

import DBWeather.CurrentWeatherDB;
import model.CurrentWeatherSchema;

public class LoaderLiveData extends LiveData<CurrentWeatherSchema> {
    private static LoaderLiveData singleton;
    private Context context;

    public static LoaderLiveData getInstance(Context context) {
        if (singleton == null)
            singleton = new LoaderLiveData(context);
        return singleton;
    }

    private LoaderLiveData(Context context) {
        this.context = context;
    }

    public void transfer(CurrentWeatherSchema toTransfer) {
        postValue(toTransfer);
        //занести их в БД
        CurrentWeatherDB.getDB(context).weatherDao().insert(toTransfer);
    }

    @Override
    protected void onActive() {
        super.onActive();
        getData();
    }

    private void getData() {

        //Получить город из настроек
        long cityID = Preferences.getPreferableCityLong(context);
        //проверить дату последней записи
        long lastData = CurrentWeatherDB.getDB(context).weatherDao().queryGetLastResponseDataForCity(cityID);
        long presentData = new Date().getTime();

        //Если времени прошло > 3 часов, проверить сеть и пытаться загрузить
        //если нет, грузить с БД
        if ((float) (presentData / 1000 - lastData) / 3600 > 0.0) {
            ConnectivityManager cm = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
            NetworkInfo networkInfo = cm != null ? cm.getActiveNetworkInfo() : null;

            if (networkInfo != null && networkInfo.isConnected()) {
                new FetchingCurrentWeather(context.getApplicationContext()).getDataFromNetwork();
            } else {
                getFromDB(CurrentWeatherDB.getDB(context), cityID);
            }
        } else {
            getFromDB(CurrentWeatherDB.getDB(context), cityID);
        }
    }

    private void getFromDB(CurrentWeatherDB db, long cityID) {
        postValue(db.weatherDao().queryGetCurrentWeatherForCity(cityID));
    }
}