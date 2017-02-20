package com.examples.android.evento.fragments;

//import android.app.Fragment;

import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.examples.android.evento.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by ankit on 8/12/16.
 */

public class WorkshoponDNSandDNSSEC extends Fragment {
    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {
        View view = inflater.inflate(R.layout.workshopondnsanddnssecfragment, container, false);

        mMapView = (MapView) view.findViewById(R.id.mapViewDns);

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
                //  googleMap.setMyLocationEnabled(true);
                // create marker
                googleMap.getUiSettings().setZoomGesturesEnabled(true);

                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(12.9615, 77.6443)).title("Hello Maps");

//


                marker.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                // For dropping a marker at a point on the Map
                LatLng hasgeek = new LatLng(12.9615, 77.6443);
                googleMap.addMarker(new MarkerOptions().position(hasgeek).title("HasGeek").snippet("2699, 19th Main Rd, HAL 2nd Stage, Indiranagar, Bengaluru, Karnataka 560008"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(hasgeek).zoom(16).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });


        Button buyDnsTIckets = (Button) view.findViewById(R.id.BuyDNSTicket);
        buyDnsTIckets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                final String URI = "https://dnssec.hasgeek.com/";
                intent.launchUrl(getActivity(), Uri.parse(URI));

            }
        });

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
