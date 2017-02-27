package com.examples.android.evento.model;

/**
 * Created by ankit on 27/12/16.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class FoodCourtVendorSection {

    @SerializedName("title")
    public String title;

    @SerializedName("items")
    public List<FoodCourtVendorItem> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<FoodCourtVendorItem> getItems() {
        return items;
    }

    public void setItems(List<FoodCourtVendorItem> items) {
        this.items = items;
    }
}