package com.examples.android.evento.utils;

/**
 * Created by ankit on 19/1/17.
 */

public class ContactExchangeUtils {

    public static boolean isValidCode(String code) {

        if (code == null)
            return false;

        if (code.length() != 16)
            return false;

        return true;

    }

    public static String getPukFromCode(String code) {
        if (isValidCode(code))
            return code.substring(0, 8);
        return null;
    }

    public static String getKeyFromCode(String code) {
        if (isValidCode(code))
            return code.substring(8);
        return null;
    }
}
