package com.drifting.bureau.data.entity;

public class WinningRecordEntity {
    private String title;

    private int type;
    public WinningRecordEntity(int type,String title) {
        this.type=type;
        this.title = title;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
