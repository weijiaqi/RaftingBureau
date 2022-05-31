package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class SpaceStationEntity {

        private Integer business_id;
        private String goods_name;
        private String price;
        private String sku_code;
        private String sku_name;
        private String small_image;
        private String large_image;

        public Integer getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(Integer business_id) {
            this.business_id = business_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSku_code() {
            return sku_code;
        }

        public void setSku_code(String sku_code) {
            this.sku_code = sku_code;
        }

        public String getSku_name() {
            return sku_name;
        }

        public void setSku_name(String sku_name) {
            this.sku_name = sku_name;
        }

        public String getSmall_image() {
            return small_image;
        }

        public void setSmall_image(String small_image) {
            this.small_image = small_image;
        }

        public String getLarge_image() {
            return large_image;
        }

        public void setLarge_image(String large_image) {
            this.large_image = large_image;
        }

}
