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
//        TextView evName = (TextView) rootView.findViewById(R.id.textView_eventName) ;
//        TextView evPlace = (TextView) rootView.findViewById(R.id.textView_place);
//        TextView evDate = (TextView) rootView.findViewById(R.id.textView_date);
//        //Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Pacifico.ttf");
//        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "FFF_Tusj.ttf");
//        evName.setTypeface(font);
//
//
//        evPlace.setTypeface(font);
//
//
//        evDate.setTypeface(font);
//
//        evName.setText(name);
//        evDate.setText(date);
//        evPlace.setText(place);

//        LinearLayout linkArea = (LinearLayout) rootView.findViewById(R.id.openlink);
//
//        linkArea.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View v){
//                Intent myWebLink = new Intent (Intent.ACTION_VIEW);
//                myWebLink.setData(Uri.parse(url));
//                startActivity(myWebLink);
//            }
//        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent myWebLink = new Intent (Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse(url));
                startActivity(myWebLink);


            }
        });
//        Random r = new Random();
//        int randomNumber = r.nextInt(10 - 1) + 1;
//
//        ImageView image = (ImageView) rootView.findViewById(R.id.imgRandom);
//        String imageName = "image_" + randomNumber;
//       int  image_ID = getResources().getIdentifier(imageName, "drawable", getActivity().getPackageName());
//
//        image.setBackgroundResource(image_ID);


        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

//
//      //  final WebView webView = (WebView) rootView.findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url)
//            {
//
//                webView.loadUrl("javascript:(function() { " +
//                        "document.getElementsByTagName('section')[0].style.display='none'; " +
//                        " document.getElementsByTagName('section')[2].style.display='none';"+"" +
//                        "document.getElementsByTagName('section')[3].style.display='none';"+"" +
//                        "document.getElementsByTagName('section')[4].style.display='none';"+"" +
//                        "document.getElementsByTagName('footer')[0].style.display='none';" +
//                        "document.getElementsByTagName('header')[0].style.display='none';" +
//                        "document.getElementsByTagName('nav')[0].style.display='none'})()");
//            }
//        });
//        webView.loadUrl("https://50p.in/2017/");

        GridViewAdapter adapter = new GridViewAdapter(getActivity(),((MainActivity)getActivity()).details);

        grid.setAdapter(adapter);
      //  makeJsonObjectRequest();
        return rootView;
    }

    /**
     * Method to make json object request where json response starts wtih {
     * */


    /**
     * Method to make json object request where json response starts wtih {
     * */
    /**
     * Method to make json object request where json response starts wtih {
     * */
//    private void makeJsonObjectRequest() {
//
//        showpDialog();
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//                urlJsonObj, null, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d(TAG, response.toString());
//                try {
//                    //  Parsing json object response
//                    //response will be a json object
//                    JSONArray eventsArray = response.getJSONArray("spaces");
//                    details = new ArrayList<EventDetails>();
//                    for(int i=0;i<eventsArray.length();i++) {
//                        JSONObject events = eventsArray.getJSONObject(i);
//                        String name = events.getString("title");
//                        String location = events.getString("datelocation");
//                        String date = events.getString("start");
//                        String URL = events.getString("url");
//
//                        EventDetails edetails = new EventDetails(name,location,date,URL);
//
//                        details.add(edetails);
//
//
//
//                    }
//
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getActivity().getApplicationContext(),
//                            "Error: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
////
//
//                GridViewAdapter adapter = new GridViewAdapter(getContext(), details);
//
//                grid.setAdapter(adapter);
////                ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),details);
////               ViewPager viewPager = (ViewPager) findViewById(viewpager);
////
////               viewPager.setAdapter(pagerAdapter);
//
////               // viewPager.setPageTransformer(true, new RotateUpTransformer());
//                //viewPager.setPageTransformer(true, new AccordionTransformer());
//                //viewPager.setPageTransformer(true, new ScaleInOutTransformer());
//                //viewPager.setPageTransformer(true, new ZoomInTransformer());
//                // viewPager.setPageTransformer(true, new FlipHorizontalTransformer());
//                // viewPager.setPageTransformer(true, new FlipVerticalTransformer());
//                // viewPager.setPageTransformer(true, new TabletTransformer());
//                //viewPager.setPageTransformer(true, new DepthPageTransformer());
//                // viewPager.setPageTransformer(true, new FlipHorizontalTransformer());
//                // viewPager.setPageTransformer(true, new CubeInTransformer());
//                //    viewPager.setPageTransformer(true, new RotateDownTransformer());
//                // viewPager.setPageTransformer(true, new StackTransformer());
//                // viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
//                // viewPager.setPageTransformer(true, new CubeOutTransformer());
//                hidepDialog();
//            }
//
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getActivity().getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
//                // hide the progress dialog
//                hidepDialog();
//            }
//        });
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(jsonObjReq);
//    }
//
//
//
//    /**
//     * Method to make json array request where response starts with [
//     */
//
//
//    private void showpDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//
//    private void hidepDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }
}
