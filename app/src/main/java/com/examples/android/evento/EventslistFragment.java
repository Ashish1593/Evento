package com.examples.android.evento;


//import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.view.View.getDefaultSize;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by ankit on 30/11/16.
 */

public class EventslistFragment extends Fragment {
    //private ArrayList<EventDetails> details;


    private String urlJsonObj = "https://talkfunnel.com/json";

    private ProgressDialog pDialog;
    private int Position;
    private String name;
    private String date;
    private String place;
    private String url;

    GridView grid;
    // temporary string to show the parsed response
    private String jsonResponse;

//    public static EventslistFragment newInstance (String name, String place, String date, String url) {
//
//        EventslistFragment eventsFragment = new EventslistFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("Name",name);
//        bundle.putString("Place",place);
//        bundle.putString("Date",date);
//        bundle.putString("URL",url);
//
//        eventsFragment.setArguments(bundle);
//        return eventsFragment;

  //  }




    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

//                name  = getArguments().getString("Name");
//                place  = getArguments().getString("Place");
//                date = getArguments().getString("Date");
//                url = getArguments().getString("URL");
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.eventlist_fragment, container, false);
        grid=(GridView)rootView.findViewById(R.id.gridView);

        GridViewAdapter adapter = new GridViewAdapter(getActivity(),((MainActivity)getActivity()).details);

        grid.setAdapter(adapter);
      //  makeJsonObjectRequest();
        return rootView;
    }


}
