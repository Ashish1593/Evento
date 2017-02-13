package com.examples.android.evento.adapters;

import android.app.Activity;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.examples.android.evento.R;
import com.examples.android.evento.model.TalkDetails;
import com.examples.android.evento.viewHolder.ViewHolder;

import java.util.ArrayList;

/**
 * Created by ankit on 16/12/16.
 */

public class RecylerViewadapter extends RecyclerView.Adapter<ViewHolder> {

    Activity mContext;
    private ArrayList<TalkDetails> detailsEventTalk;


    public RecylerViewadapter(Activity c, ArrayList<TalkDetails> detailsEventTalk) {
        this.detailsEventTalk = detailsEventTalk;
        this.mContext = c;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.talksdetailsitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.talkName.setText(detailsEventTalk.get(position).getTalkTitle());
        holder.speakerName.setText(detailsEventTalk.get(position).getSpeakerName());


        holder.openTalkDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                final String URI = detailsEventTalk.get(position).getTalkURL();
                intent.launchUrl(mContext, Uri.parse(URI));

            }
        });
    }

    @Override
    public int getItemCount() {
        return detailsEventTalk.size();
    }

}

