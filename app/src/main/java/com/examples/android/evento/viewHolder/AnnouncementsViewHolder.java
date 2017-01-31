package com.examples.android.evento.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examples.android.evento.R;

/**
 * Created by ankit on 26/12/16.
 */

public class AnnouncementsViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView Description;
public LinearLayout announcement;

    public AnnouncementsViewHolder(View v){
        super(v);

        title = (TextView) v.findViewById(R.id.announcements_title);
        Description= (TextView) v.findViewById(R.id.announcements_Description);

announcement=(LinearLayout)v.findViewById(R.id.announcementclick);

    }}
