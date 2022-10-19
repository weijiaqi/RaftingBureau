package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.MyTreasuryEntity;
import com.drifting.bureau.mvp.ui.holder.MyTreasuryHolder;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyTreasuryAdapter  extends DefaultAdapter<MyTreasuryEntity> {

    private List<MyTreasuryEntity> selectEntities = new ArrayList<>();

    private SeletChangeListener mSeletChangeListener;


    public List<MyTreasuryEntity> getSelectEntities() {
        return selectEntities;
    }

    public MyTreasuryAdapter(List infos, SeletChangeListener seletChangeListener) {
        super(infos);
        this.mSeletChangeListener = seletChangeListener;
    }

    @NonNull
    @Override
    public BaseHolder getHolder(@NonNull View v, int viewType) {
        return new MyTreasuryHolder(v, this);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_my_treasury;
    }

    /**
     * 选中状态更改
     */
    public void onItemCheckChange(MyTreasuryEntity object) {
        if (object != null) {
            if (selectEntities.size()>0){
                selectEntities.clear();
            }
            selectEntities.add(object);
            mSeletChangeListener.onSeletChange(selectEntities.get(0));
            notifyDataSetChanged();
        }
    }

    public void setData(List data) {
        if (data != null) {
            this.mInfos = data;
            notifyDataSetChanged();
        }
    }

    /**
     * 选中监听
     */
    public interface SeletChangeListener {
        void onSeletChange(MyTreasuryEntity entity);
    }

}
