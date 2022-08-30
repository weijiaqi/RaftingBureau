package com.drifting.bureau.mvp.model.entity;


public class TopicTagsEntity {

        private Integer tag_id;
        private String tag;
        private String tag_name;
        private Integer created_at_int;

        public Integer getTag_id() {
            return tag_id;
        }

        public void setTag_id(Integer tag_id) {
            this.tag_id = tag_id;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }

        public Integer getCreated_at_int() {
            return created_at_int;
        }

        public void setCreated_at_int(Integer created_at_int) {
            this.created_at_int = created_at_int;
        }

}
