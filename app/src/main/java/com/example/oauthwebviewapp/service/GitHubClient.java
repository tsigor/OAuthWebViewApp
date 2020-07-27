package com.example.oauthwebviewapp.service;

import com.example.oauthwebviewapp.model.AccessToken;
import com.example.oauthwebviewapp.model.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GitHubClient {

    @Headers("Accept: application/json") //rtetrofit add this to the request

    @POST("/login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            //send 3 fields
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("code") String code

    );

    @GET ("/users/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(@Path("user") String user);
}
