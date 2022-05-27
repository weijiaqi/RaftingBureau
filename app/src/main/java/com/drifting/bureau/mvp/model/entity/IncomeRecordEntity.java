package com.drifting.bureau.mvp.model.entity;

public class IncomeRecordEntity {
    private String money;

    public IncomeRecordEntity(String money) {
        this.money = money;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
