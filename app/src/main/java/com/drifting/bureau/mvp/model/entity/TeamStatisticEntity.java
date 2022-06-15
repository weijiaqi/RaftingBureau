package com.drifting.bureau.mvp.model.entity;

public class TeamStatisticEntity {


        private String auditing;
        private Integer order_num;
        private Integer people;
        private Integer ranking;
        private String share_code;
        private String total_income;
        private String withdrawable;
        private String withdrawn;

    public String getAuditing() {
        return auditing;
    }

    public void setAuditing(String auditing) {
        this.auditing = auditing;
    }

    public String getTotal_income() {
        return total_income;
    }

    public void setTotal_income(String total_income) {
        this.total_income = total_income;
    }

    public String getWithdrawable() {
        return withdrawable;
    }

    public void setWithdrawable(String withdrawable) {
        this.withdrawable = withdrawable;
    }

    public String getWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(String withdrawn) {
        this.withdrawn = withdrawn;
    }

    public Integer getOrder_num() {
            return order_num;
        }

        public void setOrder_num(Integer order_num) {
            this.order_num = order_num;
        }

        public Integer getPeople() {
            return people;
        }

        public void setPeople(Integer people) {
            this.people = people;
        }

        public Integer getRanking() {
            return ranking;
        }

        public void setRanking(Integer ranking) {
            this.ranking = ranking;
        }

        public String getShare_code() {
            return share_code;
        }

        public void setShare_code(String share_code) {
            this.share_code = share_code;
        }




}
