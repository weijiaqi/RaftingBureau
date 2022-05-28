package com.drifting.bureau.mvp.model.entity;

public class AoubtMeEntity {
    private String title;
    private String content;


    public AoubtMeEntity(String title,String content) {
        this.title = title;
        this.content=content;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
