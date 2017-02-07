package com.examples.android.evento.controller;

/**
 * Created by ankit on 6/2/17.
 */

public class PhoneNumber {



    public static final String Phone_Number = "phonenumber";






    public static void setPhone_Number(String phoneNumber) {
        SharedPreferenceController.setSharedPref(Phone_Number, phoneNumber);
    }

    public static String getPhone_Number() {
        return SharedPreferenceController.getSharedPref(Phone_Number);
    }


}
