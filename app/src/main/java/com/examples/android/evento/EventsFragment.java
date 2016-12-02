package com.examples.android.evento;


//import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by ankit on 30/11/16.
 */

public class EventsFragment extends Fragment {

    private String urlJsonObj = "https://talkfunnel.com/json";

    private ProgressDialog pDialog;
    private int Position;
    private String name;
    private String date;
    private String place;
    private String url;


    // temporary string to show the parsed response
    private String jsonResponse;

    public static EventsFragment newInstance (String name, String place, String date,String url) {

        EventsFragment eventsFragment = new EventsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Name",name);
        bundle.putString("Place",place);
        bundle.putString("Date",date);
        bundle.putString("URL",url);

        eventsFragment.setArguments(bundle);
        return eventsFragment;

    }




    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

                name  = getArguments().getString("Name");
                place  = getArguments().getString("Place");
                date = getArguments().getString("Date");
                url = getArguments().getString("URL");
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.events_fragment, container, false);


        TextView evName = (TextView) rootView.findViewById(R.id.textView_eventName) ;
        TextView evPlace = (TextView) rootView.findViewById(R.id.textView_place);
        TextView evDate = (TextView) rootView.findViewById(R.id.textView_date);

        evName.setText(name);
        evDate.setText(date);
        evPlace.setText(place);

        LinearLayout linkArea = (LinearLayout) rootView.findViewById(R.id.openlink);

        linkArea.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){
                Intent myWebLink = new Intent (Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse(url));
                startActivity(myWebLink);
            }
        });

        Random r = new Random();
        int randomNumber = r.nextInt(10 - 1) + 1;

        ImageView image = (ImageView) rootView.findViewById(R.id.imgRandom);
        String imageName = "image_" + randomNumber;
       int  image_ID = getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());

        image.setBackgroundResource(image_ID);


        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);




        return rootView;
    }

    /**
     * Method to make json object request where json response starts wtih {
     * */

}
