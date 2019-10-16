package com.jgoodwin.localdeets.api.model.Traffic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Traffic {

    @SerializedName("AverageTime")
    @Expose
    private Long averageTime;
    @SerializedName("CurrentTime")
    @Expose
    private Long currentTime;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Distance")
    @Expose
    private Double distance;
    @SerializedName("EndPoint")
    @Expose
    private EndPoint endPoint;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("StartPoint")
    @Expose
    private StartPoint startPoint;
    @SerializedName("TimeUpdated")
    @Expose
    private String timeUpdated;
    @SerializedName("TravelTimeID")
    @Expose
    private Long travelTimeID;

    public Long getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(Long averageTime) {
        this.averageTime = averageTime;
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public EndPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(EndPoint endPoint) {
        this.endPoint = endPoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StartPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(StartPoint startPoint) {
        this.startPoint = startPoint;
    }

    public String getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(String timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public Long getTravelTimeID() {
        return travelTimeID;
    }

    public void setTravelTimeID(Long travelTimeID) {
        this.travelTimeID = travelTimeID;
    }

}
