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
    private String enddate;

    public EventDetails(String eventName, String eventPlace, String eventDate, String eventURL, String endDate) {
        this.eventname = eventName;
        this.eventplace = eventPlace;
        this.eventdate = eventDate;
        this.eventurl = eventURL;
        this.enddate = endDate;
    }


    public String getEventname() {

        return eventname;
    }

    public String getEventplace() {
        return eventplace;
    }

    public String getEventdate() {
        return eventdate;
    }


    public String getEventURL() {
        return eventurl;
    }

    public String getEnddate() {
        return enddate;
    }


}
