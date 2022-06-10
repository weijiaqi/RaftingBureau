package com.drifting.bureau.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrizeEntity {

        @SerializedName("Id")
        private Integer Id;
        private String award_name;
        private String image_url;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getAward_name() {
            return award_name;
        }

        public void setAward_name(String award_name) {
            this.award_name = award_name;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

}
