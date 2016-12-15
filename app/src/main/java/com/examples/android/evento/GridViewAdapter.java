package com.examples.android.evento;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankit on 11/12/16.
 */

    public class GridViewAdapter extends BaseAdapter {
    private Activity mcontext;
    private final List<Fragment> mFragments = new ArrayList<Fragment>();
    private ArrayList<EventDetails> details;
    public GridViewAdapter(Activity c, ArrayList<EventDetails> details) {
        mcontext = c;
        this.details = details;
    }

    @Override
    public int getCount() {
        return details.size();
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

//    @Override
//    public Fragment getItem(int position) {
//
//      //  return EventslistFragment.newInstance(details.get(Position).getEventname(), details.get(Position).getEventplace(), details.get(Position).getEventdate(), details.get(Position).getEventURL());
//return null;
//    }


    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

           // grid = new View(mcontext);
            grid = inflater.inflate(R.layout.eventlistitem, null);

            TextView evName = (TextView) grid.findViewById(R.id.textView_eventName);
            TextView evPlace = (TextView) grid.findViewById(R.id.textView_place);
            TextView evDate = (TextView) grid.findViewById(R.id.textView_date);

            evName.setText(details.get(position).getEventname());
            evPlace.setText(details.get(position).getEventplace());
            evDate.setText(details.get(position).getEventdate());


            LinearLayout openEvents = (LinearLayout) grid.findViewById(R.id.openEvent);

            openEvents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                    final String URI = details.get(position).getEventURL();
                    intent.launchUrl(mcontext, Uri.parse(URI));

                }
            });

        } else {
            grid = (View) convertView;
        }


        return grid;
    }

}
