package com.examples.android.evento.model;

import java.util.ArrayList;

/**
 * Created by ankit on 30/11/16.
 */

public class EventDetails {

    private String eventname;
    private String eventplace;
    private String eventdate;
    private String eventurl;
    public EventDetails(String eventName , String eventPlace,String eventDate,String eventURL)
    {
        eventname = eventName;
        eventplace= eventPlace;
        eventdate = eventDate;
        eventurl = eventURL;
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


    public String getEventURL(){
    return  eventurl;
}
}
