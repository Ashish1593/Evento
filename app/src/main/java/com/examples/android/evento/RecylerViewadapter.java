package com.examples.android.evento;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by ankit on 16/12/16.
 */

public class RecylerViewadapter extends RecyclerView.Adapter<ViewHolder50p> {

    private ArrayList<Details50p>details50pEvent;

public RecylerViewadapter(ArrayList<Details50p> details50pEvent){
    this.details50pEvent=details50pEvent;

}

    @Override
    public ViewHolder50p onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.talksdetails,parent,false);
        ViewHolder50p viewHolder = new ViewHolder50p(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder50p holder,int position){

        holder.talkName.setText(details50pEvent.get(position).getTalkTitle());
        holder.speakerName.setText(details50pEvent.get(position).getSpeakerName());


    }
@Override
    public int getItemCount(){
    return details50pEvent.size();
}

}

