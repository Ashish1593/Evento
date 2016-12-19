package com.examples.android.evento;

//import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;


import static com.examples.android.evento.R.id.viewpager;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;


public class MainActivity extends AppCompatActivity {
    GridView grid;
        private static String TAG = MainActivity.class.getSimpleName();
 // json object response url
        private String urlJsonObj = "https://talkfunnel.com/json";
    private final int SPLASH_DISPLAY_LENGTH = 3000;

Context context;
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



            Fragment workshoponDNSandDNSSEC = new WorkshoponDNSandDNSSEC();
            Fragment event50p = new Event50p();
            Fragment eventFossMeet = new EventFossMeet();
            Fragment eventPycon=new EventPycon();
           // Fragment seeallevents = new SeeAllEvents();
            Fragment eventslistFragment = new EventslistFragment();




          ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            pagerAdapter.addFragment(workshoponDNSandDNSSEC);
            pagerAdapter.addFragment(event50p);
            pagerAdapter.addFragment(eventPycon);
            pagerAdapter.addFragment(eventFossMeet);
            pagerAdapter.addFragment(eventslistFragment);
          //  pagerAdapter.addFragment(seeallevents);
            ViewPager viewPager = (ViewPager) findViewById(viewpager);

             viewPager.setAdapter(pagerAdapter);



//
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

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);




         makeJsonObjectRequest();

 }
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
////
////                ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),details);
////               ViewPager viewPager = (ViewPager) findViewById(viewpager);
////
////               viewPager.setAdapter(pagerAdapter);
//
////               // viewPager.setPageTransformer(true, new RotateUpTransformer());
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

