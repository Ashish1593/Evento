package com.examples.android.evento.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import com.examples.android.evento.R;
import com.examples.android.evento.adapters.RecyclerViewAdapterAnnouncements;
import com.examples.android.evento.controller.DataBaseController;
import com.examples.android.evento.model.Announcements;
import com.examples.android.evento.model.Metadata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by ankit on 25/12/16.
 **/

public class AnnouncementsActivity extends AppCompatActivity {

    public ArrayList<Announcements> announcementsArraylist;
    private DataBaseController db;
    private RecyclerView announcementRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.announcements);
        db = DataBaseController.getInstance(this);


        announcementRecyclerView = (RecyclerView) findViewById(R.id.announcements);
        LinearLayoutManager myLayoutManager = new LinearLayoutManager(this);
        myLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        announcementRecyclerView.setLayoutManager(myLayoutManager);


        String data = this.getIntent().getStringExtra("EventNameMetadata");
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Metadata metadata = gson.fromJson(db.getScheduleAndEventData(data), new TypeToken<Metadata>() {
        }.getType());


        announcementRecyclerView.setAdapter(new RecyclerViewAdapterAnnouncements(AnnouncementsActivity.this, metadata.getAnnouncements()));
        // makeJsonObjectRequest();

    }

}
