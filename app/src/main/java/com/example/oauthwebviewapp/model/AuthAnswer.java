package com.example.oauthwebviewapp.model;

import com.google.gson.annotations.SerializedName;

public class AuthAnswer {
    @SerializedName("access_token")
    private String mAccessToken;

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String mAccessToken) {
        this.mAccessToken = mAccessToken;
    }
}
