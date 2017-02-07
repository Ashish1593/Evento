//package com.examples.android.evento.activity;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//
//import com.examples.android.evento.R;
//import com.examples.android.evento.adapters.RecylerViewadapter;
//
//import java.util.ArrayList;
//
///**
// * Created by ankit on 6/2/17.
// */
//
//public class ProposedTalksActivity extends AppCompatActivity {
//
//    RecyclerView mRecyclerView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//        WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        setContentView(R.layout.talksdetailview);
//
//        mRecyclerView = (RecyclerView) findViewById(R.id.separate_card_recycler_view);
//        mRecyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager llm = new GridLayoutManager(this,2);
//        mRecyclerView.setLayoutManager(llm);
//
//
//
//
//        ArrayList   talkdetails = (ArrayList) getIntent().getSerializableExtra("50pdetails");
//
//        mRecyclerView.setAdapter(new RecylerViewadapter(this,talkdetails));
//
//    }
//
//}
//
//
//
//
