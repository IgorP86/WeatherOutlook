package DBWeather;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import model.CurrentWeatherSchema;
import network.LoaderLiveData;

import static com.igorr.weatheroutlook.Preferences.getPreferableCityLong;


@Database(entities = {CurrentWeatherSchema.class}, exportSchema = false, version = 1)
public abstract class CurrentWeatherDB extends RoomDatabase {

    public abstract WeatherDAO weatherDao();

    private static CurrentWeatherDB dbInstance;

    public static CurrentWeatherDB getDB(Context context) {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(context,
                    CurrentWeatherDB.class, CurrentWeatherSchema.DB_CURRENT_WEATHER).build();
        }
        return dbInstance;
    }

    public enum ACTION {
        INSERT_NOTE,
        GET_CURRENT_WEATHER,
        GET_LAST_RESP_DATA,
    }

    public static class DBHelper extends HandlerThread {
        private static final String THREAD_NAME = "DB_Helper";
        private Context context;
        private Handler responseHandler;    //Обработчик ответов (ссылка на главный поток)
        private Handler requestHandler;     //Обработчик запросов в фоновый поток

        public DBHelper(Context context, Handler handler) {
            super(THREAD_NAME);
            this.context = context;
            responseHandler = handler;
        }

        @Override
        public synchronized void start() {
            this.getLooper();
            super.start();
        }

        /**
         * @param cityID идентификатор города
         * @return ответ от БД.
         */
        private synchronized CurrentWeatherSchema getCurrentWeatherForCity(long cityID) {
            return getDB(context).weatherDao().queryGetCurrentWeatherForCity(cityID);
        }

        /**
         * @param response данные для записи в базу.
         */
        private synchronized void insertData(CurrentWeatherSchema response) {
            getDB(context).weatherDao().insert(response);
        }

        @Override
        protected void onLooperPrepared() {
            super.onLooperPrepared();
            requestHandler = new Handler((Message msg) -> {
                if (msg != null) {
                    switch (msg.what) {
                        case 0:
                            if (msg.obj != null)
                                insertData((CurrentWeatherSchema) msg.obj);
                            else
                                Log.d("DB_OPERATIONS", "Данные не добавлены: msg.obj is: null");
                            return false;
                        case 1:
                            final CurrentWeatherSchema data = getCurrentWeatherForCity(getPreferableCityLong(context));
                            responseHandler.post(() -> LoaderLiveData.getInstance(context).notifyObservers(data));
                            return false;
                    }
                }
                return false;
            });
        }

        public void execute(ACTION action, @Nullable CurrentWeatherSchema toInsert) {
            //Собрать сообщение для Handler и передать с пом. sendToTarget
            if (requestHandler != null) {
                Message msg;
                if (toInsert != null)
                    msg = requestHandler.obtainMessage(action.ordinal(), toInsert);
                else
                    msg = requestHandler.obtainMessage(action.ordinal());
                msg.setTarget(requestHandler);
                msg.sendToTarget();
            } else
                Log.d("CurrentWeatherDB", "(requestHandler is null)");
        }
    }
}