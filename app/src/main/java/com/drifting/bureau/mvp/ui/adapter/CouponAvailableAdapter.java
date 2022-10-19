package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.BlindBoxRecordEntity;
import com.drifting.bureau.mvp.model.entity.CouponsMineEntity;
import com.drifting.bureau.mvp.model.entity.MyTreasuryEntity;
import com.drifting.bureau.mvp.ui.holder.BlindBoxHolder;
import com.drifting.bureau.mvp.ui.holder.CouponAvailableHolder;
import com.drifting.bureau.mvp.ui.holder.MyTreasuryHolder;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.ArrayList;
import java.util.List;

public class CouponAvailableAdapter  extends DefaultAdapter<CouponsMineEntity.ListBean> {


    private List<CouponsMineEntity.ListBean> selectEntities = new ArrayList<>();

    private SeletChangeListener mSeletChangeListener;


    public List<CouponsMineEntity.ListBean> getSelectEntities() {
        return selectEntities;
    }

    public CouponAvailableAdapter(List infos, SeletChangeListener seletChangeListener) {
        super(infos);
        this.mSeletChangeListener = seletChangeListener;
    }

    @NonNull
    @Override
    public BaseHolder getHolder(@NonNull View v, int viewType) {
        return new CouponAvailableHolder(v, this);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_coupon_available;
    }

    /**
     * 选中状态更改
     */
    public void onItemCheckChange(CouponsMineEntity.ListBean object) {
        if (object != null) {
            if (selectEntities.size()>0){
                selectEntities.clear();
            }
            selectEntities.add(object);
            mSeletChangeListener.onSeletChange(selectEntities.get(0));
            notifyDataSetChanged();
        }
    }

    public void setData(List data,CouponsMineEntity.ListBean listBean) {
        if (data != null) {
            this.mInfos = data;
            if (listBean!=null){
                if (selectEntities.size()>0){
                    selectEntities.clear();
                }
                selectEntities.add(listBean);
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 选中监听
     */
    public interface SeletChangeListener {
        void onSeletChange(CouponsMineEntity.ListBean entity);
    }
}
