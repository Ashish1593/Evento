package com.examples.android.evento.activity;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.examples.android.evento.fragments.EventKilter;
import com.examples.android.evento.fragments.Jsfoo2017;
import com.examples.android.evento.fragments.RootConf2017;
import com.examples.android.evento.fragments.EventFossMeet;
import com.examples.android.evento.fragments.EventPycon;
import com.examples.android.evento.fragments.EventslistFragment;
import com.examples.android.evento.R;
import com.examples.android.evento.adapters.ViewPagerAdapter;

import com.facebook.accountkit.AccountKit;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.examples.android.evento.R.id.viewpager;


public class MainActivity extends AppCompatActivity {
    public static final String SLACK_ANDROID_PACKAGE_NAME = "com.Slack";


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
        AccountKit.initialize(getApplicationContext());
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AccountKit.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);


        Fragment eventFossMeet = new EventFossMeet();
        Fragment eventPycon = new EventPycon();
        Fragment eventKilter = new EventKilter();
        Fragment eventslistFragment = new EventslistFragment();
        Fragment eventJsfoo2017 = new Jsfoo2017();
        Fragment eventRootconf2017 = new RootConf2017();


        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String td = formatter.format(Calendar.getInstance().getTime());


        try {


            Date fossmeetdate = formatter.parse("2017-03-12");
            Date kilterdate = formatter.parse("2017-04-02");
            Date jsFoodate = formatter.parse("2017-09-09");
            Date rootconfdate = formatter.parse("2017-05-12");
            Date pycondate = formatter.parse("2017-02-17");
            Date todaysdate = formatter.parse(td);


             pagerAdapter.addFragment(eventPycon);

            if (fossmeetdate.after(todaysdate)) {
                pagerAdapter.addFragment(eventFossMeet);
            }
            if (kilterdate.after(todaysdate)) {
                pagerAdapter.addFragment(eventKilter);
            }
            if (rootconfdate.after(todaysdate)) {
                pagerAdapter.addFragment(eventRootconf2017);
            }
            if (jsFoodate.after(todaysdate)) {
                pagerAdapter.addFragment(eventJsfoo2017);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

//        pagerAdapter.addFragment(eventPycon);
//        //pagerAdapter.addFragment(eventFossMeet);
//        pagerAdapter.addFragment(eventKilter);
//        pagerAdapter.addFragment(eventRootconf2017);
//        pagerAdapter.addFragment(eventJsfoo2017);
        pagerAdapter.addFragment(eventslistFragment);

        ViewPager viewPager = (ViewPager) findViewById(viewpager);
        viewPager.setAdapter(pagerAdapter);


    }


}

