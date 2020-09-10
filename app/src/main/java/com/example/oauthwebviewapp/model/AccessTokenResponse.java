package com.example.oauthwebviewapp.model;

import com.google.gson.annotations.SerializedName;

public class AccessTokenResponse {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("scope")
    private String scope;



    public String getAccessToken() {
        return accessToken;
    }
    public void setScope(String scope){
        this.scope = scope;
    }

    public String getScope(){
        return scope;
    }

    public void setTokenType(String tokenType){
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }
}
