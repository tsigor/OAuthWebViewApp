package com.example.oauthwebviewapp.repository;

import com.google.gson.annotations.SerializedName;

public class Repository {
    @SerializedName("name")
    private String mName;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("private")
    private boolean mPrivateX;

    public Repository(String mName, String mDescription, boolean mPrivateX) {
        this.mName = mName;
        this.mDescription = mDescription;
        this.mPrivateX = mPrivateX;
    }

    public String getName() {
        return mName;
    }

}