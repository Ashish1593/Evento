package com.examples.android.evento.model;

/**
 * Created by ankit on 27/12/16.
 */
import com.examples.android.evento.model.FoodCourtVendor;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class Metadata {
    @SerializedName("livestream_url")
    public String livestreamUrl;

    @SerializedName("venue_map_url")
    public String venueMapUrl;

    @SerializedName("announcements")
    public List<Announcements> announcements;

    @SerializedName("discussion_slack_deeplink")
    public String discussionSlackDeeplink;

    @SerializedName("discussion_slack_web")
    public String discussionSlackWeb;

    @SerializedName("foodcourt_vendors")
    public List<FoodCourtVendor> foodCourtVendors;

    public String getLivestreamUrl() {
        return livestreamUrl;
    }

    public void setLivestreamUrl(String livestreamUrl) {
        this.livestreamUrl = livestreamUrl;
    }

    public String getVenueMapUrl() {
        return venueMapUrl;
    }

    public void setVenueMapUrl(String venueMapUrl) {
        this.venueMapUrl = venueMapUrl;
    }

    public List<Announcements> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcements> announcements) {
        this.announcements = announcements;
    }

    public String getDiscussionSlackDeeplink() {
        return discussionSlackDeeplink;
    }

    public void setDiscussionSlackDeeplink(String discussionSlackDeeplink) {
        this.discussionSlackDeeplink = discussionSlackDeeplink;
    }

    public String getDiscussionSlackWeb() {
        return discussionSlackWeb;
    }

    public void setDiscussionSlackWeb(String discussionSlackWeb) {
        this.discussionSlackWeb = discussionSlackWeb;
    }

    public List<FoodCourtVendor> getFoodCourtVendors() {
        return foodCourtVendors;
    }

    public void setFoodCourtVendors(List<FoodCourtVendor> foodCourtVendors) {
        this.foodCourtVendors = foodCourtVendors;
    }
}
