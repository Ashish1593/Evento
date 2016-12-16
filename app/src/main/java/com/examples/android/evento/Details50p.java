package com.examples.android.evento;

import java.util.ArrayList;

/**
 * Created by ankit on 16/12/16.
 */

public class Details50p extends ArrayList<Details50p> {

    private String speakerName;
    private String talkTitle;
    private String talkURL;

    public Details50p(String speakerName,String talkTitle,String talkURL) {

        this.speakerName =speakerName;
        this.talkTitle=talkTitle;
        this.talkURL=talkURL;
    }

    public String getSpeakerName(){
        return speakerName;
    }

    public String getTalkTitle(){
        return talkTitle;
    }

    public String gettalkURL(){
        return talkURL;
    }
}
