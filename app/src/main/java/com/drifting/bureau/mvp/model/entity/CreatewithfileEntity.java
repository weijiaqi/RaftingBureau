package com.drifting.bureau.mvp.model.entity;

public class CreatewithfileEntity {

    private String image_url;
    private String media_url;
    private Integer message_id;
    private Integer comment_id;
    private Integer need_pay;


    public Integer getNeed_pay() {
        return need_pay;
    }

    public void setNeed_pay(Integer need_pay) {
        this.need_pay = need_pay;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public Integer getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Integer message_id) {
        this.message_id = message_id;
    }

    public Integer getComment_id() {
        return comment_id;
    }

    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }

}
