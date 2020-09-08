package com.example.oauthwebviewapp.user;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("login")
    private String login;

    @SerializedName("name")
    private Object name;



    public String getLogin(){
        return login;
    }

    public void setName(Object name){
        this.name = name;
    }
    public Object getName(){
        return name;
    }


    @Override
    public String toString(){
        return
                "UserResponse{" +
                        "login = '" + login + '\'' +
                        ",name = '" + name + '\'' +
                        "}";
    }

}
