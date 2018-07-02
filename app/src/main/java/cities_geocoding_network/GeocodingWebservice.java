package cities_geocoding_network;

import cities_data.CitySchema;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingWebservice {
    @GET("maps/api/geocode/json?language=ru")
    Call<CitySchema> getNewCity(
            @Query("key") String key,
            @Query("address") String cityName
    );
}
