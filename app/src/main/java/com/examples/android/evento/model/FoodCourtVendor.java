package com.examples.android.evento.model;

/**
 * Created by ankit on 27/12/16.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class FoodCourtVendor {

    @SerializedName("title")
    String title;

    @SerializedName("sections")
    List<FoodCourtVendorSection> sections;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<FoodCourtVendorSection> getSections() {
        return sections;
    }

    public void setSections(List<FoodCourtVendorSection> sections) {
        this.sections = sections;
    }
}