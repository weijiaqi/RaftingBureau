package com.drifting.bureau.mvp.model.entity;

public class RaftingOrderEntity {
    private String price;

    public RaftingOrderEntity(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
