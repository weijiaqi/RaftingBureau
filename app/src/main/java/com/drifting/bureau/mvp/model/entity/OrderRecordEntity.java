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
        private Integer order_id;
        private Integer user_id;
        private String sn;
        private Integer status;
        private Integer explore_id;
        private String money;
        private String order_money;
        private Integer pay_time;
        private Integer created_at_int;
        private Integer timeout;
        private List<OrderSubBean> order_sub;
        private Integer platform_gift;
        private Integer write_off;
        private Integer wake_up_pay;
        private String coupon_code;
        private String coupon_name;

        private String coupon_money;

        private String use_scene;

        public String getUse_scene() {
            return use_scene;
        }

        public void setUse_scene(String use_scene) {
            this.use_scene = use_scene;
        }

        public String getOrder_money() {
            return order_money;
        }

        public void setOrder_money(String order_money) {
            this.order_money = order_money;
        }

        public String getCoupon_money() {
            return coupon_money;
        }

        public void setCoupon_money(String coupon_money) {
            this.coupon_money = coupon_money;
        }

        public Integer getWake_up_pay() {
            return wake_up_pay;
        }

        public void setWake_up_pay(Integer wake_up_pay) {
            this.wake_up_pay = wake_up_pay;
        }

        public String getCoupon_code() {
            return coupon_code;
        }

        public void setCoupon_code(String coupon_code) {
            this.coupon_code = coupon_code;
        }

        public String getCoupon_name() {
            return coupon_name;
        }

        public void setCoupon_name(String coupon_name) {
            this.coupon_name = coupon_name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

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

        public Integer getOrder_id() {
            return order_id;
        }

        public void setOrder_id(Integer order_id) {
            this.order_id = order_id;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getExplore_id() {
            return explore_id;
        }

        public void setExplore_id(Integer explore_id) {
            this.explore_id = explore_id;
        }


        public Integer getPay_time() {
            return pay_time;
        }

        public void setPay_time(Integer pay_time) {
            this.pay_time = pay_time;
        }

        public Integer getCreated_at_int() {
            return created_at_int;
        }

        public void setCreated_at_int(Integer created_at_int) {
            this.created_at_int = created_at_int;
        }

        public Integer getTimeout() {
            return timeout;
        }

        public void setTimeout(Integer timeout) {
            this.timeout = timeout;
        }

        public List<OrderSubBean> getOrder_sub() {
            return order_sub;
        }

        public void setOrder_sub(List<OrderSubBean> order_sub) {
            this.order_sub = order_sub;
        }

        public static class OrderSubBean {
            private Integer business_id;
            private String sku_code;
            private String money;
            private Integer order_sub_id;
            private String sku_name;
            private Integer goods_num;
            private String small_image;
            private Integer status;
            private Integer is_drifting;
            private String desc;
            private Integer write_off;
            private Integer pay_time;
            private Integer created_at_int;
            private Integer platform_gift;
            private Integer explore_id;

            public Integer getExplore_id() {
                return explore_id;
            }

            public void setExplore_id(Integer explore_id) {
                this.explore_id = explore_id;
            }

            public Integer getBusiness_id() {
                return business_id;
            }

            public void setBusiness_id(Integer business_id) {
                this.business_id = business_id;
            }

            public String getSku_code() {
                return sku_code;
            }

            public void setSku_code(String sku_code) {
                this.sku_code = sku_code;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public Integer getOrder_sub_id() {
                return order_sub_id;
            }

            public void setOrder_sub_id(Integer order_sub_id) {
                this.order_sub_id = order_sub_id;
            }

            public String getSku_name() {
                return sku_name;
            }

            public void setSku_name(String sku_name) {
                this.sku_name = sku_name;
            }

            public Integer getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(Integer goods_num) {
                this.goods_num = goods_num;
            }

            public String getSmall_image() {
                return small_image;
            }

            public void setSmall_image(String small_image) {
                this.small_image = small_image;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public Integer getIs_drifting() {
                return is_drifting;
            }

            public void setIs_drifting(Integer is_drifting) {
                this.is_drifting = is_drifting;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public Integer getWrite_off() {
                return write_off;
            }

            public void setWrite_off(Integer write_off) {
                this.write_off = write_off;
            }

            public Integer getPay_time() {
                return pay_time;
            }

            public void setPay_time(Integer pay_time) {
                this.pay_time = pay_time;
            }

            public Integer getCreated_at_int() {
                return created_at_int;
            }

            public void setCreated_at_int(Integer created_at_int) {
                this.created_at_int = created_at_int;
            }

            public Integer getPlatform_gift() {
                return platform_gift;
            }

            public void setPlatform_gift(Integer platform_gift) {
                this.platform_gift = platform_gift;
            }
        }
    }

}
