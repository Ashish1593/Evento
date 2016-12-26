package com.examples.android.evento;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ankit on 26/12/16.
 */

public class AnnouncementsViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView Description;


    public AnnouncementsViewHolder(View v){
        super(v);

        title = (TextView) v.findViewById(R.id.announcements_title);
        Description= (TextView) v.findViewById(R.id.announcements_Description);



    }}
