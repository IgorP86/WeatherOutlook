package oauth.share;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.igorr.weatheroutlook.R;

import java.io.IOException;
import java.net.URI;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityVKShare extends AppCompatActivity {
    private WebView webView;

    //Для авторизации в ВК
    private String URL = "https://oauth.vk.com/authorize" +
            "?client_id=6469510" +
            "&display=mobile" +
            "&redirect_uri=https://oauth.vk.com/blank.html" +
            "&scope=friends" +
            "&response_type=token" +
            "&v=5.73";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);

        String VK_SHARE = getString(R.string.url_vk_share_php);
        String shareContent = getString(R.string.url_openweathermap);

        //Распаковка данных
        String qParamCity = getIntent().getStringExtra(getResources().getString(R.string.str_city_name));

        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(URI.create(VK_SHARE.concat(shareContent).concat(qParamCity)).toString());

        //finish();
    }
}

//Для получения токена (может пригодится)
class VKwebClient extends WebViewClient {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (request.isRedirect() && request.getUrl().toString().startsWith("https://oauth.vk.com/blank.html#access_token=")) {
            String accessToken = getTokenFromResponse(request.getUrl().toString());
            if (accessToken != null) {
                Log.d("TOKEN", accessToken);


                return true;
            }
            return false;
        }
        return false;
    }

    public static String getTokenFromResponse(String url) {
        url = url.split("access_token=")[1];
        for (int i = 0; i != url.length(); i++) {
            if (url.charAt(i) == "&".charAt(0)) {
                return url.substring(0, i);
            }
        }
        return url;
    }
}
//https://vk.com/share.php?url=https://openweathermap.org/find?q=Penza
class Share{
    private OkHttpClient okHttp;
    private String shareContent;

    public Share(){
        okHttp.newCall(new Request.Builder().url("https://vk.com/share.php?url=https://openweathermap.org/find?q=Penza").build())
        .enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });

    }

}