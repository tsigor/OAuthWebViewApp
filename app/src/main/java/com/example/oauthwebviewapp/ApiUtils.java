package com.example.oauthwebviewapp;

import com.example.oauthwebviewapp.service.GitHubApi;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {
    private static final String GITHUB_URL = "https://github.com/";
    private static final String GITHUB_API_URL = "https://api.github.com/";

    private static OkHttpClient client;
    private static Retrofit retrofit;
    private static Gson gson;
    private static GitHubApi api;

    public static OkHttpClient getClient(String token) {
        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.authenticator((route, response) -> response.request().newBuilder().header("Authorization", "token " + token).build());

            client = builder.build();
        }
        return client;
    }

    public static OkHttpClient getClientAuth() {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();

        builder.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .build();

            return chain.proceed(request);
        });

        return builder.build();
    }

    public static Retrofit getRetrofit(String token) {
        if (gson == null) {
            gson = new Gson();
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(GITHUB_API_URL)
                    .client(getClient(token))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofitAuth() {
        if (gson == null) {
            gson = new Gson();
        }

        return new Retrofit.Builder()
                .baseUrl(GITHUB_URL)
                .client(getClientAuth())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static GitHubApi getApiService(final String token) {
        if (api == null) {
            api = getRetrofit(token).create(GitHubApi.class);
        }

        return api;
    }

    public static GitHubApi getApiServiceAuth() {
        return getRetrofitAuth().create(GitHubApi.class);
    }
}
