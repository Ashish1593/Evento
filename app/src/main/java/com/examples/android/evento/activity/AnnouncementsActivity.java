package com.examples.android.evento.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.examples.android.evento.model.Announcements;
import com.examples.android.evento.controller.AppController;
import com.examples.android.evento.R;
import com.examples.android.evento.adapters.RecyclerViewAdapterAnnouncements;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ankit on 25/12/16.
 **/

public class AnnouncementsActivity extends AppCompatActivity {
    private static String TAG = AnnouncementsActivity.class.getSimpleName();
public String announcementsURL = "http://hasgeek.github.io/api/space/97/metadata";
   private RecyclerView announcementRecyclerView;
    public ArrayList<Announcements> announcementsArraylist ;
@Override
protected void onCreate(Bundle savedInstanceState)
{
    super.onCreate(savedInstanceState);

    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.announcements);




     announcementRecyclerView =(RecyclerView) findViewById(R.id.announcements);
    LinearLayoutManager myLayoutManager =new LinearLayoutManager(this);
    myLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    announcementRecyclerView.setLayoutManager(myLayoutManager);

    makeJsonObjectRequest();

}

    private void makeJsonObjectRequest() {

      //  showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                announcementsURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
            Log.d(TAG, response.toString());
                try {
                    //  Parsing json object response
                    //response will be a json object
                    JSONArray AnnouncementsArray = response.getJSONArray("announcements");
                    announcementsArraylist = new ArrayList<Announcements>();
                    for(int i=0;i<AnnouncementsArray.length();i++) {
                        JSONObject announcementsJsonContent = AnnouncementsArray.getJSONObject(i);
                        String title = announcementsJsonContent.getString("title");
                        String description = announcementsJsonContent.getString("description");

                        String url = announcementsJsonContent.getString("url");

                        if(url!=null) {
                            Announcements announcementsdetails = new Announcements(title, description, url);
                            announcementsArraylist.add(announcementsdetails);
                        }
else{
                            Announcements announcementsdetails = new Announcements(title, description);
                        announcementsArraylist.add(announcementsdetails);}

 }

  } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }


                announcementRecyclerView.setAdapter(new RecyclerViewAdapterAnnouncements(AnnouncementsActivity.this,announcementsArraylist));

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
              //  VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                 // hide the progress dialog
                     //   hidepDialog();

            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }





}
