package com.examples.android.evento;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * Created by ankit on 9/12/16.
 */

public class Event50p extends Fragment {
    MapView mMapView;
    private GoogleMap googleMap;
    private String urlJsonObj = "https://50p.talkfunnel.com/2017/json";
    private ArrayList<Details50p> details50p;
    private ProgressDialog pDialog;
    private RecyclerView myRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup Container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.event50pfragment,Container,false);
        mMapView = (MapView) view.findViewById(R.id.mapView50p);

        mMapView.onCreate(savedInstanceState);

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

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
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

        Button  propose50Psession = (Button) view.findViewById(R.id.propose50pSession);
        propose50Psession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                final String URI = "https://50p.talkfunnel.com/2017/new";
                intent.launchUrl(getActivity(), Uri.parse(URI));

            }
        });


        myRecyclerView =(RecyclerView) view.findViewById(R.id.CardView50p);
        LinearLayoutManager myLayoutManager =new LinearLayoutManager(getActivity());
        myLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        myRecyclerView.setLayoutManager(myLayoutManager);

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
                    JSONArray proposals50pArray = response.getJSONArray("proposals");
                    details50p = new ArrayList<Details50p>();
                    for(int i=0;i<proposals50pArray.length();i++) {
                        JSONObject talks50p = proposals50pArray.getJSONObject(i);

                        String speakerName = talks50p.getString("fullname");
                        String talkTitle = talks50p.getString("title");
                        String talkURL = talks50p.getString("url");

                        Details50p proprsal50pDetails = new Details50p (speakerName,talkTitle,talkURL);

                        details50p.add(proprsal50pDetails);




                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
//
                myRecyclerView.setAdapter(new RecylerViewadapter(details50p));

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



             //   hidepDialog();
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
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
