package com.drifting.bureau.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class CharacterTraitsHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_word)
    TextView mTvWord;

    public CharacterTraitsHolder(View itemView) {
        super(itemView);
    }

    public void setData(List<String> list, int postion) {
        TextUtil.setText(mTvWord, list.get(postion));
    }
}
