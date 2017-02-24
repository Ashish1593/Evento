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
import com.examples.android.evento.adapters.SessionsAdapter;
import com.examples.android.evento.controller.AppController;
import com.examples.android.evento.controller.DataBaseController;
import com.examples.android.evento.model.EventDetails;
import com.examples.android.evento.model.Session;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ankit on 30/11/16.
 */

public class EventslistFragment extends Fragment {
    //private ArrayList<EventDetails> details;




    private String urlJsonObj = "https://talkfunnel.com/json";

   private RecyclerView evRecyclerView;
private DataBaseController db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.eventlist_fragment, container, false);
        evRecyclerView=(RecyclerView) rootView.findViewById(R.id.gridView);

        db = DataBaseController.getInstance(getActivity());
        LinearLayoutManager myLayoutManager =new LinearLayoutManager(getActivity());
        myLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        evRecyclerView.setLayoutManager(myLayoutManager);
//            GridViewAdapter adapter = new GridViewAdapter(getActivity(), details);
//
//            grid.setAdapter(adapter);

        if( db.getCount("EventDetails") !=0) {
            ArrayList<EventDetails> eventDetailsModel1 = new ArrayList<>();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            eventDetailsModel1 = gson.fromJson(db.getScheduleAndEventData("EventDetails"), new TypeToken<List<EventDetails>>() {
            }.getType());

            evRecyclerView.setAdapter(new GridViewAdapter(getActivity(), eventDetailsModel1));
        }

            //makeJsonObjectRequest();



        //  makeJsonObjectRequest();

        return rootView;
    }






}
