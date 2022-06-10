package com.drifting.bureau.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SkuListEntity {


    private List<GoodsSkuBean> goods_sku;
    private String totalAmount;

    public List<GoodsSkuBean> getGoods_sku() {
        return goods_sku;
    }

    public void setGoods_sku(List<GoodsSkuBean> goods_sku) {
        this.goods_sku = goods_sku;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public static class GoodsSkuBean {
        @SerializedName("Id")
        private Integer Id;
        private String sku_code;
        private String name;
        private Integer goods_id;
        private Integer business_id;
        private Integer sales_volume;
        private Double price;
        private String small_image;
        private String large_image;
        private Integer created_at_int;
        private Integer updated_at_int;
        private String desc;


        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Integer getId() {
            return Id;
        }

        public void setId(Integer id) {
            Id = id;
        }

        public String getSku_code() {
            return sku_code;
        }

        public void setSku_code(String sku_code) {
            this.sku_code = sku_code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(Integer goods_id) {
            this.goods_id = goods_id;
        }

        public Integer getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(Integer business_id) {
            this.business_id = business_id;
        }

        public Integer getSales_volume() {
            return sales_volume;
        }

        public void setSales_volume(Integer sales_volume) {
            this.sales_volume = sales_volume;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
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
    }

}
