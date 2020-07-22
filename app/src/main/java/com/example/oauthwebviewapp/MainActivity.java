package com.example.oauthwebviewapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

/*
https://futurestud.io/tutorials/oauth-2-on-android-with-retrofit

        https://habr.com/ru/post/314028/
        https://habr.com/ru/post/429058/
*/


public class MainActivity extends AppCompatActivity {
/*
Зарегистрируйте свое приложение на GitHub
Создайте проект, в котором будет WebView на первом экране
и возможность создать репозиторий на втором (поля ввода и кнопку отправки запроса).
В WebView отображается экран логина, переходы на сторонние ссылки блокируются/открываются в браузере.
Соответственно, вы должны получить токен и сохранить его в SharedPreferences, чтобы с его помощью могли создать репозиторий.
В тулбаре второго экрана выведите ник авторизовавшегося пользователя. Скоупы, запрос на создание репозитория
Обратите внимание, что REDIRECT_URL, указанный при регистрации приложения и в запросе на токен должны совпадать.
 */

    private WebView webView;
    private ProgressDialog pd;
    private final String clientId = "your-client-id";
    private final String clientSecret = "your-client-secret";
    private final String redirectUri = "your://redirecturi";

/*    https://futurestud.io/tutorials/oauth-2-on-android-with-retrofit*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://github.com/login/oauth/authorize"+ "?client_id="+clientId+"&scope=repo&reditect_uri="+redirectUri));
        startActivity(intent);


    }
 }
