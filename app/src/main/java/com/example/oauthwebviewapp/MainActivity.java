package com.example.oauthwebviewapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

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
    private final String clientId = "225f92b4f4dafca529ce";
    private final String clientSecret = "e89bdce53af6b5b43f8749200dfde02e4958d18f";
    private final String redirectUri = "https://www.google.com";

/*    https://futurestud.io/tutorials/oauth-2-on-android-with-retrofit*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://github.com/login/oauth/authorize"+ "?client_id="+clientId+"&scope=repo&reditect_uri="+redirectUri));
        startActivity(intent);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = getIntent().getData();
        if (uri != null){
            Toast.makeText(this,"yaou",Toast.LENGTH_SHORT).show();
        }

    }

 }
