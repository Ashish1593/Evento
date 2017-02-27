package com.examples.android.evento.controller;

/**
 * Created by ankit on 17/1/17.
 */
public class AuthController {

    public static final String AUTH_TOKEN_KEY = "auth_token_key";

    public static boolean isLoggedIn() {
        if (getAuthToken() != null)
            return true;
        return false;
    }


    public static void saveAuthToken(String authToken) {
        SharedPreferenceController.setSharedPref(AUTH_TOKEN_KEY, authToken);
    }

    public static String getAuthToken() {
        return SharedPreferenceController.getSharedPref(AUTH_TOKEN_KEY);
    }

    public static void deleteAuthToken() {
        SharedPreferenceController.deleteSharedPref(AUTH_TOKEN_KEY);
    }

}

