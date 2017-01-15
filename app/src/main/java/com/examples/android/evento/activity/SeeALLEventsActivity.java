package com.examples.android.evento.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import com.examples.android.evento.model.EventDetails;
import com.examples.android.evento.adapters.GridViewAdapter;
import com.examples.android.evento.R;

import java.util.ArrayList;


/**
 * Created by ankit on 21/12/16.
 */

public class SeeALLEventsActivity extends AppCompatActivity {

    MainActivity mainActivity = new MainActivity();
    public ArrayList<EventDetails> details = mainActivity.details;

    GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.seeallevents);


        grid=(GridView)findViewById(R.id.gridView);

        GridViewAdapter adapter = new GridViewAdapter(this,details);

        grid.setAdapter(adapter);
}
    }
