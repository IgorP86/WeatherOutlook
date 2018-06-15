package DBWeather;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import model.CurrentWeatherSchema;
import model.CurrentWeatherViewModel;
import network.LoaderLiveData;

import static DBWeather.CurrentWeatherDB.getDB;
import static com.igorr.weatheroutlook.Preferences.getPreferableCityLong;


public class DBHelper extends HandlerThread {
    private static final String TAG = "DB_OPERATIONS";
    private static final String THREAD_NAME = "DB_Helper";
    private static final int INSERT_NOTE = 0;
    private static final int GET_CURRENT_WEATHER = 1;
    private Context context;
    private Handler responseHandler;    //Обработчик ответов (ссылка на главный поток)
    private Handler requestHandler;     //Обработчик запросов в фоновый поток

    public enum ACTION {
        INSERT_NOTE,
        GET_CURRENT_WEATHER,
        GET_LAST_RESP_DATA
    }

    public DBHelper(Context context, Handler handler) {
        super(THREAD_NAME);
        this.context = context;
        responseHandler = handler;
    }

    @Override
    public synchronized void start() {
        super.start();
        getLooper();
    }

    /**
     * @param cityID идентификатор города
     * @return ответ от БД.
     */
    private CurrentWeatherSchema getCurrentWeatherForCity(long cityID) {
        return getDB(context).weatherDao().queryGetCurrentWeatherForCity(cityID);
    }

    /**
     * @param response данные для записи в базу.
     */
    private void insertData(CurrentWeatherSchema response) {
        getDB(context).weatherDao().insert(response);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        Log.d(TAG, "onLooperPrepared()");
        requestHandler = new Handler((Message msg) -> {
            synchronized (this) {
                if (msg != null) {
                    switch (msg.what) {
                        case INSERT_NOTE:
                            if (msg.obj != null) {
                                insertData((CurrentWeatherSchema) msg.obj);
                            } else
                                Log.d(TAG, "Данные не добавлены: msg.obj is: null");
                            return false;
                        case GET_CURRENT_WEATHER:
                            final CurrentWeatherSchema data = getCurrentWeatherForCity(getPreferableCityLong(context));

                            Log.d("Snackbar", "case GET_CURRENT_WEATHER:");
                            responseHandler.post(() -> LoaderLiveData.getInstance(context).notifyObservers(data));
                            return false;
                    }
                }
            }
            return false;
        });
    }

    public void execute(ACTION action, CurrentWeatherSchema... toInsert) {
        if (requestHandler != null) {
            Log.d(TAG, requestHandler.toString());
            requestHandler.sendMessage(requestHandler.obtainMessage(action.ordinal(), toInsert.length != 0 ? toInsert[0] : null));
        } else
            Log.d("CurrentWeatherDB", "(requestHandler is null)");
    }
}

