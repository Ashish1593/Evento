package com.examples.android.evento;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by ankit on 27/12/16.
 */


public class OpenWifi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        WifiConfiguration wifiConfig = new WifiConfiguration();



        wifiConfig.SSID = String.format("\"%s\"", "HasGeek");
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

    }


}