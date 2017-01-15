package com.examples.android.evento.fragments;


//import android.app.Fragment;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.examples.android.evento.R;
import com.examples.android.evento.activity.MainActivity;
import com.examples.android.evento.adapters.GridViewAdapter;

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
