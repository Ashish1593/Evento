package com.examples.android.evento.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

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





public class OpenWifi extends AppCompatActivity {
    public static int APP_REQUEST_CODE = 99;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AccountKit.initialize(getApplicationContext());

        AccessToken accessToken = AccountKit.getCurrentAccessToken();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String phonenumber = prefs.getString("phonenumber", null);


        if ( phonenumber != null) {

            connectwifi(phonenumber);

        }
        else {
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

        final String accessToken = loginResult.getAccessToken().getToken();
//        if (requestCode == APP_REQUEST_CODE)
//        {




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


    public void connectwifi(String phoneNumber){

        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", "TP_LINK");
        wifiConfig.preSharedKey = String.format("\"%s\"", "wififiwi");

        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        } else {
            wifiManager.setWifiEnabled(true);
        }
        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();



        new android.app.AlertDialog.Builder(OpenWifi.this)
                .setTitle("AlertDialog Title")
                .setMessage(Html.fromHtml(phoneNumber + "Simple Dialog Message"))
                .setCancelable(true)
                .setPositiveButton("Ok",null)
                .create().show();
//
//

    }


}
