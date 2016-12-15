package com.examples.android.evento;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by ankit on 13/12/16.
 */

public class SeeAllEvents extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View view = inflater.inflate(R.layout.seeallevents,container,false);


        Button clickToSeeAllEvents = (Button) view.findViewById(R.id.seeAllEvents);


        clickToSeeAllEvents.setOnClickListener(new View.OnClickListener(){

         public void onClick (View view){
             Fragment fragment = new EventslistFragment();
             FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
             FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
             fragmentTransaction.replace(R.id.viewpager,fragment);
             fragmentTransaction.addToBackStack(null);
             fragmentTransaction.commit();

         }
        });
        return  view;
    }


}
