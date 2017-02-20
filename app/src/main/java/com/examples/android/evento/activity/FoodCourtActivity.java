package com.examples.android.evento.activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.view.Window;
import android.view.WindowManager;

import com.examples.android.evento.adapters.FoodCourtVendorPagerAdapter;
import com.examples.android.evento.controller.DataBaseController;

import com.examples.android.evento.model.Metadata;
import com.examples.android.evento.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class FoodCourtActivity extends AppCompatActivity {

    public ViewPager viewPager;
    public TabLayout tabLayout;
    private DataBaseController db;

    // FoodCourtVendorPagerAdapter foodCourtVendorPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_foodcourt);
        db = DataBaseController.getInstance(this);
        viewPager = (ViewPager) findViewById(R.id.activity_foodcourt_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.activity_foodcourt_tablayout);


        String data = this.getIntent().getStringExtra("EventNameMetadata");
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Metadata metadata = gson.fromJson(db.getScheduleAndEventData(data), new TypeToken<Metadata>() {
        }.getType());


        FoodCourtVendorPagerAdapter foodCourtVendorPagerAdapter = new FoodCourtVendorPagerAdapter(FoodCourtActivity.this, metadata.getFoodCourtVendors());

        viewPager.setAdapter(foodCourtVendorPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }

}
