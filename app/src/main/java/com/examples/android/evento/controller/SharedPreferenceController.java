package com.examples.android.evento.controller;

/**
 * Created by ankit on 23/1/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



public class SharedPreferenceController {
    private static SharedPreferences sharedPreferences;

    public static void init(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static void setSharedPref(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String getSharedPref(String key) {
        return sharedPreferences.getString(key, null);
    }

    public static void deleteSharedPref(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

}
