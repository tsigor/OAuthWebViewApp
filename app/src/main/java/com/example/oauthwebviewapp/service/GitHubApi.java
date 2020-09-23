package com.example.oauthwebviewapp.service;


import com.example.oauthwebviewapp.model.AuthAnswer;
import com.example.oauthwebviewapp.model.AuthParameters;
import com.example.oauthwebviewapp.repository.Repository;
import com.example.oauthwebviewapp.user.UserResponse;

import io.reactivex.Completable;
import io.reactivex.Single;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GitHubApi {




    @GET("user")
    Single<UserResponse> getUser();

    @POST("user/repos")
    Completable registration(@Body Repository repository);  //create repository

    @POST("login/oauth/access_token")
    Single<AuthAnswer> auth(@Body AuthParameters params);

}
