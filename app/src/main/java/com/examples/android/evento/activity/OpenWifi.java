package com.examples.android.evento.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.facebook.accountkit.AccessToken;
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



        AccessToken accessToken = AccountKit.getCurrentAccessToken();

        if (accessToken != null) {
            //Handle Returning User

            WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", "HasGeek Legacy");
        wifiConfig.preSharedKey = String.format("\"%s\"", "geeksrus");

        WifiManager wifiManager=(WifiManager)getSystemService(WIFI_SERVICE);
        if(wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(false);
        }else{
            wifiManager.setWifiEnabled(true);
        }
        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();

        } else {
            onLoginPhone();
            //Handle new or logged out user
        }




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

        if (requestCode == APP_REQUEST_CODE)
        {
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                @Override
                public void onSuccess(final Account account) {

                    PhoneNumber phoneNumber = account.getPhoneNumber();
                    String phoneNumberString = phoneNumber.toString();

                    WifiConfiguration wifiConfig = new WifiConfiguration();
                    wifiConfig.SSID = String.format("\"%s\"", "HasGeek Legacy");
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


}















        //package com.examples.android.evento.activity;
//
//
//import android.content.DialogInterface;
//import android.content.Intent;
//
//import android.content.SharedPreferences;
//import android.net.wifi.WifiConfiguration;
//import android.net.wifi.WifiManager;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//
//import com.examples.android.evento.R;
//import com.examples.android.evento.controller.SharedPreferenceController;
//import com.facebook.accountkit.AccessToken;
//import com.facebook.accountkit.Account;
//import com.facebook.accountkit.AccountKit;
//import com.facebook.accountkit.AccountKitCallback;
//import com.facebook.accountkit.AccountKitError;
//
//import com.facebook.accountkit.AccountKitLoginResult;
//import com.facebook.accountkit.PhoneNumber;
//import com.facebook.accountkit.internal.Utility;
//import com.facebook.accountkit.ui.AccountKitActivity;
//import com.facebook.accountkit.ui.AccountKitConfiguration;
//import com.facebook.accountkit.ui.LoginType;
//
//
///**
// * Created by ankit on 27/12/16.
// */
//
//
//public class OpenWifi extends AppCompatActivity {
//    public static int APP_REQUEST_CODE = 99;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AccountKit.initialize(getApplicationContext());
//////setContentView(R.layout.openwifi);
////       AccessToken accessToken = AccountKit.getCurrentAccessToken();
////
//////if(accessToken==null)
//    onLoginPhone();
////else
////    connectwifi();
////            Handle Returning User
////if(SharedPreferenceController.getSharedPref("PhoneNumber")==null)
////        onLoginPhone();
////
////String phoneNumber = com.examples.android.evento.controller.PhoneNumber.getPhone_Number();
////
////        if(com.examples.android.evento.controller.PhoneNumber.getPhone_Number()==null)
////
////        if(phoneNumber==null)
////
////
////else
////            connectwifi(phoneNumber);
//
//        }
//
//
//
//
//
//    public void onLoginPhone() {
//        final Intent intent = new Intent(this, AccountKitActivity.class);
//        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
//                new AccountKitConfiguration.AccountKitConfigurationBuilder(
//                        LoginType.PHONE,
//                        AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
//        // ... perform additional configuration ...
//        intent.putExtra(
//                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
//                configurationBuilder.build());
//      //  startActivityForResult(intent, APP_REQUEST_CODE);
//        connectwifi();
//    }
//
//
//
//
//
//
//    @Override
//    protected void onActivityResult(
//            final int requestCode,
//            final int resultCode,
//            final Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode != APP_REQUEST_CODE) {
//            return;
//        }
//
//        AccountKitLoginResult loginResult = AccountKit.loginResultWithIntent(data);
//        final String accessToken = loginResult.getAccessToken().getToken();
//
//
//        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
//            @Override
//            public void onSuccess(final Account account) {
//
//                PhoneNumber phoneNumber = account.getPhoneNumber();
//                String phoneNumberString = phoneNumber.toString();
//
////                com.examples.android.evento.controller.PhoneNumber.setPhone_Number(accessToken);
//             //   com.examples.android.evento.controller.PhoneNumber.setPhone_Number(phoneNumberString);
//
//                connectwifi();
////                mUtility.saveToPreferences("AccessToken", accessToken);
////                mUtility.saveToPreferences("PhoneNumber", phoneNumberString);
////
////
////
////                fetchUserTask.execute(accessToken, phoneNumberString);
//
//
//
//
//            }
//
//            @Override
//            public void onError(final AccountKitError error) {
//                // Handle Error
//
//            }
//        });
//
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////
////    @Override
////    protected void onActivityResult(
////            final int requestCode,
////            final int resultCode,
////            final Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////
////        if (requestCode != APP_REQUEST_CODE)
////        {
////            return;
////        }
////
////
////           // AccountKitLoginResult loginResult = AccountKit.loginResultWithIntent(data);
////         //   final String accessToken = loginResult.getAccessToken().getToken();
////
////         AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
////                @Override
////                public void onSuccess(final Account account) {
////
////                    PhoneNumber phoneNumber = account.getPhoneNumber();
////                    String phoneNumberString = phoneNumber.toString();
////
////
////
////
////                 com.examples.android.evento.controller.PhoneNumber.setPhone_Number(phoneNumberString);
////
////                    WifiConfiguration wifiConfig = new WifiConfiguration();
////                    wifiConfig.SSID = String.format("\"%s\"", "Geekskool");
////                    wifiConfig.preSharedKey = String.format("\"%s\"", "takeiteasy");
////
////                    WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
////                    if (wifiManager.isWifiEnabled()) {
////                        wifiManager.setWifiEnabled(false);
////                    } else {
////                        wifiManager.setWifiEnabled(true);
////                    }
////                    int netId = wifiManager.addNetwork(wifiConfig);
////                    wifiManager.disconnect();
////                    wifiManager.enableNetwork(netId, true);
////                    wifiManager.reconnect();
////
////
////                    //  showSimpleDialog(phoneNumberString);
////                    AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
////                    builder.setCancelable(false);
////                    builder.setTitle("Connected to Hasgeek successfully");
////                    builder.setMessage(phoneNumberString + "Simple Dialog Message");
////                    builder.setPositiveButton("OK!!!", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialog, int id) {
////
////                            Intent intent = new Intent(OpenWifi.this, MainActivity.class);
////                            startActivity(intent);
////                        }
////                    });
////
//////                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
//////                    @Override
//////                    public void onClick(DialogInterface dialog, int which) {
//////
//////                    }
//////                });
////
////                    // Create the AlertDialog object and return it
////                    builder.create().show();
////
//////                        Toast.makeText(OpenWifi.this,
//////                                phoneNumberString +"is Connected to Hasgeek internet", Toast.LENGTH_LONG).show();
////
////
////                }
////
////                @Override
////                public void onError(final AccountKitError error) {
////                    // Handle Error
////
////                }
////            });
////        }
//
//
//public void connectwifi() {
//    WifiConfiguration wifiConfig = new WifiConfiguration();
//    wifiConfig.SSID = String.format("\"%s\"", "Geekskool");
//    wifiConfig.preSharedKey = String.format("\"%s\"", "takeiteasy");
//
//    WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
//    if (wifiManager.isWifiEnabled()) {
//        wifiManager.setWifiEnabled(false);
//    } else {
//        wifiManager.setWifiEnabled(true);
//    }
//    int netId = wifiManager.addNetwork(wifiConfig);
//    wifiManager.disconnect();
//    wifiManager.enableNetwork(netId, true);
//    wifiManager.reconnect();
//
//
//    //  showSimpleDialog(phoneNumberString);
//    AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
//    builder.setCancelable(false);
//    builder.setTitle("Connected to Hasgeek successfully");
//    builder.setMessage("Simple Dialog Message");
//    builder.setPositiveButton("OK!!!", new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int id) {
//
//            Intent intent = new Intent(OpenWifi.this, MainActivity.class);
//            startActivity(intent);
//        }
//    });
//
////                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////
////                    }
////                });
//
//    // Create the AlertDialog object and return it
//    builder.create().show();
//}
//    }
//



