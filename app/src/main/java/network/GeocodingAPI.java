package network;

import model.CitySchema;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingAPI {
    @GET("")
    Call<CitySchema> getNewCity(
            @Query("id") String cityID
    );
}
