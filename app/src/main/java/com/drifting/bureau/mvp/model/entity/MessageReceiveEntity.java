package com.drifting.bureau.mvp.model.entity;

public class MessageReceiveEntity {

    private Integer Id;
    private Integer user_id;
    private String user_name;
    private Integer type_id;
    private Integer is_drifting;
    private Integer is_throw;
    private Integer created_at_int;
    private Integer updated_at_int;
    private Integer explore_id;
    private Integer drift_rest;
    private Integer drift_end;
    private String nebula_name;

    public Integer getDrift_end() {
        return drift_end;
    }

    public void setDrift_end(Integer drift_end) {
        this.drift_end = drift_end;
    }

    public String getNebula_name() {
        return nebula_name;
    }

    public void setNebula_name(String nebula_name) {
        this.nebula_name = nebula_name;
    }

    public Integer getDrift_rest() {
        return drift_rest;
    }

    public void setDrift_rest(Integer drift_rest) {
        this.drift_rest = drift_rest;
    }

    public Integer getExplore_id() {
        return explore_id;
    }

    public void setExplore_id(Integer explore_id) {
        this.explore_id = explore_id;
    }

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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public Integer getIs_drifting() {
        return is_drifting;
    }

    public void setIs_drifting(Integer is_drifting) {
        this.is_drifting = is_drifting;
    }

    public Integer getIs_throw() {
        return is_throw;
    }

    public void setIs_throw(Integer is_throw) {
        this.is_throw = is_throw;
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
