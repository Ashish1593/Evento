package com.examples.android.evento.utils;

/**
 * Created by ankit on 19/1/17.
 */

public class AuthUtils {

    public static String getAuthHeaderFromToken(String token) {
        return "Bearer "+token;
    }
}
