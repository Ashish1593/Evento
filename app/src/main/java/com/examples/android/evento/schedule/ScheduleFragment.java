//package com.examples.android.evento.schedule;
//
///**
// * Created by ankit on 30/1/17.
// */
//
//
//import android.content.Context;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import com.examples.android.evento.R;
//
//import com.examples.android.evento.interfacelistener.ItemInteractionListener;
//import com.hasgeek.funnel.data.SessionController;
//import com.hasgeek.funnel.space.SpaceActivity;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//
//
//public class ScheduleFragment extends Fragment{
//        public void l(String msg) {
//            Log.d(this.getClass().getSimpleName(), msg+" ");
//        }
//
//    private static final String EXTRA_SPACE_ID = "extra_space_id";
//    private static final String EXTRA_SESSION_DAY_OF_YEAR = "extra_day_of_year";
//    private String spaceId;
//    private int sessionDayOfYear;
//    private ItemInteractionListener<Session> sessionItemInteractionListener;
//    public ScheduleFragment() {
//    }
//
//    public static ScheduleFragment newInstance(String spaceId, int sessionDayOfYear) {
//        ScheduleFragment fragment = new ScheduleFragment();
//        Bundle arguments = new Bundle();
//        arguments.putString(EXTRA_SPACE_ID, spaceId);
//        arguments.putInt(EXTRA_SESSION_DAY_OF_YEAR, sessionDayOfYear);
//        fragment.setArguments(arguments);
//        return fragment;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof SpaceActivity) {
//            SpaceActivity spaceActivity = (SpaceActivity)getActivity();
//            spaceId = getArguments().getString(EXTRA_SPACE_ID, null);
//            sessionDayOfYear = getArguments().getInt(EXTRA_SESSION_DAY_OF_YEAR);
//            sessionItemInteractionListener = (ItemInteractionListener<Session>) spaceActivity.getSessionItemInteractionListener();
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_singleday_list, container, false);
//        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.canvas);
//
//
//        List<Session> allSessions = Realm.getDefaultInstance().copyFromRealm(SessionController.getSessionsBySpaceId(Realm.getDefaultInstance(), spaceId));
//
//        List<Session> sessions = new ArrayList<>();
//        for (Session s: allSessions) {
//            if (TimeUtils.getCalendarFromISODateString(s.getStart()).get(Calendar.DAY_OF_YEAR) == sessionDayOfYear)
//                sessions.add(s);
//        }
//        l("We have: "+sessions.size()+" sessions");
//
//        int trackWidth = (int) (DeviceController.getDeviceWidth() * 0.80);
//
//        int width = 0;
//        int height = 0;
//        for (final Session s: sessions) {
//            LinearLayout linearLayout = getScheduleViewForSession(inflater, relativeLayout, s);
//
//            int mul;
//            if (s.getRoom() == null) {
//                mul = 0;
//            } else if (s.getRoom().contains("audi")) {
//                mul = 0;
//            } else {
//                mul = 1;
//            }
//
//            int sessionWidth = trackWidth;
//            int marginLeft = sessionWidth * mul;
//
//            if (width < (marginLeft + sessionWidth))
//                width = marginLeft + sessionWidth;
//            if (height < (s.getMarginTop() + s.getHeight()))
//                height = s.getMarginTop() + s.getHeight();
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(sessionWidth, s.getHeight());
//            params.leftMargin = marginLeft;
//            params.topMargin = s.getMarginTop();
//            relativeLayout.addView(linearLayout, params);
//
//        }
//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(relativeLayout.getLayoutParams());
//        layoutParams.height = height + 200;
//        layoutParams.width = width;
//
//        ViewGroup parent = (ViewGroup)relativeLayout.getParent();
//        if (parent!=null){
//            parent.removeView(relativeLayout);
//        }
//        relativeLayout.setLayoutParams(layoutParams);
//        parent.addView(relativeLayout);
//        return view;
//    }
//
//    LinearLayout getScheduleViewForSession(LayoutInflater inflater, ViewGroup container, final Session s) {
//
//        LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.fragment_schedule_item, container, false);
//
//        RelativeLayout background = (RelativeLayout) linearLayout.findViewById(R.id.fragment_schedule_item_layout);
//
//        TextView title = (TextView) linearLayout.findViewById(R.id.fragment_schedule_item_title);
//        TextView speaker = (TextView) linearLayout.findViewById(R.id.fragment_schedule_item_speaker);
//        TextView location = (TextView) linearLayout.findViewById(R.id.fragment_schedule_item_location);
//        TextView starttime = (TextView) linearLayout.findViewById(R.id.fragment_schedule_item_starttime);
//        TextView endtime = (TextView) linearLayout.findViewById(R.id.fragment_schedule_item_endtime);
//
//        title.setText(s.getTitle());
//
//        String speakerText = s.getSpeaker();
//
//        if (speakerText==null) {
//            speaker.setVisibility(View.GONE);
//        } else if (speakerText.equals("")) {
//            speaker.setVisibility(View.GONE);
//        } else {
//            speaker.setText(speakerText);
//        }
//
//
//        if(s.getRoom()==null) {
//            background.setBackgroundColor(inflater.getContext().getResources().getColor(R.color.colorPrimary));
//            location.setText("Main Auditorium");
//        } else if(s.getRoom().contains("audi")) {
//            background.setBackgroundColor(inflater.getContext().getResources().getColor(R.color.colorPrimary));
//            location.setText("Main Auditorium");
//        }
//        else {
//            background.setBackgroundColor(inflater.getContext().getResources().getColor(R.color.colorAccent));
//            location.setText("Banquet Hall");
//        }
//
//        if (s.getIsBreak()) {
//            background.setBackgroundColor(Color.LTGRAY);
//            background.setClickable(false);
//        } else {
//            linearLayout.setClickable(true);
//            background.setClickable(true);
//            background.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //Answers.getInstance().logContentView(new ContentViewEvent()
//                          //  .putContentName(s.getTitle())
//                          //  .putContentType("Session View")
//                          //  .putCustomAttribute("Referrer", "Schedule")
//                          //  .putContentId(s.getId()));
//                    sessionItemInteractionListener.onItemClick(view, s);
//                }
//            });
//
//        }
//
//        starttime.setText(TimeUtils.getSimpleTimeForString(s.getStart()));
//
//        endtime.setText(TimeUtils.getSimpleTimeForString(s.getEnd()));
//
//        return linearLayout;
//
//    }
//
////    @Override
////    public void refresh() {
////
////    }
////
////    @Override
////    public void notFoundError() {
////
////    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }
//
//
//}
