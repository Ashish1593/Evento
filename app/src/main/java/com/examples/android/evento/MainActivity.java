package com.examples.android.evento;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.ToxicBakery.viewpager.transforms.FlipHorizontalTransformer;
import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;
import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.ToxicBakery.viewpager.transforms.ScaleInOutTransformer;
import com.ToxicBakery.viewpager.transforms.StackTransformer;
import com.ToxicBakery.viewpager.transforms.TabletTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;
import static com.examples.android.evento.R.id.viewpager;


public class MainActivity extends AppCompatActivity {

        private static String TAG = MainActivity.class.getSimpleName();
 // json object response url
        private String urlJsonObj = "https://talkfunnel.com/json";
        // json array response url
        private String urlJsonArry = "https://talkfunnel.com/json";
    private TextView eventName;
    private TextView eventPlace;
    private  TextView eventDate;
    private ArrayList<EventDetails> details ;


    // Progress dialog
       private ProgressDialog pDialog;

    private TextView txtResponse;

        // temporary string to show the parsed response
        private String jsonResponse;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);

            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            setContentView(R.layout.activity_main);
            eventName = (TextView) findViewById(R.id.textView_eventName);
            eventDate = (TextView) findViewById(R.id.textView_date);
            eventPlace = (TextView) findViewById(R.id.textView_place);
//
//     ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),details);
//            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//
//            viewPager.setAdapter(pagerAdapter);
//
//            viewPager.setPageTransformer(true, new CubeOutTransformer());


            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);




            makeJsonObjectRequest();


               }
        /**
         * Method to make json object request where json response starts wtih {
         * */
    /**
     * Method to make json object request where json response starts wtih {
     * */
    private void makeJsonObjectRequest() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    //  Parsing json object response
                    //response will be a json object
                    JSONArray eventsArray = response.getJSONArray("spaces");
                    details = new ArrayList<EventDetails>();
                    for(int i=0;i<eventsArray.length();i++) {
                        JSONObject events = eventsArray.getJSONObject(i);
                        String name = events.getString("title");
                        String location = events.getString("datelocation");
                        String date = events.getString("start");

                      EventDetails edetails = new EventDetails(name,location,date);

                        details.add(edetails);



                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),details);
               ViewPager viewPager = (ViewPager) findViewById(viewpager);

               viewPager.setAdapter(pagerAdapter);
               // viewPager.setPageTransformer(true, new RotateUpTransformer());
                //viewPager.setPageTransformer(true, new AccordionTransformer());
                //viewPager.setPageTransformer(true, new ScaleInOutTransformer());
                //viewPager.setPageTransformer(true, new ZoomInTransformer());
               // viewPager.setPageTransformer(true, new FlipHorizontalTransformer());
               // viewPager.setPageTransformer(true, new FlipVerticalTransformer());
               // viewPager.setPageTransformer(true, new TabletTransformer());
                //viewPager.setPageTransformer(true, new DepthPageTransformer());
               // viewPager.setPageTransformer(true, new FlipHorizontalTransformer());
               // viewPager.setPageTransformer(true, new CubeInTransformer());
              //  viewPager.setPageTransformer(true, new RotateDownTransformer());
               // viewPager.setPageTransformer(true, new StackTransformer());
               // viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
           viewPager.setPageTransformer(true, new CubeOutTransformer());
                hidepDialog();
       }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }



    /**
     * Method to make json array request where response starts with [
     */


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
           }

