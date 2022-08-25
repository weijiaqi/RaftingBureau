package com.drifting.bureau.mvp.model.entity;

public class PlanetLocationEntity {


    private int show;
    private int assess_after;
    private int assess_status;
    private int answer;

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public int getAssess_after() {
        return assess_after;
    }

    public void setAssess_after(int assess_after) {
        this.assess_after = assess_after;
    }

    public int getAssess_status() {
        return assess_status;
    }

    public void setAssess_status(int assess_status) {
        this.assess_status = assess_status;
    }
}
