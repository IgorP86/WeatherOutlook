package share;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.igorr.weatheroutlook.Preferences;
import com.igorr.weatheroutlook.R;

import java.net.URI;

import static android.app.Activity.RESULT_OK;

public class ActivityVKShare extends AppCompatActivity {
    private static final String DESTINATION = "https://openweathermap.org/city/";
    private static final String CITY_ID = "cityID";
    private static final String SHARE_REQUEST = "https://vk.com/share.php?url=";

    public static Intent getInstance(Context packageContext){
        return new Intent(packageContext, ActivityVKShare.class)
                .putExtra(CITY_ID, Preferences.getPreferableCityStr(packageContext));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);

        //Распаковка данных
        String qParamCity = getIntent().getStringExtra(CITY_ID);
        //Построение запроса
        String request = SHARE_REQUEST + DESTINATION + qParamCity;

        Log.d("request", request);

        WebView webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new ShareWebViewClient(this));
        webView.loadUrl(URI.create(request).toString());
    }
}

class ShareWebViewClient extends WebViewClient {
    private AppCompatActivity activity;

    public ShareWebViewClient(AppCompatActivity activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        //если запрос прошел успешно:
        if (request.getUrl().toString().startsWith("https://m.vk.com/share.php?act=success")) {
            //Отправить сообщение в MainActivity, закрыть эту активити.
            activity.setResult(RESULT_OK);
            activity.finish();
            return true;
        }
        //обработать следующий url
        return false;
    }
}


//Для получения токена (может пригодится)


//Для авторизации в ВК
   /* private String URL = "https://oauth.vk.com/authorize" +
            "?client_id=6469510" +
            "&display=mobile" +
            "&redirect_uri=https://oauth.vk.com/blank.html" +
            "&scope=friends" +
            "&response_type=token" +
            "&v=5.73";*/

/*class VKwebClient extends WebViewClient {
    private String accessToken;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (request.isRedirect() && request.getUrl().toString().startsWith("https://oauth.vk.com/blank.html#access_token=")) {
            accessToken = getTokenFromResponse(request.getUrl().toString());
            Log.d("TOKEN", accessToken);
            //дальнейшие действия, требующие AccessToken

            //VKwebClient не будет обрабатывать url, а отдаст управление приложнию (активити)
            return true;
        }
        return false;
    }

    @NonNull
    private static String getTokenFromResponse(String url) {
        url = url.split("access_token=")[1];
        for (int i = 0; i != url.length(); i++) {
            if (url.charAt(i) == "&".charAt(0)) {
                return url.substring(0, i);
            }
        }
        return url;
    }
}*/