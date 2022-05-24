package com.drifting.bureau.mvp.model.entity;

public class BarrageEntity {
    private String text;

    public BarrageEntity(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
