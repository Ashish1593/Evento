package com.examples.android.evento.model;

import java.util.ArrayList;

/**
 * Created by ankit on 16/12/16.
 */

public class TalkDetails {

    private String speakerName;
    private String talkTitle;
    private String talkURL;

    public TalkDetails(String speakerName, String talkTitle, String talkURL) {

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

    public String getTalkURL(){
        return talkURL;
    }
}
