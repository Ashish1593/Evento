package com.examples.android.evento.model;

        import com.google.gson.annotations.SerializedName;


public class FoodCourtVendorItem {


    @SerializedName("title")
    public String title;

    @SerializedName("type")
    public String type;

    @SerializedName("price")
    public String price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}