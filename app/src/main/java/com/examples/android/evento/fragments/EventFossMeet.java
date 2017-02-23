package com.examples.android.evento.fragments;

//import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examples.android.evento.activity.AnnouncementsActivity;
import com.examples.android.evento.activity.FoodCourtActivity;
import com.examples.android.evento.activity.MainActivity;
import com.examples.android.evento.activity.OpenWifi;
import com.examples.android.evento.activity.QRcodeScanner;
import com.examples.android.evento.adapters.SessionsAdapter;

import com.examples.android.evento.R;
import com.examples.android.evento.controller.DataBaseController;
import com.examples.android.evento.model.Metadata;
import com.examples.android.evento.model.Session;
import com.examples.android.evento.model.TalkDetails;

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
import com.google.gson.reflect.TypeToken;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import static com.examples.android.evento.activity.MainActivity.SLACK_ANDROID_PACKAGE_NAME;


/**
 * Created by ankit on 11/12/16.
 */

public class EventFossMeet extends Fragment {
    MapView mMapView;
    private GoogleMap googleMap;
    private String urlJsonObj = "https://fossmeet-nitc.talkfunnel.com/2017/json";
    private ArrayList<TalkDetails> detailsFossMeet;
    private ProgressDialog pDialog;
    private RecyclerView mRecyclerView;
private DataBaseController db;
    private TextView emptyView;
    String eventDate = "2017-03-10";
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.fossmeet2017,container,false);
        db = DataBaseController.getInstance(getActivity());

        mMapView = (MapView) view.findViewById(R.id.mapViewFossMeet);

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

                LatLng NITCalicut = new LatLng(11.3217, 75.9342);
                googleMap.addMarker(new MarkerOptions().position(NITCalicut).title("NIT").snippet("National Institute Of Technology Calicut"));

                MarkerOptions mo = new MarkerOptions().position(NITCalicut).title("NIT").snippet("National Institute Of Technology Calicut").visible(true);
                Marker marker = googleMap.addMarker(mo);
                mo.anchor(0f, 0.5f);
                marker.showInfoWindow();
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(NITCalicut).zoom(16).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });



        Button buyFossmeetTickets = (Button) view.findViewById(R.id.BuyfossmeetTickets);
        buyFossmeetTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                final String URI = "http://fossmeet.in/2017/";
                intent.launchUrl(getActivity(), Uri.parse(URI));

            }
        });

        emptyView = (TextView) view.findViewById(R.id.empty_viewfosmeet);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.schedule_recyclerviewfossmeet);
        mRecyclerView.setNestedScrollingEnabled(false);
        //  mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        final ImageView imageView = (ImageView) view.findViewById(R.id.viewlessmorefossmeet);
        if( db.getCount("EventFossMeet") !=0) {
            List<Session> sessionModel1 = new ArrayList<>();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            sessionModel1 = gson.fromJson(db.getScheduleAndEventData("EventFossMeet"), new TypeToken<List<Session>>() {
            }.getType());

            if (sessionModel1.size() != 0) {

                final List<Session>  sessionModel2 = sessionModel1.subList(0,2);
                final List<Session> sessionModel3 = sessionModel1;

                mRecyclerView.setAdapter(new SessionsAdapter(getActivity(), sessionModel2));
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String backgroundImageName = String.valueOf(v.getTag());
                        if (backgroundImageName.equals("arrowdown")){

                            mRecyclerView.setAdapter(new SessionsAdapter(getActivity(), sessionModel3));

                            imageView.setImageResource(R.drawable.arrowup);
                            imageView.setTag("arrowup");
                        }

                        else {
                            mRecyclerView.setAdapter(new SessionsAdapter(getActivity(), sessionModel2));

                            imageView.setImageResource(R.drawable.arrowdown);
                            imageView.setTag("arrowdown");

                        }
                    }
                });



            } else {
                //     makeJsonObjectRequest();
                mRecyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
            }// makeJsonObjectRequest();

        }

        ImageButton scanBadgeFossmeet = (ImageButton) view.findViewById(R.id.scanBadgesFossmeet);
        scanBadgeFossmeet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){



                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String td= formatter.format(Calendar.getInstance().getTime());

                try {

                    Date date = formatter.parse(eventDate);
                    Date todaysdate = formatter.parse(td);

                    if (date.after(todaysdate)) {
                        new android.app.AlertDialog.Builder(getActivity())
                                .setTitle("")
                                .setMessage(Html.fromHtml("  Available during Conference"))
                                .setCancelable(true)
                                .setPositiveButton("Ok", null)
                                .create().show();


                    } else {
                        Intent intent = new Intent(getActivity(), QRcodeScanner.class);
                        startActivity(intent);


                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        ImageButton connectToNetworkFossmeet = (ImageButton) view.findViewById(R.id.connecttonetworkFossmeet);
        connectToNetworkFossmeet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){



                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String td= formatter.format(Calendar.getInstance().getTime());

                try {

                    Date date = formatter.parse(eventDate);
                    Date todaysdate = formatter.parse(td);
                    if (date.after(todaysdate)) {
                        new android.app.AlertDialog.Builder(getActivity())
                                .setTitle("")
                                .setMessage(Html.fromHtml("  Available during Conference"))
                                .setCancelable(true)
                                .setPositiveButton("Ok", null)
                                .create().show();


                    } else {
                        Intent intent = new Intent(getActivity(), OpenWifi.class);
                        startActivity(intent);


                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        final Metadata metadata;
        if(db.getCount("MetadataEventFossMeet")!=0) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            metadata = gson.fromJson(db.getScheduleAndEventData("MetadataEventFossMeet"), new TypeToken<Metadata>() {
            }.getType());
        }
        else
            metadata = null;



        ImageButton liveStreamButton = (ImageButton) view.findViewById(R.id.livestreamFossmeet);
        liveStreamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(metadata!=null) {
                    final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                    // final String URI = "https://pune.pycon.org/registration/";
                    final String URI = metadata.getLivestreamUrl();

                    intent.launchUrl(getActivity(), Uri.parse(URI));
                }
                else
                {
                    new android.app.AlertDialog.Builder(getActivity())
                            .setTitle("")
                            .setMessage(Html.fromHtml("  Available during Conference"))
                            .setCancelable(true)
                            .setPositiveButton("Ok", null)
                            .create().show();
                }
            }
        });



        ImageButton foodcourtButton = (ImageButton) view.findViewById(R.id.foodcourtFossmeet);
        foodcourtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(metadata!=null) {
                    Intent intent = new Intent(getActivity(),FoodCourtActivity.class);
                    intent.putExtra("EventNameMetadata","MetadataEventFossMeet");
                    startActivity(intent);
                }
                else
                {
                    new android.app.AlertDialog.Builder(getActivity())
                            .setTitle("")
                            .setMessage(Html.fromHtml("Available during Conference"))
                            .setCancelable(true)
                            .setPositiveButton("Ok", null)
                            .create().show();
                }
            }
        });


        ImageButton discussionButton = (ImageButton) view.findViewById(R.id.discussionsFossmeet);
        discussionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(metadata!=null) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Join the discussion!")
                            .setMessage("Are you on the Friends of HasGeek Slack team? Follow the discussion on the our channel")
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String uri;
                                    if (MainActivity.isPackageInstalled(SLACK_ANDROID_PACKAGE_NAME, getActivity().getPackageManager()))
                                        uri = metadata.getDiscussionSlackDeeplink();
                                    else
                                        // uri = "https://friendsofhasgeek.slack.com/messages/droidcon/";
                                        uri = metadata.getDiscussionSlackWeb();
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(uri));
                                    startActivity(i);
                                }
                            })
                            .setNegativeButton("No, send me an invite", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder()
                                            .setToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

                                    CustomTabsIntent customTabsIntent = builder.build();
                                    customTabsIntent.launchUrl(getActivity(), Uri.parse("https://friends.hasgeek.com/"));
                                }
                            })
                            .create().show();
                }
                else
                {
                    new android.app.AlertDialog.Builder(getActivity())
                            .setTitle("")
                            .setMessage(Html.fromHtml(" Available during Conference"))
                            .setCancelable(true)
                            .setPositiveButton("Ok", null)
                            .create().show();
                }
            }
        });





        ImageButton announcementButton = (ImageButton) view.findViewById(R.id.announcementsFossmeet);
        announcementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(metadata!=null) {
                    Intent intent = new Intent(getActivity(),AnnouncementsActivity.class);
                    intent.putExtra("EventNameMetadata","MetadataEventFossMeet");
                    startActivity(intent);
                }
                else
                {
                    new android.app.AlertDialog.Builder(getActivity())
                            .setTitle("")
                            .setMessage(Html.fromHtml("  Available during Conference"))
                            .setCancelable(true)
                            .setPositiveButton("Ok", null)
                            .create().show();
                }
            }
        });





        //  makeJsonObjectRequest();



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


}
