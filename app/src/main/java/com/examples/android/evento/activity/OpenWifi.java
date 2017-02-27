package com.examples.android.evento.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import java.util.HashMap;
import java.util.List;


public class OpenWifi extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static int APP_REQUEST_CODE = 99;
    StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AccountKit.initialize(getApplicationContext());

        AccessToken accessToken = AccountKit.getCurrentAccessToken();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String phonenumber = prefs.getString("phonenumber", null);


        if (phonenumber != null) {

            connectwifi(phonenumber);

        } else {
            onLoginPhone();

        }


    }


    public void onLoginPhone() {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());

        startActivityForResult(intent, APP_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AccountKitLoginResult loginResult = AccountKit.loginResultWithIntent(data);


        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {

                PhoneNumber phoneNumber = account.getPhoneNumber();
                String phoneNumberString = phoneNumber.toString();


                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("phonenumber", phoneNumberString);
                editor.apply();


                connectwifi(phoneNumberString);

            }


            @Override
            public void onError(final AccountKitError error) {
                // Handle Error

            }
        });


    }


    public void connectwifi(final String phoneNumber) {


        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration wc = new WifiConfiguration();

        if (wifiManager.isWifiEnabled()) {
            int flag = 0;

            List<ScanResult> results = wifiManager.getScanResults();

            for (ScanResult scanResult : results) {
                if (scanResult.SSID.equals("allen"))

                    flag = 1;
            }


            if (flag == 1) {

                wc.SSID = "\"allen\"";
                wc.preSharedKey = "\"12345678\"";
                wc.status = WifiConfiguration.Status.ENABLED;
                wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
// connect to and enable the connection
                int netId = wifiManager.addNetwork(wc);
                wifiManager.enableNetwork(netId, true);
                wifiManager.setWifiEnabled(true);


                new android.app.AlertDialog.Builder(OpenWifi.this)
                        .setTitle("Hasgeek wifi")
                        .setMessage(Html.fromHtml(phoneNumber + "  You are now connected to Hasgeek network"))
                        .setCancelable(true)
                        .setPositiveButton("Ok", null)
                        .create().show();
            } else {
                new android.app.AlertDialog.Builder(OpenWifi.this)
                        .setTitle("Hasgeek wifi")
                        .setMessage(Html.fromHtml("Hasgeek network not in range"))
                        .setCancelable(true)
                        .setPositiveButton("Ok", null)
                        .create().show();

            }


        } else {
            wifiManager.setWifiEnabled(true);
            connectwifi(phoneNumber);
        }

    }


}


