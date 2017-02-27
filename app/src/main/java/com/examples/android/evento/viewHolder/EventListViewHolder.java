package com.examples.android.evento.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.examples.android.evento.R;

/**
 * Created by ankit on 2/2/17.
 */

public class EventListViewHolder extends RecyclerView.ViewHolder {


    public TextView evName;
    public TextView evPlace;
    public TextView evDate;
    public TextView evURL;
    public FrameLayout frameLayout;
    public TextView statustext;

    public EventListViewHolder(View v) {
        super(v);


        evName = (TextView) v.findViewById(R.id.textView_eventName);
        evPlace = (TextView) v.findViewById(R.id.textView_place);
        frameLayout = (FrameLayout) v.findViewById(R.id.frame);
        statustext = (TextView) v.findViewById(R.id.statustext);
    }
}



