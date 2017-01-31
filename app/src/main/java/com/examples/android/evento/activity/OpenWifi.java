package com.examples.android.evento.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;

import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;



/**
 * Created by ankit on 27/12/16.
 */


public class OpenWifi extends AppCompatActivity {
    public static int APP_REQUEST_CODE = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onLoginPhone();


//        WifiConfiguration wifiConfig = new WifiConfiguration();
//        wifiConfig.SSID = String.format("\"%s\"", "HasGeek Legacy");
//        wifiConfig.preSharedKey = String.format("\"%s\"", "geeksrus");
//
//        WifiManager wifiManager=(WifiManager)getSystemService(WIFI_SERVICE);
//        if(wifiManager.isWifiEnabled()){
//            wifiManager.setWifiEnabled(false);
//        }else{
//            wifiManager.setWifiEnabled(true);
//        }
//        int netId = wifiManager.addNetwork(wifiConfig);
//        wifiManager.disconnect();
//        wifiManager.enableNetwork(netId, true);
//        wifiManager.reconnect();

    }


    public void onLoginPhone() {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
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

        if (requestCode == APP_REQUEST_CODE)
        {
         AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                @Override
                public void onSuccess(final Account account) {

                    PhoneNumber phoneNumber = account.getPhoneNumber();
                    String phoneNumberString = phoneNumber.toString();

                    WifiConfiguration wifiConfig = new WifiConfiguration();
                    wifiConfig.SSID = String.format("\"%s\"", "HasGeek");
                    wifiConfig.preSharedKey = String.format("\"%s\"", "geeksrus");

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


                    //  showSimpleDialog(phoneNumberString);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                    builder.setCancelable(false);
                    builder.setTitle("AlertDialog Title");
                    builder.setMessage(phoneNumberString + "Simple Dialog Message");
                    builder.setPositiveButton("OK!!!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            Intent intent = new Intent(OpenWifi.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });

//                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });

                    // Create the AlertDialog object and return it
                    builder.create().show();

//                        Toast.makeText(OpenWifi.this,
//                                phoneNumberString +"is Connected to Hasgeek internet", Toast.LENGTH_LONG).show();


                }

                @Override
                public void onError(final AccountKitError error) {
                    // Handle Error

                }
            });
        }

    }
//    public void showSimpleDialog(String phoneNumber) {
//        // Use the Builder class for convenient dialog construction
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(false);
//        builder.setTitle("AlertDialog Title");
//        builder.setMessage(phoneNumber+"Simple Dialog Message");
//        builder.setPositiveButton("OK!!!", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//
//                Intent intent = new Intent(OpenWifi.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
////                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////
////                    }
////                });
//
//        // Create the AlertDialog object and return it
//        builder.create().show();
//    }
//


}