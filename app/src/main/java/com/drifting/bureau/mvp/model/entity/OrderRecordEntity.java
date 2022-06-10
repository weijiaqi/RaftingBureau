package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class OrderRecordEntity {


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
            private Integer user_id;
            private Integer order_id;
            private Integer order_sub_id;
            private Integer goods_id;
            private Integer business_id;
            private Integer sku_id;
            private String sku_name;
            private Integer explore_id;
            private Integer is_drifting;
            private Double money;
            private Integer sku_num;
            private Integer pay_time_int;
            private Integer created_at_int;
            private Integer status;
            private Integer timeout;
            private String desc;
            private Integer platform_gift;
            private Integer write_off;

            public Integer getPlatform_gift() {
                return platform_gift;
            }

            public void setPlatform_gift(Integer platform_gift) {
                this.platform_gift = platform_gift;
            }

            public Integer getWrite_off() {
                return write_off;
            }

            public void setWrite_off(Integer write_off) {
                this.write_off = write_off;
            }

            public Integer getUser_id() {
                return user_id;
            }

            public void setUser_id(Integer user_id) {
                this.user_id = user_id;
            }

            public Integer getOrder_id() {
                return order_id;
            }

            public void setOrder_id(Integer order_id) {
                this.order_id = order_id;
            }

            public Integer getOrder_sub_id() {
                return order_sub_id;
            }

            public void setOrder_sub_id(Integer order_sub_id) {
                this.order_sub_id = order_sub_id;
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

            public Integer getSku_id() {
                return sku_id;
            }

            public void setSku_id(Integer sku_id) {
                this.sku_id = sku_id;
            }

            public String getSku_name() {
                return sku_name;
            }

            public void setSku_name(String sku_name) {
                this.sku_name = sku_name;
            }

            public Integer getExplore_id() {
                return explore_id;
            }

            public void setExplore_id(Integer explore_id) {
                this.explore_id = explore_id;
            }

            public Integer getIs_drifting() {
                return is_drifting;
            }

            public void setIs_drifting(Integer is_drifting) {
                this.is_drifting = is_drifting;
            }

            public Double getMoney() {
                return money;
            }

            public void setMoney(Double money) {
                this.money = money;
            }

            public Integer getSku_num() {
                return sku_num;
            }

            public void setSku_num(Integer sku_num) {
                this.sku_num = sku_num;
            }

            public Integer getPay_time_int() {
                return pay_time_int;
            }

            public void setPay_time_int(Integer pay_time_int) {
                this.pay_time_int = pay_time_int;
            }

            public Integer getCreated_at_int() {
                return created_at_int;
            }

            public void setCreated_at_int(Integer created_at_int) {
                this.created_at_int = created_at_int;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public Integer getTimeout() {
                return timeout;
            }

            public void setTimeout(Integer timeout) {
                this.timeout = timeout;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

}
