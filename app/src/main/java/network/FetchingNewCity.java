package network;

import model.CitySchema;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchingNewCity {
    private static final String BASE_URL = "";
    private static FetchingNewCity singleton;

    private FetchingNewCity(String cityName) {
        getCity(cityName);
    }

    public void findCityByName(String cityName) {
        if (singleton == null)
            singleton = new FetchingNewCity(cityName);
        singleton.getCity(cityName);
    }

    private void getCity(String cityName) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GeocodingAPI geocodingAPI = retrofit.create(GeocodingAPI.class);
        geocodingAPI.getNewCity(cityName).enqueue(new Callback<CitySchema>() {
            @Override
            public void onResponse(Call<CitySchema> call, Response<CitySchema> response) {

            }

            @Override
            public void onFailure(Call<CitySchema> call, Throwable t) {

            }
        });

    }


}
