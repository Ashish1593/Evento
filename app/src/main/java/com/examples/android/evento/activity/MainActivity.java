package com.examples.android.evento.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.app.ProgressDialog;

import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.examples.android.evento.fragments.Jsfoo2017;
import com.examples.android.evento.fragments.RootConf2017;

import com.examples.android.evento.fragments.EventFossMeet;
import com.examples.android.evento.fragments.EventPycon;
import com.examples.android.evento.fragments.EventslistFragment;
import com.examples.android.evento.R;
import com.examples.android.evento.adapters.ViewPagerAdapter;

import com.facebook.accountkit.AccountKit;


import static com.examples.android.evento.R.id.viewpager;


public class MainActivity extends AppCompatActivity {
    public static final String SLACK_ANDROID_PACKAGE_NAME = "com.Slack";

    private static String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;

    public static boolean isPackageInstalled(String targetPackage, PackageManager packageManager) {
        try {
            PackageInfo info = packageManager.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // AccountKit.initialize(getApplicationContext());
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AccountKit.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);




        Fragment eventFossMeet = new EventFossMeet();
        Fragment eventPycon = new EventPycon();

        Fragment eventslistFragment = new EventslistFragment();
        Fragment eventJsfoo2017 = new Jsfoo2017();
        Fragment eventRootconf2017 = new RootConf2017();


        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(eventPycon);
        pagerAdapter.addFragment(eventFossMeet);
        pagerAdapter.addFragment(eventRootconf2017);
        pagerAdapter.addFragment(eventJsfoo2017);
        pagerAdapter.addFragment(eventslistFragment);

        ViewPager viewPager = (ViewPager) findViewById(viewpager);
        viewPager.setAdapter(pagerAdapter);


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        //makeJsonObjectRequest();


    }





}

