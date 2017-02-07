package com.examples.android.evento.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.examples.android.evento.R;
import com.examples.android.evento.adapters.RecylerViewadapter;
import com.examples.android.evento.controller.AppController;
import com.examples.android.evento.model.TalkDetails;
import com.examples.android.evento.activity.ScheduleActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by ankit on 1/2/17.
 */

public class Jsfoo2017 extends Fragment{

    MapView mMapView;
    private GoogleMap googleMap;
    private String urlJsonObj = "https://jsfoo.talkfunnel.com/2017/json";
    private ArrayList<TalkDetails> detailsJSFOO;
    private ProgressDialog pDialog;
    private RecyclerView myRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.jsfoo2017,container,false);

        mMapView = (MapView) view.findViewById(R.id.mapViewJSFOO);

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
                //googleMap.setMyLocationEnabled(true);
                //11.3217° N, 75.9342° E
                // For dropping a marker at a point on the Map
                LatLng MLRConventionCenter = new LatLng(12.8917,77.5852);
                googleMap.addMarker(new MarkerOptions().position(MLRConventionCenter).title("MLR CONVENTON Center").snippet("M L R CONVENTION CENtER J P NAGAR"));

                MarkerOptions mo = new MarkerOptions().position(MLRConventionCenter).title("MLR CONVENTON Center").snippet("M L R CONVENTION CENtER J P NAGAR").visible(true);
                Marker marker = googleMap.addMarker(mo);
                mo.anchor(0f, 0.5f);
                marker.showInfoWindow();
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(MLRConventionCenter).zoom(16).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });


        Button proposeJSFOOSession = (Button) view.findViewById(R.id.proposeJSFOOSession);
        proposeJSFOOSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                final String URI = "https://jsfoo.talkfunnel.com/2017/new";
                intent.launchUrl(getActivity(), Uri.parse(URI));

            }
        });


        Button  ViewSchedule = (Button) view.findViewById(R.id.viewschedulejsfoo);
        ViewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
                intent.putExtra("jsonurl","https://jsfoo.talkfunnel.com/2017/json");
                startActivity(intent);
            }
        });



        myRecyclerView =(RecyclerView) view.findViewById(R.id.CardViewJSFOO);
        LinearLayoutManager myLayoutManager = new LinearLayoutManager(getActivity());
        // myRecyclerView =(RecyclerView) view.findViewById(R.id.card_recycler_view);
        // RecyclerView.LayoutManager myLayoutManager = new GridLayoutManager(getActivity(),2);
        myLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        myRecyclerView.setLayoutManager(myLayoutManager);


        Button buyJsfooTicket = (Button) view.findViewById(R.id.BuyjsfooTickets);
        buyJsfooTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                final String URI = "https://jsfoo.in/2017/";
                intent.launchUrl(getActivity(), Uri.parse(URI));

            }
        });


//        final TextView clickToSeeAllEvents = (TextView) view.findViewById(R.id.clicktoseefossmeetproposedtalks);
//
//
//        clickToSeeAllEvents.setOnClickListener(new View.OnClickListener(){
//
//            public  void onClick (View view){
//                Fragment fragment = new EventslistFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.proposedTalksFossMeet,fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                clickToSeeAllEvents.setVisibility(View.GONE);
//
//            }
//        });

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



        // showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //   Log.d(TAG,response.toString());
                try {
                    //  Parsing json object response
                    //response will be a json object
                    JSONArray proposalsJSFOOArray = response.getJSONArray("proposals");
                    detailsJSFOO = new ArrayList<TalkDetails>();
                    for(int i=0;i<proposalsJSFOOArray.length();i++) {
                        JSONObject talksFossmeet = proposalsJSFOOArray.getJSONObject(i);

                        String speakerName = talksFossmeet.getString("fullname");
                        String talkTitle = talksFossmeet.getString("title");
                        String talkURL = talksFossmeet.getString("url");

                        TalkDetails proprsalJSFOODetails = new TalkDetails(speakerName,talkTitle,talkURL);

                        detailsJSFOO.add(proprsalJSFOODetails);




                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
//
                myRecyclerView.setAdapter(new RecylerViewadapter(getActivity(),detailsJSFOO));



                //   hidepDialog();
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        "no network", Toast.LENGTH_SHORT).show();
                // hide the progress dialog

                //  hidepDialog();
            }
        });



        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }



    /**
     * Method to make json array request where response starts with [
     */


    public void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }





}