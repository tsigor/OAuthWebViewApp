package com.example.oauthwebviewapp;

import android.content.Context;
import android.content.SharedPreferences;

/*
Счел разумным использовать отдельный класс для хранения/чтения настроек/токенов
 */

public class MyPreferences {

    private final static String PREF_FILE = "PREF";
    private static final String TOKEN = "token";

    static Context context;
    public static void init(Context c) {
        context = c;
    }

    public static void setToken(String token) {
        setString(TOKEN, token);
    }

    public static String getToken() {
        return getString(TOKEN,"");
    }

    private static void setString(String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private static void setInt(String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private static void setLong(String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    private static void setBoolean(String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private static String getString(String key, String defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getString(key, defValue);
    }

    private static int getInt(String key, int defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getInt(key, defValue);
    }

    private static long getLong(String key, long defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getLong(key, defValue);
    }

    private static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getBoolean(key, defValue);
    }

}