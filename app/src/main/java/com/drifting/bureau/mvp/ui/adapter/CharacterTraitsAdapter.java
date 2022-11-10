package com.drifting.bureau.mvp.ui.adapter;

import android.view.View;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.ui.holder.CharacterTraitsHolder;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

public class CharacterTraitsAdapter  extends BaseRecyclerAdapter<String> {
    public CharacterTraitsAdapter(List<String> infos) {
        super(infos);
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        CharacterTraitsHolder characterTraitsHolder = (CharacterTraitsHolder) holder;
        characterTraitsHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_character_traits;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new CharacterTraitsHolder(view);
    }
}
