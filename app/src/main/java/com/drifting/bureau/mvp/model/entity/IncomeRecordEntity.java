package com.drifting.bureau.mvp.model.entity;

import java.util.List;

public class IncomeRecordEntity {


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
        private Integer bill_log_id;
        private String bill_type;
        private Integer created_at_int;
        private String money;
        private Integer status;
        private Integer user_id;
        private Integer b_type;

        public Integer getB_type() {
            return b_type;
        }

        public void setB_type(Integer b_type) {
            this.b_type = b_type;
        }

        public Integer getBill_log_id() {
            return bill_log_id;
        }

        public void setBill_log_id(Integer bill_log_id) {
            this.bill_log_id = bill_log_id;
        }

        public String getBill_type() {
            return bill_type;
        }

        public void setBill_type(String bill_type) {
            this.bill_type = bill_type;
        }

        public Integer getCreated_at_int() {
            return created_at_int;
        }

        public void setCreated_at_int(Integer created_at_int) {
            this.created_at_int = created_at_int;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }
    }

}
