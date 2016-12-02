package com.examples.android.evento;

import java.util.ArrayList;

/**
 * Created by ankit on 30/11/16.
 */

public class EventDetails extends ArrayList<EventDetails> {

    private String eventname;
    private String eventplace;
    private String eventdate;

    public EventDetails(String eventName , String eventPlace,String eventDate)
    {
        eventname = eventName;
        eventplace= eventPlace;
        eventdate = eventDate;
   }


    public String getEventname(){

        return eventname;
    }
    public String getEventplace(){
        return eventplace;
    }

    public String getEventdate(){
        return eventdate;
    }



}
