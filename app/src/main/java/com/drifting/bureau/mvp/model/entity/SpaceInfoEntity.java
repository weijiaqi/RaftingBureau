package com.drifting.bureau.mvp.model.entity;

public class SpaceInfoEntity {

    private Integer Id;
    private String space_name;
    private Integer user_id;
    private Integer level;
    private String level_name;
    private Integer explore_num;
    private Integer total_make;
    private Integer today_make;
    private double total_income;
    private double withdrawable;
    private Integer status;
    private Integer created_at_int;
    private Integer updated_at_int;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getSpace_name() {
        return space_name;
    }

    public void setSpace_name(String space_name) {
        this.space_name = space_name;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public Integer getExplore_num() {
        return explore_num;
    }

    public void setExplore_num(Integer explore_num) {
        this.explore_num = explore_num;
    }

    public Integer getTotal_make() {
        return total_make;
    }

    public void setTotal_make(Integer total_make) {
        this.total_make = total_make;
    }

    public Integer getToday_make() {
        return today_make;
    }

    public void setToday_make(Integer today_make) {
        this.today_make = today_make;
    }

    public double getTotal_income() {
        return total_income;
    }

    public void setTotal_income(double total_income) {
        this.total_income = total_income;
    }

    public double getWithdrawable() {
        return withdrawable;
    }

    public void setWithdrawable(double withdrawable) {
        this.withdrawable = withdrawable;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

