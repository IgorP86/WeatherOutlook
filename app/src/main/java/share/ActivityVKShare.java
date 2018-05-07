package share;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.igorr.weatheroutlook.R;

import java.net.URI;

import static android.app.Activity.RESULT_OK;

public class ActivityVKShare extends AppCompatActivity {
    private WebView webView;

    //Для авторизации в ВК
   /* private String URL = "https://oauth.vk.com/authorize" +
            "?client_id=6469510" +
            "&display=mobile" +
            "&redirect_uri=https://oauth.vk.com/blank.html" +
            "&scope=friends" +
            "&response_type=token" +
            "&v=5.73";*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);

        String VK_SHARE = getString(R.string.url_vk_share_php);
        String shareContent = getString(R.string.url_openweathermap);

        //Распаковка данных
        String qParamCity = getIntent().getStringExtra(getResources().getString(R.string.str_city_name));

        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new ShareWebViewClient(this));
        webView.loadUrl(URI.create(VK_SHARE.concat(shareContent).concat(qParamCity)).toString());
    }
}

class ShareWebViewClient extends WebViewClient{
    private AppCompatActivity activity;

    public ShareWebViewClient(AppCompatActivity activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        //если запрос прошел успешно:
        if (request.getUrl().toString().startsWith("https://m.vk.com/share.php?act=success")){
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