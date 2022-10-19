package com.drifting.bureau.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class CouponsMineEntity {

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

        public static class ListBean   implements Serializable {
            private String name;
            private String code;
            private Integer coupon_type;
            private Double discount_rate;
            private String deduct_money;
            private Integer user_id;
            private Integer gained_at_int;
            private Integer expired;
            private Integer validate_at_int;
            private Integer expired_at_int;
            private String reach_money;
            private String use_range;
            private int type;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getReach_money() {
                return reach_money;
            }

            public void setReach_money(String reach_money) {
                this.reach_money = reach_money;
            }

            public String getUse_range() {
                return use_range;
            }

            public void setUse_range(String use_range) {
                this.use_range = use_range;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public Integer getCoupon_type() {
                return coupon_type;
            }

            public void setCoupon_type(Integer coupon_type) {
                this.coupon_type = coupon_type;
            }

            public Double getDiscount_rate() {
                return discount_rate;
            }

            public void setDiscount_rate(Double discount_rate) {
                this.discount_rate = discount_rate;
            }

            public String getDeduct_money() {
                return deduct_money;
            }

            public void setDeduct_money(String deduct_money) {
                this.deduct_money = deduct_money;
            }

            public Integer getUser_id() {
                return user_id;
            }

            public void setUser_id(Integer user_id) {
                this.user_id = user_id;
            }

            public Integer getGained_at_int() {
                return gained_at_int;
            }

            public void setGained_at_int(Integer gained_at_int) {
                this.gained_at_int = gained_at_int;
            }

            public Integer getExpired() {
                return expired;
            }

            public void setExpired(Integer expired) {
                this.expired = expired;
            }

            public Integer getValidate_at_int() {
                return validate_at_int;
            }

            public void setValidate_at_int(Integer validate_at_int) {
                this.validate_at_int = validate_at_int;
            }

            public Integer getExpired_at_int() {
                return expired_at_int;
            }

            public void setExpired_at_int(Integer expired_at_int) {
                this.expired_at_int = expired_at_int;
            }
        }

}
