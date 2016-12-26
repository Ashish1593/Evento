package com.examples.android.evento;

import android.app.Activity;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by ankit on 26/12/16.
 */

public class RecyclerViewAdapterAnnouncements extends RecyclerView.Adapter<AnnouncementsViewHolder> {
    private ArrayList<Announcements>detailsAnnouncements;
    Activity mContext;


    public RecyclerViewAdapterAnnouncements(Activity c,ArrayList<Announcements> detailsAnnouncements){
        this.detailsAnnouncements=detailsAnnouncements;
        this.mContext=c;

    }

    @Override
    public AnnouncementsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcementsitem,parent,false);
        AnnouncementsViewHolder viewHolder = new AnnouncementsViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final AnnouncementsViewHolder holder, final int  position){

        holder.title.setText(detailsAnnouncements.get(position).getTitle());
        holder.Description.setText(detailsAnnouncements.get(position).getDescription());

//
//        holder.openTalkDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
//                final String URI = detailsEventTalk.get(position).getTalkURL();
//                intent.launchUrl(mContext, Uri.parse(URI));
//
//            }
//        });



    }
    @Override
    public int getItemCount(){
        return detailsAnnouncements.size();
    }
}
