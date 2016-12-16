package com.examples.android.evento;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ankit on 16/12/16.
 */

public class ViewHolder50p extends RecyclerView.ViewHolder{

    public TextView talkName;
    public TextView speakerName;

    public ViewHolder50p(View v){
        super(v);

        talkName = (TextView) v.findViewById(R.id.talkname);
        speakerName= (TextView) v.findViewById(R.id.speaker);



    }

}
