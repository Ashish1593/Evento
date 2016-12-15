package com.examples.android.evento;




//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by ankit on 30/11/16.
 */


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private Context mcontext;
private  final List <Fragment> mFragments = new ArrayList<Fragment>();
   private ArrayList<EventDetails> details;

//    public ViewPagerAdapter(FragmentManager fm, ArrayList<EventDetails> details) {
//        super(fm);
//     this.details= details;
//
//    }
public ViewPagerAdapter(FragmentManager manager) {
    super(manager);
}
public void addFragment(Fragment fragment){
    mFragments.add(fragment);
    notifyDataSetChanged();
}

//    @Override
//    public  Fragment getItem(int Position) {
//
//       return  EventslistFragment.newInstance(details.get(Position).getEventname(),details.get(Position).getEventplace(),details.get(Position).getEventdate(),details.get(Position).getEventURL());
//
//    }
    @Override
    public Fragment getItem(int position){
        return mFragments.get(position);
    }

//    @Override
//    public int getCount() {
//        return this.details.size();
//    }

    @Override
    public int getCount(){
        return mFragments.size();
    }

}




