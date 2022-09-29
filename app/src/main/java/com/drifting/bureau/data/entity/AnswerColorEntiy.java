package com.drifting.bureau.data.entity;

public class AnswerColorEntiy {
    private int pl_id;
    private String name;
    private int color;

    public AnswerColorEntiy(int pl_id, String name, int color) {
        this.pl_id = pl_id;
        this.name = name;
        this.color = color;
    }

    public int getPl_id() {
        return pl_id;
    }

    public void setPl_id(int pl_id) {
        this.pl_id = pl_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
