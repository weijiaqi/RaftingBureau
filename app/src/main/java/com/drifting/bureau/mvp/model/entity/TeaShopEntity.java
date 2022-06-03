package com.drifting.bureau.mvp.model.entity;

public class TeaShopEntity {
    private String title;
    private String address;
    private String distance;

    private int type;

    public TeaShopEntity(String title, String address, String distance, int type) {
        this.title = title;
        this.address = address;
        this.distance = distance;
        this.type = type;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
