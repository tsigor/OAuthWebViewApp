package com.example.oauthwebviewapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.oauthwebviewapp.model.AccessTokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
https://futurestud.io/tutorials/oauth-2-on-android-with-retrofit

        https://habr.com/ru/post/314028/
        https://habr.com/ru/post/429058/
*/


/*
Already Registered On github
https://github.com/settings/applications/1342416
Client ID
225f92b4f4dafca529ce
Client Secret
e89bdce53af6b5b43f8749200dfde02e4958d18f */

public class MainActivity extends AppCompatActivity {

    public final String REDIRECT_HOST = "www.google.com";

/*    https://futurestud.io/tutorials/oauth-2-on-android-with-retrofit*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView =  findViewById(R.id.main_activity_web_view); //get the webView from the layout

        WebViewClient webViewClient = new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri url = request.getUrl();
                String host = url.getHost();
                if(host.equals("github.com")){
                    return false;
                } else if(host.equals(REDIRECT_HOST)){
                    String code = url.getQueryParameter("code");
                    App.getGitHubApi().getAccessToken(App.CLIENT_ID, App.CLIENT_SECRET, code)
                            .enqueue(new Callback<AccessTokenResponse>() {
                                @Override
                                public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                                    MyPreferences.setToken(response.body().getAccessToken());
                                    App.initApi();
                                    startActivity(new Intent(MainActivity.this, CreateRepositoryActivity.class));
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<AccessTokenResponse> call, Throwable t) {

                                }
                            });

                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                    startActivity(intent);
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        };
        webView.setWebViewClient(webViewClient);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl("https://github.com/login/oauth/authorize?client_id="+App.CLIENT_ID+"&scope=user%20repo");

    }

}
