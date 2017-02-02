package com.examples.android.evento.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examples.android.evento.model.EventDetails;
import com.examples.android.evento.R;
import com.examples.android.evento.viewHolder.AnnouncementsViewHolder;
import com.examples.android.evento.viewHolder.EventListViewHolder;
import com.examples.android.evento.viewHolder.ViewHolder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ankit on 11/12/16.
 */

    public class GridViewAdapter extends RecyclerView.Adapter<EventListViewHolder> {
    private Activity mcontext;
    private final List<Fragment> mFragments = new ArrayList<Fragment>();
    private ArrayList<EventDetails> details;

    public GridViewAdapter(Activity c, ArrayList<EventDetails> details) {
        mcontext = c;
        this.details = details;
    }


    @Override
    public EventListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventlistitem, parent, false);
        EventListViewHolder viewHolder = new EventListViewHolder(view);

        return viewHolder;

    }


    @Override
    public void onBindViewHolder(final EventListViewHolder holder, final int position) {

        holder.evName.setText(details.get(position).getEventname());
        holder.evPlace.setText(details.get(position).getEventplace());
        holder.evDate.setText(details.get(position).getEventdate());
        holder.evURL.setText(details.get(position).getEventURL());


        String d =details.get(position).getEventdate();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String td = formatter.format(Calendar.getInstance().getTime());


//
//        if(date1.after(date2)){
//            System.out.println("Date1 is after Date2");
//        }
//
//        //before() will return true if and only if date1 is before date2
//        if(date1.before(date2)){
//            System.out.println("Date1 is before Date2");
//        }
//
//        //equals() returns true if both the dates are equal
//        if(date1.equals(date2)){
//            System.out.println("Date1 is equal Date2");
//        }



        try {

            Date date = formatter.parse(d);
            Date todaysdate = formatter.parse(td);

            if(date.before(todaysdate))
            {
         holder.frameLayout.setBackgroundColor(Color.parseColor("#20000000"));
                holder.statustext.setText("PAST EVENTS");
                holder.statustext.setTextColor(Color.RED);
            }
            else{
                holder.frameLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.statustext.setText("UPCOMING");
                holder.statustext.setTextColor(Color.GREEN);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.evURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                final String URI = details.get(position).getEventURL();
                intent.launchUrl(mcontext, Uri.parse(URI));

            }


        });

    }
        @Override
        public int getItemCount () {
            return details.size();
        }


    }


