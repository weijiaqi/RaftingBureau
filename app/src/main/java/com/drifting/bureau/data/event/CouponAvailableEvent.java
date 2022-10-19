package com.drifting.bureau.data.event;

import com.drifting.bureau.mvp.model.entity.CouponsMineEntity;

public class CouponAvailableEvent {

    private CouponsMineEntity.ListBean listBean;

    public CouponsMineEntity.ListBean getListBean() {
        return listBean;
    }

    public void setListBean(CouponsMineEntity.ListBean listBean) {
        this.listBean = listBean;
    }
}
