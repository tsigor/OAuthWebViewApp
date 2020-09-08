package com.example.oauthwebviewapp.repository;

import com.google.gson.annotations.SerializedName;

public class CreateRepositoryResponse {
    @SerializedName("html_url")
    private String htmlUrl;

    public String getHtmlUrl(){
        return htmlUrl;
    }

}
