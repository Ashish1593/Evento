package com.examples.android.evento.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
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
//import com.examples.android.evento.activity.ProposedTalksActivity;
import com.examples.android.evento.controller.AppController;
import com.examples.android.evento.R;
import com.examples.android.evento.model.TalkDetails;
import com.examples.android.evento.adapters.RecylerViewadapter;
import com.examples.android.evento.activity.ScheduleActivity;
import com.examples.android.evento.adapters.SessionsAdapter;
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

//import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * Created by ankit on 9/12/16.
 */

public class Event50p extends Fragment    {
    MapView mMapView;
    private GoogleMap googleMap;
    private String urlJsonObj = "https://50p.talkfunnel.com/2017/json";
    private ArrayList<TalkDetails> details50p;
    private ProgressDialog pDialog;
    private RecyclerView myRecyclerView;

    private SessionsAdapter sessionsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup Container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.event50pfragment,Container,false);
        mMapView = (MapView) view.findViewById(R.id.mapView50p);

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
                // googleMap.setMyLocationEnabled(true);
                // 12.8917° N, 77.5852° E
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

        Button buy50pTickets = (Button) view.findViewById(R.id.Buy50pTickets);
        buy50pTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                final String URI = "https://50p.in/2017/";
                intent.launchUrl(getActivity(), Uri.parse(URI));

            }
        });

        Button  ViewSchedule = (Button) view.findViewById(R.id.viewschedule);
        ViewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
//                final String URI = "https://50p.talkfunnel.com/2017/schedule";
//                intent.launchUrl(getActivity(), Uri.parse(URI));



                Intent intent = new Intent(v.getContext(), ScheduleActivity.class);
                startActivity(intent);




            }
        });


//        Button  Viewsepratetalk = (Button) view.findViewById(R.id.separatetalkview);
//        Viewsepratetalk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
////                final String URI = "https://50p.talkfunnel.com/2017/schedule";
////                intent.launchUrl(getActivity(), Uri.parse(URI));
//
//
//
//                Intent intent = new Intent(v.getContext(), ProposedTalksActivity.class);
//                startActivity(intent);
//
//
//
//
//            }
//        });

        myRecyclerView =(RecyclerView) view.findViewById(R.id.CardView50p);
        LinearLayoutManager myLayoutManager =new LinearLayoutManager(getActivity());
        myLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        //  myRecyclerView = (RecyclerView)view.findViewById(R.id.card_recycler_view);
        //     myRecyclerView.setHasFixedSize(true);
//    RecyclerView.LayoutManager myLayoutManager = new GridLayoutManager(getActivity(),2);
//
        myRecyclerView.setLayoutManager(myLayoutManager);











        makeJsonObjectRequest();

//        final TextView clickToSeeAllEvents = (TextView) view.findViewById(R.id.clicktosee50pproposedtalks);
//
//
//        clickToSeeAllEvents.setOnClickListener(new View.OnClickListener(){
//
//            public  void onClick (View view){
//                Fragment fragment = new TalksDetailViewFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.proposedTalks50p,fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                clickToSeeAllEvents.setVisibility(View.GONE);
//
//            }
//        });



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
                    JSONArray proposals50pArray = response.getJSONArray("proposals");
                    details50p = new ArrayList<TalkDetails>();
                    for(int i=0;i<proposals50pArray.length();i++) {
                        JSONObject talks50p = proposals50pArray.getJSONObject(i);

                        String speakerName = talks50p.getString("fullname");
                        String talkTitle = talks50p.getString("title");
                        String talkURL = talks50p.getString("url");

                        TalkDetails proposal50pDetails = new TalkDetails(speakerName,talkTitle,talkURL);

                        details50p.add(proposal50pDetails);



                        Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                      intent.putExtra("50pdetails",details50p);
                        //startActivity(intent);
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                myRecyclerView.setAdapter(new RecylerViewadapter(getActivity(),details50p));

                //  hidepDialog();
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