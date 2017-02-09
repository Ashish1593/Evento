package com.examples.android.evento.activity;

//import android.app.Fragment;
//import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.examples.android.evento.controller.AppController;
import com.examples.android.evento.fragments.Event50p;
import com.examples.android.evento.fragments.Jsfoo2017;
import com.examples.android.evento.fragments.RootConf2017;
import com.examples.android.evento.model.EventDetails;
import com.examples.android.evento.fragments.EventFossMeet;
import com.examples.android.evento.fragments.EventPycon;
import com.examples.android.evento.fragments.EventslistFragment;
import com.examples.android.evento.R;
import com.examples.android.evento.adapters.ViewPagerAdapter;
import com.examples.android.evento.fragments.WorkshoponDNSandDNSSEC;
import com.facebook.accountkit.Account;
import  com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import java.util.ArrayList;


import static com.examples.android.evento.R.id.viewpager;


public class MainActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    GridView grid;
     private static String TAG = MainActivity.class.getSimpleName();
 // json object response url
     private String urlJsonObj = "https://talkfunnel.com/json";
     private final int SPLASH_DISPLAY_LENGTH = 3000;
    public static int APP_REQUEST_CODE = 99;

    Context context;

    String networkSSID = "test";
    String networkPass = "pass";
    //public ArrayList<EventDetails> details ;


    // Progress dialog
       private ProgressDialog pDialog;

    private TextView txtResponse;

        // temporary string to show the parsed response
        private String jsonResponse;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            AccountKit.initialize(getApplicationContext());
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);

            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            AccountKit.initialize(getApplicationContext());
             setContentView(R.layout.activity_main);

            //ImageButton openDrawer = (ImageButton) findViewById(R.id.opendrawer);

           final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);



            ImageButton openDrawer = (ImageButton) findViewById(R.id.opendrawer);

            openDrawer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            });


/////////////////////////////////////////////////////////
//            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
//            ActionBar actionBar = getSupportActionBar();
//            actionBar.setDisplayHomeAsUpEnabled(true);
//
//            collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//            collapsingToolbarLayout.setTitle(getResources().getString(R.string.user_name));
//
//            dynamicToolbarColor();
//            toolbarTextAppernce();
//////////////////////////////////////////////////////////


         //   Fragment workshoponDNSandDNSSEC = new WorkshoponDNSandDNSSEC();
           // Fragment Events_two_fragments = new Events_two_fragments();
          //  Fragment event50p = new Event50p();
            Fragment eventFossMeet = new EventFossMeet();
            Fragment eventPycon=new EventPycon();
          //Fragment seeallevents = new SeeAllEvents();
           Fragment eventslistFragment = new EventslistFragment();
            Fragment eventJsfoo2017 = new Jsfoo2017();
            Fragment eventRootconf2017 = new RootConf2017();




            ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            // pagerAdapter.addFragment(workshoponDNSandDNSSEC);
            //pagerAdapter.addFragment(Events_two_fragments);
          // pagerAdapter.addFragment(event50p);
            pagerAdapter.addFragment(eventPycon);
            pagerAdapter.addFragment(eventFossMeet);
            pagerAdapter.addFragment(eventRootconf2017);
            pagerAdapter.addFragment(eventJsfoo2017);
            pagerAdapter.addFragment(eventslistFragment);
            //pagerAdapter.addFragment(seeallevents);
            ViewPager viewPager = (ViewPager) findViewById(viewpager);
            viewPager.setAdapter(pagerAdapter);








////////////////////////////////////////
//     ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),details);
//            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//
//            viewPager.setAdapter(pagerAdapter);
//
//            viewPager.setPageTransformer(true, new CubeOutTransformer());


//            LinearLayout linkArea = (LinearLayout) findViewById(R.id.openlink);
//            linkArea.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
//                    myWebLink.setData(Uri.parse("http://www.talkfunnel.com"));
//                    startActivity(myWebLink);
//                }
//            });

//////////////////////////////////////////////////

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);

            //makeJsonObjectRequest();


        }










    public void hasgeektv(View view ) {

        final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
        final String URI = "https://hasgeek.tv/";
        intent.launchUrl(MainActivity.this, Uri.parse(URI));

    }


    public void talkfunnel(View view ) {

        final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
        final String URI = "https://talkfunnel.com/";
        intent.launchUrl(MainActivity.this, Uri.parse(URI));

    }



    public void hasjob(View view ) {

        final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
        final String URI = "https://hasjob.co/";
        intent.launchUrl(MainActivity.this, Uri.parse(URI));

    }


    public void hasgeek(View view ) {

        final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
        final String URI = "https://hasgeek.com/";
        intent.launchUrl(MainActivity.this, Uri.parse(URI));

    }


    public void liveStream(View view ) {

        final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
       // final String URI = "http://www.ustream.tv/channel/hasgeek";
      final String URI=  "https://www.youtube.com/watch?v=8YZZZwcckE8";
        intent.launchUrl(MainActivity.this, Uri.parse(URI));

    }


    public void qrcodescanner(View view ) {

        Intent intent = new Intent(view.getContext(), QRcodeScanner.class);
        startActivity(intent);

    }


    public void foodcourt(View view ) {

        Intent intent = new Intent(view.getContext(), FoodCourtActivity.class);
        startActivity(intent);

    }
    public void connecttonetwork(View view ) {



        Intent intent = new Intent(view.getContext(),OpenWifi.class);
        startActivity(intent);

    }

    public void announcements(View view ) {

        Intent intent = new Intent(view.getContext(), AnnouncementsActivity.class);
        startActivity(intent);

    }




    public void aboutGeekskool(View view ) {

        final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
        final String URI = "http://www.geekskool.com/";
        intent.launchUrl(MainActivity.this, Uri.parse(URI));

    }




            public void onDiscussionClick(View view) {

                            new AlertDialog.Builder(this)
                                    .setTitle("Join the discussion!")
                                    .setMessage("Are you on the Friends of HasGeek Slack team? Follow the discussion on the our channel")
                                    .setCancelable(true)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                            String uri ;
                                            if (isPackageInstalled(SLACK_ANDROID_PACKAGE_NAME, getPackageManager()))
                                                   uri = "slack://channel?team=T04TX4KJG&id=C04TX6S4Y";
                                            else
                                               // uri = "https://friendsofhasgeek.slack.com/messages/droidcon/";
                                            uri= "https://friendsofhasgeek.slack.com/messages/50p/";
                                                   Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(uri));
                                            startActivity(i);
                                        }
                               })
                                    .setNegativeButton("No, send me an invite", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                           CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder()
                                                            .setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));

                                                    CustomTabsIntent customTabsIntent = builder.build();
                                            customTabsIntent.launchUrl(MainActivity.this, Uri.parse("https://friends.hasgeek.com/"));
                                        }
                               })
                                    .create().show();

                        }




    public static final String SLACK_ANDROID_PACKAGE_NAME = "com.Slack";

                public static boolean isPackageInstalled(String targetPackage, PackageManager packageManager){
                try {
                        PackageInfo info=packageManager.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
                    } catch (PackageManager.NameNotFoundException e) {
                        return false;
                    }
                return true;
            }



           }

