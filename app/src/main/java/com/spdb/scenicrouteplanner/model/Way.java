package com.spdb.scenicrouteplanner.model;

import com.spdb.scenicrouteplanner.lib.OSM.OSMClassLib;

public class Way {
    private long id;
    private OSMClassLib.WayType wayType;
    private boolean isScenicRoute;
    private double maxSpeed;

    public Way(long id, OSMClassLib.WayType wayType, boolean isScenicRoute, double maxSpeed) {
        this.id = id;
        this.wayType = wayType;
        this.isScenicRoute = isScenicRoute;
        this.maxSpeed = maxSpeed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OSMClassLib.WayType getWayType() {
        return wayType;
    }

    public void setWayType(OSMClassLib.WayType wayType) {
        this.wayType = wayType;
    }

    public boolean isScenicRoute() {
        return isScenicRoute;
    }

    public void setScenicRoute(boolean scenicRoute) {
        isScenicRoute = scenicRoute;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
