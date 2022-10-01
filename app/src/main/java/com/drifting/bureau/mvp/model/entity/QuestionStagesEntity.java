package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class QuestionStagesEntity {


        private Integer resource_id;
        private String ad;
        private String ad_second;
        private String heading;
        private String subheading;
        private String intro;
        private String video;
        private Integer created_at_int;
        private Integer updated_at_int;
        private String url;

        public Integer getResource_id() {
            return resource_id;
        }

        public void setResource_id(Integer resource_id) {
            this.resource_id = resource_id;
        }

        public String getAd() {
            return ad;
        }

        public void setAd(String ad) {
            this.ad = ad;
        }

        public String getAd_second() {
            return ad_second;
        }

        public void setAd_second(String ad_second) {
            this.ad_second = ad_second;
        }

        public String getHeading() {
            return heading;
        }

        public void setHeading(String heading) {
            this.heading = heading;
        }

        public String getSubheading() {
            return subheading;
        }

        public void setSubheading(String subheading) {
            this.subheading = subheading;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public Integer getCreated_at_int() {
            return created_at_int;
        }

        public void setCreated_at_int(Integer created_at_int) {
            this.created_at_int = created_at_int;
        }

        public Integer getUpdated_at_int() {
            return updated_at_int;
        }

        public void setUpdated_at_int(Integer updated_at_int) {
            this.updated_at_int = updated_at_int;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

}
