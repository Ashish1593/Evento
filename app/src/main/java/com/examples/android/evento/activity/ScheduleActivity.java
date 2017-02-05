package com.examples.android.evento.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.examples.android.evento.R;
import com.examples.android.evento.controller.AppController;
import com.examples.android.evento.model.Session;
import com.examples.android.evento.adapters.SessionsAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ankit on 31/1/17.
 */

public class ScheduleActivity extends AppCompatActivity {

    private static String TAG = com.examples.android.evento.activity.AnnouncementsActivity.class.getSimpleName();
   //private String urlJsonObj = "https://50p.talkfunnel.com/2017/json";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeLayout;
    private SessionsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.fragment_space_schedule);


//
//        swipeLayout = (SwipeRefreshLayout)findViewById(R.id.fragment_proposal_swipe_container);
//        swipeLayout.setOnRefreshListener(mOnSwipeListener);
//        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

        mRecyclerView = (RecyclerView) findViewById(R.id.fragment_space_schedule_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        //  mRecyclerView.addItemDecoration(new DividerItemDecoration(this));

        //  mRecyclerView.setOnScrollListener(scrollListener);
//
        Intent intent =getIntent();
        String url = intent.getStringExtra("jsonurl");
        makeJsonObjectRequest(url);
    }
//    private SwipeRefreshLayout.OnRefreshListener mOnSwipeListener = new SwipeRefreshLayout.OnRefreshListener() {
//        @Override
//        public void onRefresh() {
//            Log.i(TAG, "onRefresh() POST LoadScheduleEvent");
//            makeJsonObjectRequest();
//        }
//
//    };

//    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//            int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
//            swipeLayout.setEnabled(topRowVerticalPosition >= 0);
//
//        }

//        @Override
//        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
//        }
//    };





    private void makeJsonObjectRequest(String urlJsonObj) {

        //  showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
              urlJsonObj , null, new Response.Listener<JSONObject>() {

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

                    mRecyclerView.setAdapter(new SessionsAdapter(ScheduleActivity.this, sessions));



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

        }, new Response.ErrorListener() {

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



    /**
     * Method to make json array request where response starts with [
     */

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


}
