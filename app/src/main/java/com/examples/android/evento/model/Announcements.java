package com.examples.android.evento.model;

import android.app.Activity;

/**
 * Created by ankit on 26/12/16.
 */

public class Announcements {

    private String title;
    private String Description;
  //  private String URL;

     public Announcements(String title,String Description)
     {
         this.title = title;
         this.Description=Description;
        // this.URL= URL;
     }


    public String  getTitle(){
        return title;
    }

    public String getDescription(){
        return Description;
    }
//    public String getURL(){
//        return URL;
//    }
}
