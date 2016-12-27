package com.examples.android.evento;

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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import java.lang.reflect.Method;
import java.util.ArrayList;


import static com.examples.android.evento.R.id.viewpager;


public class MainActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    GridView grid;
     private static String TAG = MainActivity.class.getSimpleName();
 // json object response url
        private String urlJsonObj = "https://talkfunnel.com/json";
    private final int SPLASH_DISPLAY_LENGTH = 3000;

Context context;

    String networkSSID = "test";
    String networkPass = "pass";
    public ArrayList<EventDetails> details ;


    // Progress dialog
       private ProgressDialog pDialog;

    private TextView txtResponse;

        // temporary string to show the parsed response
        private String jsonResponse;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);

            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

             setContentView(R.layout.activity_main);

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


            Fragment workshoponDNSandDNSSEC = new WorkshoponDNSandDNSSEC();
           // Fragment Events_two_fragments = new Events_two_fragments();
            Fragment event50p = new Event50p();
            Fragment eventFossMeet = new EventFossMeet();
            Fragment eventPycon=new EventPycon();
          //Fragment seeallevents = new SeeAllEvents();
           Fragment eventslistFragment = new EventslistFragment();




          ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
         pagerAdapter.addFragment(workshoponDNSandDNSSEC);
            //pagerAdapter.addFragment(Events_two_fragments);
            pagerAdapter.addFragment(event50p);
            pagerAdapter.addFragment(eventPycon);
            pagerAdapter.addFragment(eventFossMeet);
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




         makeJsonObjectRequest();


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
        final String URI = "http://www.ustream.tv/channel/hasgeek";
        intent.launchUrl(MainActivity.this, Uri.parse(URI));

    }


    public void qrcodescanner(View view ) {

        Intent intent = new Intent(view.getContext(), BarcodeScanner.class);
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
                                                uri = "https://friendsofhasgeek.slack.com/messages/droidcon/";

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






//////////////////////////////////////
//
//            @Override
//            public void onGenerated(Palette palette) {
//               collapsingToolbarLayout .setContentScrimColor(palette.getMutedColor(R.attrs.colorPrimary));
//                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attrs.colorPrimaryDark));
//            }
//        });
//    }
//
//    private void toolbarTextAppernce() {
//        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
//        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
//    }
/////////////////////////////////////
//        /**
//         * Method to make json object request where json response starts wtih {
//         * */
//    /**
//     * Method to make json object request where json response starts wtih {
//     * */
//    private void makeJsonObjectRequest() {
//
//        showpDialog();
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//                urlJsonObj, null, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d(TAG, response.toString());
//                try {
//                    //  Parsing json object response
//                    //response will be a json object
//                    JSONArray eventsArray = response.getJSONArray("spaces");
//                    details = new ArrayList<EventDetails>();
//                    for(int i=0;i<eventsArray.length();i++) {
//                        JSONObject events = eventsArray.getJSONObject(i);
//                        String name = events.getString("title");
//                        String location = events.getString("datelocation");
//                        String date = events.getString("start");
//                        String URL = events.getString("url");
//
//                      EventDetails edetails = new EventDetails(name,location,date,URL);
//
//                        details.add(edetails);
//
//
//
//                    }
//
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),
//                            "Error: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//
//                ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),details);
//               ViewPager viewPager = (ViewPager) findViewById(viewpager);
//
//               viewPager.setAdapter(pagerAdapter);
//
//              // viewPager.setPageTransformer(true, new RotateUpTransformer());
//                //viewPager.setPageTransformer(true, new AccordionTransformer());
//                //viewPager.setPageTransformer(true, new ScaleInOutTransformer());
//                //viewPager.setPageTransformer(true, new ZoomInTransformer());
//               // viewPager.setPageTransformer(true, new FlipHorizontalTransformer());
//               // viewPager.setPageTransformer(true, new FlipVerticalTransformer());
//               // viewPager.setPageTransformer(true, new TabletTransformer());
//                //viewPager.setPageTransformer(true, new DepthPageTransformer());
//               // viewPager.setPageTransformer(true, new FlipHorizontalTransformer());
//               // viewPager.setPageTransformer(true, new CubeInTransformer());
//            //    viewPager.setPageTransformer(true, new RotateDownTransformer());
//               // viewPager.setPageTransformer(true, new StackTransformer());
//               // viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
//          // viewPager.setPageTransformer(true, new CubeOutTransformer());
//                hidepDialog();
//       }
//
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
//                // hide the progress dialog
//                hidepDialog();
//            }
//        });
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(jsonObjReq);
//    }
//
//
//
//    /**
//     * Method to make json array request where response starts with [
//     */
//
//
//    private void showpDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//
//    private void hidepDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }



    private void makeJsonObjectRequest() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    //  Parsing json object response
                    //response will be a json object
                    JSONArray eventsArray = response.getJSONArray("spaces");
                    details = new ArrayList<EventDetails>();
                    for(int i=0;i<eventsArray.length();i++) {
                        JSONObject events = eventsArray.getJSONObject(i);
                        String name = events.getString("title");
                        String location = events.getString("datelocation");
                        String date = events.getString("start");
                        String URL = events.getString("url");

                        EventDetails edetails = new EventDetails(name,location,date,URL);

                        details.add(edetails);



                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
//


//                ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),details);
//               ViewPager viewPager = (ViewPager) findViewById(viewpager);
//
//               viewPager.setAdapter(pagerAdapter);

//               // viewPager.setPageTransformer(true, new RotateUpTransformer());
                //viewPager.setPageTransformer(true, new AccordionTransformer());
                //viewPager.setPageTransformer(true, new ScaleInOutTransformer());
                //viewPager.setPageTransformer(true, new ZoomInTransformer());
                // viewPager.setPageTransformer(true, new FlipHorizontalTransformer());
                // viewPager.setPageTransformer(true, new FlipVerticalTransformer());
                // viewPager.setPageTransformer(true, new TabletTransformer());
                //viewPager.setPageTransformer(true, new DepthPageTransformer());
                // viewPager.setPageTransformer(true, new FlipHorizontalTransformer());
                // viewPager.setPageTransformer(true, new CubeInTransformer());
                //    viewPager.setPageTransformer(true, new RotateDownTransformer());
                // viewPager.setPageTransformer(true, new StackTransformer());
                // viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
                // viewPager.setPageTransformer(true, new CubeOutTransformer());

                hidepDialog();
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }



    /**
     * Method to make json array request where response starts with [
     */


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }




           }

