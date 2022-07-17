package com.drifting.bureau.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.SpaceAboutEntity;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class HowToPlayHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    public HowToPlayHolder(View itemView) {
        super(itemView);
    }

    public void setData(@NonNull List<SpaceAboutEntity> listBeanList, int position) {

        mTvTitle.setText(listBeanList.get(position).getTitle());
        mTvContent.setText( listBeanList.get(position).getContent());

    }
}
