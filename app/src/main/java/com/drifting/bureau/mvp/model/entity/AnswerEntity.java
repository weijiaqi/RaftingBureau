package com.drifting.bureau.mvp.model.entity;

public class AnswerEntity {
    private String title;

    private String answerA;
    private String answerB;
    private int type;
    private int postion;

    public AnswerEntity(String title, String answerA, String answerB, int type, int postion) {
        this.title = title;
        this.type = type;
        this.answerA = answerA;
        this.answerB = answerB;
        this.postion = postion;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerb) {
        this.answerB = answerb;
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
