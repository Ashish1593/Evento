package com.examples.android.evento.activity;

/**
 * Created by ankit on 14/12/16.
 */

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.examples.android.evento.R;

import com.examples.android.evento.adapters.FoodCourtVendorPagerAdapter;

import com.examples.android.evento.controller.AppController;
import com.examples.android.evento.controller.DataBaseController;
import com.examples.android.evento.model.EventDetails;
import com.examples.android.evento.model.Metadata;
import com.examples.android.evento.model.Session;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by navneet on 12/11/16.
 */

public class SplashScreen extends AppCompatActivity {
    private String urlJsonObjPycon = "https://pyconpune.talkfunnel.com/2017/json";
    private String urlJsonUbjFossMeet = "https://fossmeet-nitc.talkfunnel.com/2017/json";
    private String urlJsonObjRootConf = "https://rootconf.talkfunnel.com/2017/json";
    private String urlJsonObjJsfoo = "https://jsfoo.talkfunnel.com/2017/json";
    private String urlJsonObjKilter = "https://kilter.talkfunnel.com/2017/json";
    private String urlJsonObjEventList = "https://talkfunnel.com/json";
    private String urlMetadataPycon = "http://hasgeek.github.io/api/space/104/metadata";
    private String urlMetadataFossMeet = "http://hasgeek.github.io/api/space/103/metadata";
    private String urlMetadataRootConf = "http://hasgeek.github.io/api/space/102/metadata";
    private String urlMetadataJsfoo = "http://hasgeek.github.io/api/space/106/metadata";
    private String urlMetadataKilter = "http://hasgeek.github.io/api/space/110/metadata";


    private DataBaseController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = DataBaseController.getInstance(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen);


        makeJsonObjectRequestForSession(urlJsonObjPycon, "EventPycon");
        makeJsonObjectRequestForSession(urlJsonUbjFossMeet, "EventFossMeet");
        makeJsonObjectRequestForSession(urlJsonObjRootConf, "EventRootConf");
        makeJsonObjectRequestForSession(urlJsonObjJsfoo, "EventJsfoo");
        makeJsonObjectRequestForSession(urlJsonObjKilter, "EventKilter");

        makeJsonObjectRequestForEventDetails(urlJsonObjEventList, "EventDetails");

        requestJsonObject(urlMetadataPycon, "MetadataEventPycon");
        requestJsonObject(urlMetadataFossMeet, "MetadataEventFossMeet");
        requestJsonObject(urlMetadataRootConf, "MetadataEventRootConf");
        requestJsonObject(urlMetadataJsfoo, "MetadataEventJsfoo");
        requestJsonObject(urlMetadataKilter, "MetadataEventKilter");

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final Animation animation_1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation animation_2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.antirotate);
        final Animation animation_3 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);

        imageView.startAnimation(animation_2);
        animation_2.setAnimationListener(new Animation.AnimationListener() {


            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(animation_1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation_1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(animation_2);
                finish();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    private void makeJsonObjectRequestForSession(String urlJsonObj, final String EventName) {

        //  showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {          //  Parsing json object response
                    //response will be a json object
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    // JSONObject obj = null;

                    List<Session> sessions = new ArrayList<>();
                    JSONArray schedule = new JSONArray(response.optString("schedule", "[]"));

                    for (int i = 0; i < schedule.length(); i++) {
                        JSONArray slots = schedule.getJSONObject(i).getJSONArray("slots");
                        for (int k = 0; k < slots.length(); k++) {
                            sessions.addAll(Arrays.asList(gson.fromJson(slots.getJSONObject(k).optString("sessions", "[]"), Session[].class)));
                        }
                    }
                    String eventName = gson.toJson(sessions, new TypeToken<List<Session>>() {
                    }.getType());
                    if (db.getCount(EventName) == 0)
                        db.addScheduleAndEventData(eventName, EventName);
                    else
                        db.updateScheduleAndEventData(eventName, EventName);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SplashScreen.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });


        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    private void makeJsonObjectRequestForEventDetails(String urlJsonObj, final String EventDetails) {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(ContentValues.TAG, response.toString());
                try {

                    JSONArray eventsArray = response.getJSONArray("spaces");
                    List<EventDetails> details = new ArrayList<>();

                    for (int i = 0; i < eventsArray.length(); i++) {
                        JSONObject events = eventsArray.getJSONObject(i);
                        String name = events.getString("title");
                        String location = events.getString("datelocation");
                        String date = events.getString("start");
                        String URL = events.getString("url");
                        String endDate = events.getString("end");

                        EventDetails edetails = new EventDetails(name, location, date, URL, endDate);

                        details.add(edetails);


                    }
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();


                    String eventdetails = gson.toJson(details, new TypeToken<List<EventDetails>>() {
                    }.getType());

                    if (db.getCount(EventDetails) == 0)
                        db.addScheduleAndEventData(eventdetails, EventDetails);
                    else
                        db.updateScheduleAndEventData(eventdetails, EventDetails);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SplashScreen.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                //   hidepDialog();
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(ContentValues.TAG, "Error: " + error.getMessage());

            }
        }

        );

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    private void requestJsonObject(String urlJsonObj, final String MetadataEventName) {
        RequestQueue queue = Volley.newRequestQueue(this);
        // String url = "http://hasgeek.github.io/api/space/97/metadata";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlJsonObj, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                Metadata metadata = gson.fromJson(response, Metadata.class);
                //foodCourtVendorPagerAdapter = new FoodCourtVendorPagerAdapter(SplashScreen.this, metadata.getFoodCourtVendors());
                int count = db.getAllContacts();
                String eventName = gson.toJson(metadata, new TypeToken<Metadata>() {
                }.getType());
                if (db.getCount(MetadataEventName) == 0)
                    db.addScheduleAndEventData(eventName, MetadataEventName);
                else
                    db.updateScheduleAndEventData(eventName, MetadataEventName);

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);


    }


}