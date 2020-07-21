package com.example.oauthwebviewapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

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


/*    https://futurestud.io/tutorials/oauth-2-on-android-with-retrofit*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the webView from the layout
        webView = (WebView) findViewById(R.id.main_activity_web_view);

        //Request focus for the webview
        webView.requestFocus(View.FOCUS_DOWN);

        //Show a progress dialog to the user
        pd = ProgressDialog.show(this, "", this.getString(R.string.loading),true);



    }
}
