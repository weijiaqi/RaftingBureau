package com.drifting.bureau.mvp.model.entity;

public class CreatewithfileEntity {

        private String image_url;
        private String media_url;
        private Integer message_id;
        private Integer comment_id;

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
