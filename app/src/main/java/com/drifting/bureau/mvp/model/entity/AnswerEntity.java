package com.drifting.bureau.mvp.model.entity;

public class AnswerEntity {
    private String title;
    private int type;

    public AnswerEntity(String title, int type) {
        this.title = title;
        this.type = type;
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
