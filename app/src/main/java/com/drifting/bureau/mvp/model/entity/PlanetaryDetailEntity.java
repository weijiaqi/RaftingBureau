package com.drifting.bureau.mvp.model.entity;

public class PlanetaryDetailEntity {

        private Integer pl_id;
        private String name;
        private String image_url;
        private String desc;
        private String attr;
        private Integer people;

        public Integer getPl_id() {
            return pl_id;
        }

        public void setPl_id(Integer pl_id) {
            this.pl_id = pl_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
        }

        public Integer getPeople() {
            return people;
        }

        public void setPeople(Integer people) {
            this.people = people;
        }

}
