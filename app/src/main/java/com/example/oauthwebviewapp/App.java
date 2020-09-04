package com.example.oauthwebviewapp;

import android.app.Application;
import android.app.ProgressDialog;
import android.webkit.WebView;

import com.example.oauthwebviewapp.service.GitHubApi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
/*
Зарегистрируйте свое приложение на GitHub
Создайте проект, в котором будет WebView на первом экране
и возможность создать репозиторий на втором (поля ввода и кнопку отправки запроса).
В WebView отображается экран логина, переходы на сторонние ссылки блокируются/открываются в браузере.
Соответственно, вы должны получить токен и сохранить его в SharedPreferences, чтобы с его помощью могли создать репозиторий.
В тулбаре второго экрана выведите ник авторизовавшегося пользователя. Скоупы, запрос на создание репозитория
Обратите внимание, что REDIRECT_URL, указанный при регистрации приложения и в запросе на токен должны совпадать.
 */


    static GitHubApi gitHubApi;

    public static final String CLIENT_ID = "225f92b4f4dafca529ce";
    public static final String CLIENT_SECRET = "e89bdce53af6b5b43f8749200dfde02e4958d18f";

    @Override
    public void onCreate() {
        super.onCreate();
        MyPreferences.init(this);
        initApi();
    }

    public static void initApi() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", "token " + MyPreferences.getToken());
                        Request request = requestBuilder.build();
                        Response response = chain.proceed(request);
                        return response;
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gitHubApi = retrofit.create(GitHubApi.class);
    }

    public static GitHubApi getGitHubApi(){
        return gitHubApi;
    }

}



