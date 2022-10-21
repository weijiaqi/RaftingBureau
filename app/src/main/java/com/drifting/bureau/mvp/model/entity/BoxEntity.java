package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class BoxEntity {

        private Integer equity;
        private Integer key;
        private String lat;
        private String lng;
        private String name;
        private Integer type;
        private Integer is_kongtou;

    public Integer getIs_kongtou() {
        return is_kongtou;
    }

    public void setIs_kongtou(Integer is_kongtou) {
        this.is_kongtou = is_kongtou;
    }

    public Integer getEquity() {
            return equity;
        }

        public void setEquity(Integer equity) {
            this.equity = equity;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

}
