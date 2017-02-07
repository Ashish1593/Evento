package com.examples.android.evento.controller;

/**
 * Created by ankit on 23/1/17.
 */


//import com.examples.android.evento.schedule.ScheduleHelper;
////import com.examples.android.evento.schedule.Space;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.examples.android.evento.activity.ScheduleActivity;
import com.examples.android.evento.adapters.SessionsAdapter;
import com.examples.android.evento.model.Session;
import com.examples.android.evento.utils.AuthWrapper;
import com.examples.android.evento.interfacelistener.TalkfunnelAPI;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;

import static android.content.ContentValues.TAG;
import static com.facebook.accountkit.internal.AccountKitController.getApplicationContext;


public class APIController {

    public static APIController apiController;
    public static TalkfunnelAPI api;

    public static APIController getService() {
        if (apiController == null) {
            apiController = new APIController();
            apiController.api = apiController.createController();
        }
        return apiController;
    }

   public RecyclerView mRecyclerView;


    public  String getAuthHeaderFromToken(String token) {
        return "Bearer "+token;
    }



    public TalkfunnelAPI createController() {
        return createController(null);
    }

    public TalkfunnelAPI createController(String spaceUrl) {

        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                // return f.getDeclaringClass() == RealmObject.class;
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();


        String baseUrl;
        if (spaceUrl != null)
            baseUrl = spaceUrl;
        else
            baseUrl = "https://talkfunnel.com/";
        Retrofit.Builder builder = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl);

        return builder.build().create(TalkfunnelAPI.class);
    }


    public Observable<AuthWrapper> getAuthVerification(final String authToken) {
        return Observable.create(new Observable.OnSubscribe<AuthWrapper>() {
            @Override
            public void call(Subscriber<? super AuthWrapper> subscriber) {
                try {

                    final Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            // return f.getDeclaringClass() == RealmObject.class;
                            return false;
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    }).create();

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://talkfunnel.com/api/whoami")
                            .addHeader("Authorization", getAuthHeaderFromToken(authToken))
                            .build();

                    Response response = client.newCall(request).execute();
                    AuthWrapper authWrapper = gson.fromJson(response.body().string(), AuthWrapper.class);

                    subscriber.onNext(authWrapper);
                    subscriber.onCompleted();

                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }



    public void makeJsonObjectRequest(String urlJsonObj) {

        //  showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,
                urlJsonObj , null, new com.android.volley.Response.Listener<JSONObject>() {

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

                    for(int i=0; i<schedule.length(); i++) {
                        JSONArray slots = schedule.getJSONObject(i).getJSONArray("slots");
                        for(int k=0; k<slots.length();k++) {
                            sessions.addAll(Arrays.asList(gson.fromJson(slots.getJSONObject(k).optString("sessions", "[]"), Session[].class)));
                        }
                    }

                    mRecyclerView.setAdapter(new SessionsAdapter(getApplicationContext(), sessions));



//                    for(Session s: sessions) {
//                       mRecyclerView.setAdapter(new SessionsAdapter(ScheduleActivity.this, sessions));
//                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }


                //   announcementRecyclerView.setAdapter(new RecyclerViewAdapterAnnouncements(com.examples.android.evento.activity.AnnouncementsActivity.this,announcementsArraylist));

            }

        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //  VolleyLog.d(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),
                        "no network", Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                //   hidepDialog();

            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }





}
