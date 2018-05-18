package network;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.igorr.weatheroutlook.R;

import java.io.IOException;

import model.CurrentWeatherSchema;
import model.ResponseSchema;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import presenters.PresentData;
import presenters.Presenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentWeather extends WeatherFetcher<CurrentWeatherSchema> {
    private Fragment uiContainer;
    private BitmapDrawable drawableSky;
    private Handler uiHandler;
    private PresentData presentData;

    public CurrentWeather(String cityID, Fragment uiContainer) {
        super(cityID);
        this.uiContainer = uiContainer;
        uiHandler = new Handler();
        presentData = new Presenter(uiContainer);
    }
    @Override
    String setRequestType() {
        return TYPE_CURRENT;
    }

    @Override
    Callback<CurrentWeatherSchema> setRetrofitCallback() {
        return new Callback<CurrentWeatherSchema>() {
            @Override
            public void onResponse(@NonNull Call<CurrentWeatherSchema> call, @NonNull Response<CurrentWeatherSchema> response) {
                if (response.isSuccessful()) {
                    responseSchema = response.body();

                    //получив json, загружаю картинку
                    /**/
                    new OkHttpClient().newCall(
                            new Request.Builder().url(BASE_URL + IMAGE + responseSchema.getWeather()[0].getIcon() + PNG)
                                    .build())
                            .enqueue(new okhttp3.Callback() {
                                @Override
                                public void onFailure(@NonNull okhttp3.Call call, IOException e) {
                                    Log.i("OkHttpClient", "onFailure");
                                }

                                @Override
                                public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                                    try {
                                        drawableSky = new BitmapDrawable(uiContainer.getResources(),
                                                BitmapFactory.decodeByteArray(
                                                        response.body().bytes(),
                                                        0, (int) response.body().contentLength()
                                                )
                                        );
                                        //В UI потоке:
                                        uiHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                presentData.fillData(responseSchema, drawableSky);
                                            }
                                        });

                                    } catch (NullPointerException ex) {
                                        Log.e("OkHttpClient", ex.getMessage());
                                    }
                                    Log.i("OkHttpClient", "onResponse");
                                }
                            });
                }
                Log.i("RETROFIT", "onResponse");
            }

            @Override
            public void onFailure(Call<CurrentWeatherSchema> call, Throwable t) {
                Log.i("RETROFIT", "ERROR: " + t.getMessage());
            }
        };
    }
}
