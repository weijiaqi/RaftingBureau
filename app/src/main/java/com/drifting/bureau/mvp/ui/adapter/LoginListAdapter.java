package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.data.entity.LoginLocallyEntity;
import com.drifting.bureau.mvp.ui.holder.LoginListHolder;
import com.drifting.bureau.mvp.ui.holder.LoginListPlanetHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class LoginListAdapter extends BaseRecyclerAdapter<LoginLocallyEntity> {

    private final int ITEM_TYPE_LEFT = 0;
    private final int ITEM_TYPE_RIGHT = 1;
    private final int ITEM_TYPE_LEFT_PLANET = 2;

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        if (holder instanceof LoginListHolder){
            LoginListHolder loginListHolder = (LoginListHolder) holder;
            loginListHolder.setData(mDatas, position);
        }else if (holder instanceof  LoginListPlanetHolder){
            LoginListPlanetHolder loginListPlanetHolder = (LoginListPlanetHolder) holder;
            loginListPlanetHolder.setData(mDatas, position);
        }
    }

    @Override
    protected int getLayoutId(int viewType) {
        if (viewType == ITEM_TYPE_LEFT) {
            return R.layout.item_login_left;
        } else if (viewType == ITEM_TYPE_RIGHT) {
            return R.layout.item_login_right;
        }else {
            return R.layout.item_login_left_planet;
        }
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        if (viewType==ITEM_TYPE_LEFT || viewType==ITEM_TYPE_RIGHT){
            return new LoginListHolder(view);
        }else {
            return new LoginListPlanetHolder(view);
        }
    }


    @Override
    public int getItemViewType(int position) {
        int showType = mDatas.get(position).getType();
        if (showType== 1) {
            return ITEM_TYPE_LEFT;
        } else if (showType == 2) {
            return ITEM_TYPE_RIGHT;
        } else {
            return ITEM_TYPE_LEFT_PLANET;
        }
    }

    public void addData(LoginLocallyEntity comentEntity) {
        if (comentEntity != null) {
            mDatas.add(mDatas.size(), comentEntity);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加数据(局部刷新，局部刷新时必须重写getItemId方法，同时setHasStableIds(true))
     *
     * @param datas
     */
    public void addData(List<LoginLocallyEntity> datas) {
        if (datas == null)
            return;
        int size = mDatas.size();
        this.mDatas.addAll(datas);
        notifyItemRangeChanged(size, datas.size());
    }
}
