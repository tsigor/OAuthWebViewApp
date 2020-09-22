package com.example.oauthwebviewapp.service;

import com.example.oauthwebviewapp.model.AccessTokenResponse;

import com.example.oauthwebviewapp.model.AuthAnswer;
import com.example.oauthwebviewapp.model.AuthParameters;
import com.example.oauthwebviewapp.repository.CreateRepositoryRequest;
import com.example.oauthwebviewapp.repository.CreateRepositoryResponse;
import com.example.oauthwebviewapp.repository.Repository;
import com.example.oauthwebviewapp.user.UserRequest;
import com.example.oauthwebviewapp.user.UserResponse;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GitHubApi {


    @POST("user")
    Call<UserResponse> getUser(@Body UserRequest userRequest);


    @GET("user")
    Single<UserResponse> getUser();

    @POST("user/repos")
    Completable registration(@Body Repository repository);  //create repository

    @POST("login/oauth/access_token")
    Single<AuthAnswer> auth(@Body AuthParameters params);

}
