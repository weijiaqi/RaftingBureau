package com.drifting.bureau.data.entity;

import com.drifting.bureau.util.DigitUtil;

import java.io.Serializable;

public class CityEntity implements Comparable {


    private String adcode;
    private String name;

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Object o) {
        CityEntity areaModel = (CityEntity) o;
        return DigitUtil.getPinYinFirst(this.getName()).compareTo(DigitUtil.getPinYinFirst(areaModel.getName()));
    }
}
