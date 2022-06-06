package com.drifting.bureau.mvp.model.entity;

public class OrderRecordEntity {
    private String title;
    private int type;
   private String pricre;

    public String getPricre() {
        return pricre;
    }

    public void setPricre(String pricre) {
        this.pricre = pricre;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public OrderRecordEntity(String title, int type,String pricre) {
        this.title = title;
        this.type = type;
        this.pricre=pricre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
