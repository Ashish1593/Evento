package com.examples.android.evento.fragments;


//import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.examples.android.evento.R;
import com.examples.android.evento.activity.MainActivity;
import com.examples.android.evento.adapters.GridViewAdapter;
import com.examples.android.evento.controller.AppController;
import com.examples.android.evento.model.EventDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by ankit on 30/11/16.
 */

public class EventslistFragment extends Fragment {
    //private ArrayList<EventDetails> details;




    private String urlJsonObj = "https://talkfunnel.com/json";

    public ArrayList<EventDetails> details ;
//    private ProgressDialog pDialog;
//    private int Position;
//    private String name;
//    private String date;
//    private String place;
//    private String url;

   private RecyclerView evRecyclerView;


  //  }

//  @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//
//
//    }
//



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.eventlist_fragment, container, false);
        evRecyclerView=(RecyclerView) rootView.findViewById(R.id.gridView);


        LinearLayoutManager myLayoutManager =new LinearLayoutManager(getActivity());
        myLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        evRecyclerView.setLayoutManager(myLayoutManager);
//            GridViewAdapter adapter = new GridViewAdapter(getActivity(), details);
//
//            grid.setAdapter(adapter);
        makeJsonObjectRequest();

        return rootView;
    }






    private void makeJsonObjectRequest() {

      //  showpDialog();

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

                        for (int i = 0; i < eventsArray.length(); i++) {
                            JSONObject events = eventsArray.getJSONObject(i);
                            String name = events.getString("title");
                            String location = events.getString("datelocation");
                            String date = events.getString("start");
                            String URL = events.getString("url");

                            EventDetails edetails = new EventDetails(name, location, date, URL);

                            details.add(edetails);


                        }



                  //  GridViewAdapter adapter = new GridViewAdapter(getActivity(), details);

                    evRecyclerView.setAdapter(new GridViewAdapter(getActivity(), details));



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

             //   hidepDialog();
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        "No Network", Toast.LENGTH_SHORT).show();
                // hide the progress dialog
             //   hidepDialog();
            }
        }

        );

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }



    /**
     * Method to make json array request where response starts with [
     */


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
