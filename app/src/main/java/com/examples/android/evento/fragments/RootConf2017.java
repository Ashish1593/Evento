package com.examples.android.evento.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examples.android.evento.R;
import com.examples.android.evento.activity.AnnouncementsActivity;
import com.examples.android.evento.activity.FoodCourtActivity;
import com.examples.android.evento.activity.MainActivity;
import com.examples.android.evento.activity.OpenWifi;
import com.examples.android.evento.activity.QRcodeScanner;

import com.examples.android.evento.adapters.SessionsAdapter;

import com.examples.android.evento.controller.DataBaseController;
import com.examples.android.evento.model.EventDetails;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.examples.android.evento.activity.MainActivity.SLACK_ANDROID_PACKAGE_NAME;


/**
 * Created by ankit on 1/2/17.
 */

public class RootConf2017 extends Fragment {

    MapView mMapView;
    String eventDate;
    private GoogleMap googleMap;
    private RecyclerView mRecyclerView;
    private TextView emptyView;
    private DataBaseController db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rootconf2017, container, false);

        mMapView = (MapView) view.findViewById(R.id.mapViewRootConf);

        mMapView.onCreate(null);

        mMapView.onResume();


        final android.support.v4.widget.NestedScrollView mainScrollView = (android.support.v4.widget.NestedScrollView) view.findViewById(R.id.scrollView);
        (view.findViewById(R.id.mapViewRootConf)).setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        mainScrollView.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        mainScrollView.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        mainScrollView.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });
        db = DataBaseController.getInstance(getActivity());

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                LatLng MLRConventionCenter = new LatLng(12.8917, 77.5852);
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


        Button buyRootConfTicket = (Button) view.findViewById(R.id.BuyrootconfTickets);
        buyRootConfTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                final String URI = "https://rootconf.in/2017/";
                intent.launchUrl(getActivity(), Uri.parse(URI));

            }
        });


        emptyView = (TextView) view.findViewById(R.id.empty_viewrootconf);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.schedule_recyclerviewrootconf);
        mRecyclerView.setNestedScrollingEnabled(false);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);


        final ImageView imageView = (ImageView) view.findViewById(R.id.viewlessmorerootconf);

        if (db.getCount("EventRootConf") != 0) {
            List<Session> sessionModel1 = new ArrayList<>();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            sessionModel1 = gson.fromJson(db.getScheduleAndEventData("EventRootConf"), new TypeToken<List<Session>>() {
            }.getType());

            if (sessionModel1.size() != 0) {

                final List<Session> sessionModel2 = sessionModel1.subList(0, 2);
                final List<Session> sessionModel3 = sessionModel1;

                mRecyclerView.setAdapter(new SessionsAdapter(getActivity(), sessionModel2));
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String backgroundImageName = String.valueOf(v.getTag());
                        if (backgroundImageName.equals("arrowdown")) {

                            mRecyclerView.setAdapter(new SessionsAdapter(getActivity(), sessionModel3));

                            imageView.setImageResource(R.drawable.arrowup);
                            imageView.setTag("arrowup");
                        } else {
                            mRecyclerView.setAdapter(new SessionsAdapter(getActivity(), sessionModel2));

                            imageView.setImageResource(R.drawable.arrowdown);
                            imageView.setTag("arrowdown");

                        }
                    }
                });


            } else {


                mRecyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
            }
        }


        ImageButton scanBadgeRootconf = (ImageButton) view.findViewById(R.id.scanBadgesRootconf);
        scanBadgeRootconf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String td = formatter.format(Calendar.getInstance().getTime());

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


        ImageButton connectToNetworkRootconf = (ImageButton) view.findViewById(R.id.connecttonetworkRootconf);
        connectToNetworkRootconf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String td = formatter.format(Calendar.getInstance().getTime());

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

        if (db.getCount("MetadataEventRootConf") != 0) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            metadata = gson.fromJson(db.getScheduleAndEventData("MetadataEventRootConf"), new TypeToken<Metadata>() {
            }.getType());
        } else
            metadata = null;


        ImageButton liveStreamButton = (ImageButton) view.findViewById(R.id.livestreamRootconf);
        liveStreamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (metadata != null) {
                    final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                    // final String URI = "https://pune.pycon.org/registration/";
                    final String URI = metadata.getLivestreamUrl();

                    intent.launchUrl(getActivity(), Uri.parse(URI));
                } else {
                    new android.app.AlertDialog.Builder(getActivity())
                            .setTitle("")
                            .setMessage(Html.fromHtml(" Available during Conference"))
                            .setCancelable(true)
                            .setPositiveButton("Ok", null)
                            .create().show();
                }
            }
        });


        ImageButton foodcourtButton = (ImageButton) view.findViewById(R.id.foodcourtRootconf);
        foodcourtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (metadata != null) {
                    Intent intent = new Intent(getActivity(), FoodCourtActivity.class);
                    intent.putExtra("EventNameMetadata", "MetadataEventRootConf");
                    startActivity(intent);
                } else {
                    new android.app.AlertDialog.Builder(getActivity())
                            .setTitle("")
                            .setMessage(Html.fromHtml("  Available during Conference"))
                            .setCancelable(true)
                            .setPositiveButton("Ok", null)
                            .create().show();
                }
            }
        });


        ImageButton discussionButton = (ImageButton) view.findViewById(R.id.discussionsRootconf);
        discussionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (metadata != null) {
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
                } else {
                    new android.app.AlertDialog.Builder(getActivity())
                            .setTitle("")
                            .setMessage(Html.fromHtml(" Available during Conference"))
                            .setCancelable(true)
                            .setPositiveButton("Ok", null)
                            .create().show();
                }
            }
        });


        ImageButton announcementButton = (ImageButton) view.findViewById(R.id.announcementsRootconf);
        announcementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (metadata != null) {
                    Intent intent = new Intent(getActivity(), AnnouncementsActivity.class);
                    intent.putExtra("EventNameMetadata", "MetadataEventRootConf");
                    startActivity(intent);
                } else {
                    new android.app.AlertDialog.Builder(getActivity())
                            .setTitle("")
                            .setMessage(Html.fromHtml(" Available during Conference"))
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
