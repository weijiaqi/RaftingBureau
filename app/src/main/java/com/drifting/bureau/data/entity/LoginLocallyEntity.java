package com.drifting.bureau.data.entity;

public class LoginLocallyEntity {
    private String content;
    private int type;
    private boolean status;

    public LoginLocallyEntity(String content, int type,boolean status) {
        this.content = content;
        this.type = type;
        this.status=status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
