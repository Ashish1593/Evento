package com.examples.android.evento.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by ankit on 16/12/16.
 */

public class TalkDetails implements Serializable {

    private String speakerName;
    private String talkTitle;
    private String talkURL;

    public TalkDetails(String speakerName, String talkTitle, String talkURL) {

        this.speakerName = speakerName;
        this.talkTitle = talkTitle;
        this.talkURL = talkURL;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public String getTalkTitle() {
        return talkTitle;
    }

    public String getTalkURL() {
        return talkURL;
    }


}
