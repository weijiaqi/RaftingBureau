package com.drifting.bureau.mvp.model.entity;

public class DeliveryDetailsEntity {
    private int type;

    public DeliveryDetailsEntity(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
