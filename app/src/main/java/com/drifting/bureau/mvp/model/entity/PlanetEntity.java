package com.drifting.bureau.mvp.model.entity;

public class PlanetEntity {
    private String title;
    private int drawable;

    public PlanetEntity(String title, int drawable) {
        this.title = title;
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
