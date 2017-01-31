package com.examples.android.evento.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examples.android.evento.R;

/**
 * Created by ankit on 16/12/16.
 */

public class ViewHolder extends RecyclerView.ViewHolder{

    public TextView talkName;
    public TextView speakerName;
    public LinearLayout openTalkDetails;

    public ViewHolder(View v){
        super(v);

        talkName = (TextView) v.findViewById(R.id.talkname);
        speakerName= (TextView) v.findViewById(R.id.speaker);
        openTalkDetails=(LinearLayout) v.findViewById(R.id.openTalkDetails);
 }

}
