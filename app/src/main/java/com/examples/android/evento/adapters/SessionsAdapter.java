package com.examples.android.evento.adapters;

/**
 * Created by ankit on 31/1/17.
 */


import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.hasgeek.zalebi.R;
//import com.hasgeek.zalebi.api.ContactExchangeService;
//import com.hasgeek.zalebi.api.model.Room;
//import com.hasgeek.zalebi.api.model.Session;
import com.examples.android.evento.R;
import com.examples.android.evento.model.Session;
import com.vipul.hp_hp.timelineview.TimelineView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static com.facebook.accountkit.internal.AccountKitController.getApplicationContext;


public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.ListItemViewHolder> {

    private final Context context;
    private final List<Session> sessions;

    public SessionsAdapter(Context context, List<Session> sessions) {
        this.context = context;
        this.sessions = sessions;
    }


    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.fragment_space_schedule_list_row,
                        viewGroup,
                        false);

        return new ListItemViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
        final Session s = sessions.get(position);
        viewHolder.title.setText(s.getTitle());
        viewHolder.speaker.setText(s.getSpeaker());


        if (s.getRoom() == null) {
            //background.setBackgroundColor(inflater.getContext().getResources().getColor(R.color.colorPrimary));
            viewHolder.location.setText("Main Auditorium");
        } else if (s.getRoom().contains("audi")) {
            // background.setBackgroundColor(inflater.getContext().getResources().getColor(R.color.colorPrimary));
            viewHolder.location.setText("Main Auditorium");
        } else {
            //  background.setBackgroundColor(inflater.getContext().getResources().getColor(R.color.colorAccent));
            viewHolder.location.setText("Banquet Hall");
        }

        DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        m_ISO8601Local.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        final Calendar start = Calendar.getInstance();
        final Calendar end = Calendar.getInstance();

        try {
            start.setTime(m_ISO8601Local.parse(s.getStart()));
            end.setTime(m_ISO8601Local.parse(s.getEnd()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String duration = String.format("%d min", TimeUnit.MILLISECONDS.toMinutes(end.getTimeInMillis() - start.getTimeInMillis()));

        viewHolder.duration.setText(duration + "");

        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
        sdfs.setTimeZone(TimeZone.getDefault());

        String session_time = sdfs.format(start.getTime()) + "-" + sdfs.format(end.getTime());

        viewHolder.time.setText(session_time);


        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        viewHolder.date.setText(sd.format(start.getTime()));


        viewHolder.mListener = new ListItemViewHolder.ViewHolderClick() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle(s.getTitle())
                        .setMessage(Html.fromHtml(s.getDescription()))
                        .setCancelable(true)
                        .setPositiveButton("Ok", null)
                        .create().show();
            }
        };


    }


    @Override
    public int getItemCount() {

        if (sessions.size() == 0) {
        }
        return sessions.size();
    }


    public final static class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView date;
        public TextView speaker;
        public TextView time;
        public TextView duration;
        public TextView location;
        public LinearLayout colorIndicator;
        public ViewHolderClick mListener;
        public TimelineView mTimelineView;

        public ListItemViewHolder(View itemView, int viewType) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.fragment_space_schedule_list_row_title);
            speaker = (TextView) itemView.findViewById(R.id.fragment_space_schedule_list_row_speaker);
            time = (TextView) itemView.findViewById(R.id.fragment_space_schedule_list_row_time);
            duration = (TextView) itemView.findViewById(R.id.fragment_space_schedule_list_row_duration);
            //colorIndicator = (LinearLayout) itemView.findViewById(R.id.fragment_space_schedule_list_row_color_indicator);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
            location = (TextView) itemView.findViewById(R.id.room);
            date = (TextView) itemView.findViewById(R.id.date);
            mTimelineView.initLine(viewType);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }

        public static interface ViewHolderClick {
            public void onClick(View v);
        }
    }
}
