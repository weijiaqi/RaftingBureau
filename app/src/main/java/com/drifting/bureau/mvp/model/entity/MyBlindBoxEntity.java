package com.drifting.bureau.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyBlindBoxEntity {


        private Integer limit;
        private Integer page;
        private Integer count;
        private List<ListBean> list;

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {

            private Integer Id;
            private Integer user_id;
            private String sku_code;
            private String sku_name;
            private Integer space_id;
            private Integer is_open;
            private Integer transfer;
            private Integer created_at_int;
            private String image;

            public Integer getId() {
                return Id;
            }

            public void setId(Integer id) {
                Id = id;
            }

            public Integer getUser_id() {
                return user_id;
            }

            public void setUser_id(Integer user_id) {
                this.user_id = user_id;
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

            public Integer getSpace_id() {
                return space_id;
            }

            public void setSpace_id(Integer space_id) {
                this.space_id = space_id;
            }

            public Integer getIs_open() {
                return is_open;
            }

            public void setIs_open(Integer is_open) {
                this.is_open = is_open;
            }

            public Integer getTransfer() {
                return transfer;
            }

            public void setTransfer(Integer transfer) {
                this.transfer = transfer;
            }

            public Integer getCreated_at_int() {
                return created_at_int;
            }

            public void setCreated_at_int(Integer created_at_int) {
                this.created_at_int = created_at_int;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }


