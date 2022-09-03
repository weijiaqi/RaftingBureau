package com.drifting.bureau.data.entity;

public class LatLngEntity {
    private String city;
    private double latitude;
    private double longitude;

    public LatLngEntity(String city,double latitude, double longitude) {
        this.city=city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
