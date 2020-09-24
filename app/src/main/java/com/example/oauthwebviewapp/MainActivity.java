package com.example.oauthwebviewapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.oauthwebviewapp.model.AuthParameters;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*
https://futurestud.io/tutorials/oauth-2-on-android-with-retrofit

        https://habr.com/ru/post/314028/
        https://habr.com/ru/post/429058/
*/

/*
Зарегистрируйте свое приложение на GitHub
Создайте проект, в котором будет WebView на первом экране
и возможность создать репозиторий на втором (поля ввода и кнопку отправки запроса).
В WebView отображается экран логина, переходы на сторонние ссылки блокируются/открываются в браузере.
Соответственно, вы должны получить токен и сохранить его в SharedPreferences, чтобы с его помощью могли создать репозиторий.
В тулбаре второго экрана выведите ник авторизовавшегося пользователя. Скоупы, запрос на создание репозитория
Обратите внимание, что REDIRECT_URL, указанный при регистрации приложения и в запросе на токен должны совпадать.
 */

/*
Already Registered On github
https://github.com/settings/applications/1342416
Client ID
225f92b4f4dafca529ce
Client Secret
e89bdce53af6b5b43f8749200dfde02e4958d18f */

public class MainActivity extends AppCompatActivity {
    private static final String CLIENT_ID = "225f92b4f4dafca529ce";
    private static final String SECRET_KEY = "e89bdce53af6b5b43f8749200dfde02e4958d18f";
    private static final String STATE = "JM2ZKyOsoyJCkRy";
    private static final String REDIRECT_URI = "https://www.google.com";
    private static final String AUTH_SHORT_URL = "https://github.com/login";
    private static final String AUTHORIZATION_URL = "https://github.com/login/oauth/authorize";
    private static final String SCOPE_PARAM = "scope";
    private static final String SCOPE_PARAM_VALUE = "repo";
    private static final String CODE_PARAM = "code";
    private static final String CLIENT_ID_PARAM = "client_id";
    private static final String STATE_PARAM = "state";//
    private static final String REDIRECT_URI_PARAM = "redirect_uri";
    /*---------------------------------------*/
    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUALS = "=";

    private WebView mWebView;
    private ProgressDialog mPd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the webView from the layout
        mWebView = findViewById(R.id.main_activity_web_view);
        //Request focus for the webview
        mWebView.requestFocus(View.FOCUS_DOWN);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        //Show a progress dialog to the user
        mPd = ProgressDialog.show(this, "", this.getString(R.string.loading), true);
        //Set a custom web view client
        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                //Here we describe what we will do after page load finished is
                //This method will be executed each time a page finished loading.
                //The only we do is dismiss the progressDialog, in case we are showing any.
                if(mPd !=null && mPd.isShowing()){
                    mPd.dismiss();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String authorizationUrl) {
                //This method will be called when the Auth proccess redirect to our RedirectUri.
                //We will check the url looking for our RedirectUri.
                if(authorizationUrl.startsWith(REDIRECT_URI)){
                    Log.i(getString(R.string.auth_tag), "startsWith(REDIRECT_URI)");
                    Uri uri = Uri.parse(authorizationUrl);
                    //We take from the url the authorizationToken and the state token. We have to check that the state token returned by the Service is the same we sent.
                    //If not, that means the request may be a result of CSRF and must be rejected.
                    String stateToken = uri.getQueryParameter(STATE_PARAM);
                    if(stateToken==null || !stateToken.equals(STATE)){
                        Log.e(getString(R.string.auth_tag), getString(R.string.state_not_match));
                        return true;
                    }

                    //If the user doesn't allow authorization to our application, the authorizationToken Will be null.
                    String authorizationToken = uri.getQueryParameter(CODE_PARAM);
                    if(authorizationToken==null){
                        Log.i(getString(R.string.auth_tag), getString(R.string.wrong_user));
                        return true;
                    }
                    Log.i(getString(R.string.auth_tag), getString(R.string.auth_token_recieved)+authorizationToken);

                    AuthParameters params = new AuthParameters(CLIENT_ID
                            , SECRET_KEY
                            , authorizationToken
                            , REDIRECT_URI
                            , STATE);

                    ApiUtils.getApiServiceAuth().auth(params)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe(disposable -> mPd = ProgressDialog.show(MainActivity.this, "", MainActivity.this.getString(R.string.loading),true))
                            .doFinally(() -> {if(mPd!=null && mPd.isShowing()){
                                mPd.dismiss();
                            }})
                            .subscribe(answer -> {
                                        if (answer.getAccessToken() != null){
                                            Log.i(getString(R.string.auth_tag), getString(R.string.access_token) + answer.getAccessToken());

                                            SharedPreferences preferences = MainActivity.this.getSharedPreferences(CreateRepositoryActivity.SP_USER_INFO, 0);
                                            SharedPreferences.Editor editor = preferences.edit();

                                            editor.putString(CreateRepositoryActivity.SP_ACCESS_TOKEN_PARAM, answer.getAccessToken());
                                            editor.commit();

                                            Intent startAddRepoActivity = new Intent(MainActivity.this, CreateRepositoryActivity.class);
                                            MainActivity.this.startActivity(startAddRepoActivity);
                                        }
                                    },
                                    throwable -> {});
                }else{
                    //Default behaviour
                    Log.i(getString(R.string.auth_tag),getString(R.string.redirrecting_to)+authorizationUrl);
                    if(authorizationUrl.startsWith(AUTH_SHORT_URL)) {
                        mWebView.loadUrl(authorizationUrl);
                    }
                    else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authorizationUrl));
                        startActivity(intent);
                    }
                }
                return true;
            }
        });


        //Get the authorization Url
        String authUrl = getAuthorizationUrl();
        Log.i(getString(R.string.auth_tag),getString(R.string.loading_url)+authUrl);
        //Load the authorization URL into the webView
        mWebView.loadUrl(authUrl);
    }

    private static String getAuthorizationUrl(){
        return AUTHORIZATION_URL
                + QUESTION_MARK + SCOPE_PARAM + EQUALS + SCOPE_PARAM_VALUE
                + AMPERSAND + CLIENT_ID_PARAM + EQUALS + CLIENT_ID
                + AMPERSAND + STATE_PARAM + EQUALS + STATE
                + AMPERSAND + REDIRECT_URI_PARAM + EQUALS + REDIRECT_URI;

    }


}
