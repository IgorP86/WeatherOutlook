package network;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.igorr.weatheroutlook.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import model.ResponseSchema;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import presenters.PresentData;
import presenters.Presenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherFetcher {
    private AppCompatActivity context;
    private volatile Drawable drawableSky;
    private ResponseSchema responseSchema;
    private PresentData presentData;
    private String city;

    public WeatherFetcher(AppCompatActivity context, String cityID) {
        this.context = context;
        this.city = cityID;
        presentData = new Presenter(context);
    }

    public void getData() {
        //Обработчик главного потока
        final Handler uiHandler = new Handler();
        //создать Gson, настроить стратегию десереализации
        //Для примера удалю из ответа несколько полей
        Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(
                new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getName().equals("cod") || f.getName().equals("base");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create();

        //получить экземпляр Retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(context.getString(R.string.baseURL))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        //получить экземпляр интерфейса
        OpenWeatherAPI openWeatherAPI = retrofit.create(OpenWeatherAPI.class);

        //Запустить фоновый поток для получения данных
        openWeatherAPI.getResponse(city,
                context.getString(R.string.appID),
                context.getString(R.string.tempCelsius)
        ).enqueue(new Callback<ResponseSchema>() {
                      @Override
                      public void onResponse(@NonNull Call<ResponseSchema> call, @NonNull Response<ResponseSchema> response) {
                          if (response.isSuccessful()) {
                              responseSchema = response.body();
                              new OkHttpClient().newCall(
                                      new Request.Builder().url(context.getString(R.string.HTTP_ICO_LIST)
                                              + responseSchema.getWeather()[0].getIcon() + context.getString(R.string.dotPNG))
                                              .build())
                                      .enqueue(new okhttp3.Callback() {
                                          @Override
                                          public void onFailure(@NonNull okhttp3.Call call, IOException e) {
                                              Log.i("OkHttpClient", "onFailure");
                                          }

                                          @Override
                                          public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                                              try {
                                                  drawableSky = new BitmapDrawable(context.getResources(),
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
                      public void onFailure(Call<ResponseSchema> call, Throwable t) {
                          Log.i("RETROFIT", "ERROR: " + t.getMessage());
                      }
                  }
        );
    }

    //Пока пускай тут полежит
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + " with " + urlSpec);
            }

            int bytesRead;
            byte[] byteBuffer = new byte[1024];

            while ((bytesRead = in.read(byteBuffer)) > 0) {
                byteArrayOutputStream.write(byteBuffer, 0, bytesRead);
            }

            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
}
