package com.example.oauthwebviewapp.model;

import com.google.gson.annotations.SerializedName;

public class AuthParameters {
    @SerializedName("client_id")
    private String mClientID;

    @SerializedName("client_secret")
    private String mClientSecret;

    @SerializedName("code")
    private String mCode;

    @SerializedName("redirect_uri")
    private String mRedirectURI;

    @SerializedName("state")
    private String mState;

    public AuthParameters(String mClientID, String mClientSecret, String mCode, String mRedirectURI, String mState) {
        this.mClientID = mClientID;
        this.mClientSecret = mClientSecret;
        this.mCode = mCode;
        this.mRedirectURI = mRedirectURI;
        this.mState = mState;
    }
}

