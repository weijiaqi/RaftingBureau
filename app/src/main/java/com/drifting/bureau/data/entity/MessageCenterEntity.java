package com.drifting.bureau.data.entity;

public class MessageCenterEntity {
    private String title;
    private boolean isUnread;
   private int type;
    public MessageCenterEntity(int type,String title,boolean isUnread) {
        this.type=type;
        this.title = title;
        this.isUnread = isUnread;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isUnread() {
        return isUnread;
    }

    public void setUnread(boolean unread) {
        isUnread = unread;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
