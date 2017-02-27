package com.examples.android.evento.model;

public class Announcements {

    private String title;
    private String Description;
    private String URL;

    public Announcements(String title, String Description, String URL) {
        this.title = title;
        this.Description = Description;
        this.URL = URL;
    }

    public Announcements(String title, String Description) {
        this.title = title;
        this.Description = Description;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return Description;
    }

    public String getURL() {
        return URL;
    }
}
