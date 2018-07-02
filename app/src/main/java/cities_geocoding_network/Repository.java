package cities_geocoding_network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import cities_data.CityLiveData;
import cities_data.CitySchema;
import database.AppDataBase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private static final String BASE_URL = "https://maps.googleapis.com/";
    private static final String APP_KEY = "AIzaSyBJDYxPotDvXvtL8Y-AGlj4V_T4BLJHcI8";

    private static Repository singleton;
    private static AppDataBase appDataBase;

    private Repository() {
    }

    public static void findCityByName(String cityName, Context context) {
        appDataBase = AppDataBase.getDB(context.getApplicationContext());
        if (singleton == null)
            singleton = new Repository();
        singleton.getCity(cityName);
    }

    private void getCity(String cityName) {
        CitySchema citySchema = appDataBase.citiesDAO().queryGetCityByName(cityName);
        if (citySchema == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            GeocodingWebservice geocodingWebservice = retrofit.create(GeocodingWebservice.class);
            geocodingWebservice.getNewCity(APP_KEY, cityName).enqueue(new Callback<CitySchema>() {

                @Override
                public void onResponse(@NonNull Call<CitySchema> call, @NonNull Response<CitySchema> response) {
                    CitySchema citySchema1 = response.body() != null ? response.body().convertInTuple() : null;
                    if (citySchema1 != null)
                        appDataBase.citiesDAO().insert(citySchema1);
                    CityLiveData.getInstance().transferData(citySchema1);
                }

                @Override
                public void onFailure(Call<CitySchema> call, Throwable t) {
                    Log.d("onFailure", t.toString());
                }
            });
        }
    }
}