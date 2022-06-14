package com.drifting.bureau.view.radar;

/**
 * Created by a1anwang.com on 2019/11/19.
 */
public class RadarItem {
    String labelName;//标签名称
    int value;//数值
    float progress;//0-1;

    public RadarItem(String labelName, int value, float progress) {
        this.labelName = labelName;
        this.value = value;
        this.progress = progress;
    }
}
