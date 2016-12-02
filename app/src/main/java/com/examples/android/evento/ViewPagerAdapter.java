package com.examples.android.evento;



//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import java.util.ArrayList;

/**
 * Created by ankit on 30/11/16.
 */


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

   private ArrayList<EventDetails> details;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<EventDetails> details) {
        super(fm);
     this.details= details;

    }

    @Override
    public  Fragment getItem(int Position) {

       return  EventsFragment.newInstance(details.get(Position).getEventname(),details.get(Position).getEventplace(),details.get(Position).getEventdate(),details.get(Position).getEventURL());

    }

    @Override
    public int getCount() {
        return this.details.size();
    }


}
