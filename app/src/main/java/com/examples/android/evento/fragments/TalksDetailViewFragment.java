//package com.examples.android.evento.fragments;
//
//import android.support.v4.app.Fragment;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.GridView;
//
//import com.examples.android.evento.R;
//
///**
// * Created by ankit on 26/12/16.
// */
//
//public class TalksDetailViewFragment extends Fragment {
//
//    public   RecyclerView myRecyclerView;
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//
////                name  = getArguments().getString("Name");
////                place  = getArguments().getString("Place");
////                date = getArguments().getString("Date");
////                url = getArguments().getString("URL");
//    }
//
//
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        final View rootView = inflater.inflate(R.layout.talksdetailview, container, false);
//
//        myRecyclerView = (RecyclerView) rootView.findViewById(R.id.separate_card_recycler_view);
//        myRecyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager myLayoutManager = new GridLayoutManager(getActivity(),2);
//
//        myRecyclerView.setLayoutManager(myLayoutManager);
//
//      //  myRecyclerView.setAdapter(new RecylerViewadapter(getActivity(),getActivity().details50p));
//
////        grid=(GridView)rootView.findViewById(R.id.gridView);
////
////        GridViewAdapter adapter = new GridViewAdapter(getActivity(),((MainActivity)getActivity()).details);
////
////        grid.setAdapter(adapter);
//
//
////
////        myRecyclerView =(RecyclerView) rootView.findViewById(R.id.card_recycler_view);
////        RecyclerView.LayoutManager myLayoutManager = new GridLayoutManager(getActivity(),2);
////        myRecyclerView.setLayoutManager(myLayoutManager);
//
//        //  makeJsonObjectRequest();
//        return rootView;
//    }
//
//}
