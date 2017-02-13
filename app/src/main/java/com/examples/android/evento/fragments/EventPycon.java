package com.examples.android.evento.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.examples.android.evento.adapters.SessionsAdapter;
import com.examples.android.evento.controller.AppController;
import com.examples.android.evento.R;
import com.examples.android.evento.controller.DataBaseController;
import com.examples.android.evento.model.Session;
import com.examples.android.evento.model.TalkDetails;
import com.examples.android.evento.adapters.RecylerViewadapter;
//import com.examples.android.evento.activity.ScheduleActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by ankit on 11/12/16.
 */

public class EventPycon extends Fragment {
    MapView mMapView;
    private RecyclerView mRecyclerView;
    private GoogleMap googleMap;
    private ProgressDialog pDialog;
    private String urlJsonObj = "https://pyconpune.talkfunnel.com/2017/json";
    private ArrayList<TalkDetails> detailsPycon;
    private TextView emptyView;
  private   DataBaseController db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pycon2017, container, false);

      db = new DataBaseController(getActivity());

        mMapView = (MapView) view.findViewById(R.id.mapViewPycon);
        mMapView.onCreate(null);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                //   googleMap.setMyLocationEnabled(true);
                // 18.5312° N, 73.8557° E
                // For dropping a marker at a point on the Map
                LatLng collegeofEngPune = new LatLng(18.5312, 73.8557);
                googleMap.addMarker(new MarkerOptions().position(collegeofEngPune).title("College Of Engineering Pune").snippet("College Of Engoneering Pune"));

                MarkerOptions mo = new MarkerOptions().position(collegeofEngPune).title("College Of Engineering Pune").snippet("College Of Engoneering Pune").visible(true);
                Marker marker = googleMap.addMarker(mo);
                mo.anchor(0f, 0.5f);
                marker.showInfoWindow();
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(collegeofEngPune).zoom(16).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });


        Button buyPyconTicket = (Button) view.findViewById(R.id.BuyPyconTickets);
        buyPyconTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                final String URI = "https://pune.pycon.org/registration/";
                intent.launchUrl(getActivity(), Uri.parse(URI));

            }
        });
        emptyView = (TextView) view.findViewById(R.id.empty_view);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.schedule_recyclerview);
        mRecyclerView.setNestedScrollingEnabled(false);
        //  mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        makeJsonObjectRequest();


        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    private void makeJsonObjectRequest() {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();


                    List<Session> sessions = new ArrayList<>();
                    JSONArray schedule = new JSONArray(response.optString("schedule", "[]"));

                    for (int i = 0; i < schedule.length(); i++) {
                        JSONArray slots = schedule.getJSONObject(i).getJSONArray("slots");
                        for (int k = 0; k < slots.length(); k++) {
                            sessions.addAll(Arrays.asList(gson.fromJson(slots.getJSONObject(k).optString("sessions", "[]"), Session[].class)));
                        }
                    }



                    String eventPycon= gson.toJson(sessions);



                    db.addScheduleAndEventData(eventPycon ,"EventPycon");


                    if (sessions.isEmpty()) {
                        mRecyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                    }



                    mRecyclerView.setAdapter(new SessionsAdapter(getActivity(), sessions));


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),
                        "no network", Toast.LENGTH_SHORT).show();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

}