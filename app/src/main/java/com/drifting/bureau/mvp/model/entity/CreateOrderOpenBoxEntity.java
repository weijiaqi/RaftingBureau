package com.drifting.bureau.mvp.model.entity;

public class CreateOrderOpenBoxEntity {

        private String sn;
        private String total_amount;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }
}
