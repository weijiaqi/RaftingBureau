package com.drifting.bureau.mvp.model.entity;

public class MakingRecordEntity {
  private String price;

    public MakingRecordEntity(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
