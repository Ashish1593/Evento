package com.examples.android.evento;

/**
 * Created by ankit on 27/12/16.
 */
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class Metadata {
    @SerializedName("foodcourt_vendors")
    public List<FoodCourtVendor> foodCourtVendors;

    public  List<FoodCourtVendor> getFoodCourtVendors() {
        return foodCourtVendors;
    }

    public void setFoodCourtVendors(List<FoodCourtVendor> foodCourtVendors) {
        this.foodCourtVendors = foodCourtVendors;
    }
}
