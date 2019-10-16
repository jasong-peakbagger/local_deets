package com.jgoodwin.localdeets.api.model.Traffic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StartPoint {

    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Direction")
    @Expose
    private String direction;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("MilePost")
    @Expose
    private Double milePost;
    @SerializedName("RoadName")
    @Expose
    private String roadName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getMilePost() {
        return milePost;
    }

    public void setMilePost(Double milePost) {
        this.milePost = milePost;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

}
