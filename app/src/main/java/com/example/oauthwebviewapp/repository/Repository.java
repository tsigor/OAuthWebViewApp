package com.example.oauthwebviewapp.repository;

import com.google.gson.annotations.SerializedName;

public class Repository {
    @SerializedName("name")
    private String mName;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("homepage")
    private String mHomepage;

    @SerializedName("private")
    private boolean mPrivateX;

    public Repository(String mName, String mDescription, String mHomepage, boolean mPrivateX) {
        this.mName = mName;
        this.mDescription = mDescription;
        this.mHomepage = mHomepage;
        this.mPrivateX = mPrivateX;
    }
}