package com.drifting.bureau.mvp.model.entity;

public class InfoForShareEntity {
        private String planet_name;
        private Integer schedule;
        private String share_url;
        private Integer user_id;
        private String user_level_name;
        private String user_name;

        public String getPlanet_name() {
            return planet_name;
        }

        public void setPlanet_name(String planet_name) {
            this.planet_name = planet_name;
        }

        public Integer getSchedule() {
            return schedule;
        }

        public void setSchedule(Integer schedule) {
            this.schedule = schedule;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public String getUser_level_name() {
            return user_level_name;
        }

        public void setUser_level_name(String user_level_name) {
            this.user_level_name = user_level_name;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

}
