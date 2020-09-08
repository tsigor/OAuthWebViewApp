package com.example.oauthwebviewapp.repository;

import com.google.gson.annotations.SerializedName;

public class CreateRepositoryRequest{

    @SerializedName("name")
    private String name;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return
                "CreateRepositoryRequest{" +
                        "name = '" + name + '\'' +
                        "}";
    }
}