package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.MyTreasuryEntity;
import com.drifting.bureau.mvp.ui.adapter.MyTreasuryAdapter;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseHolder;

import butterknife.BindView;

public class MyTreasuryHolder extends BaseHolder<MyTreasuryEntity> {
    @BindView(R.id.rl_bg)
    RelativeLayout mRlBg;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_quantity)
    TextView mTvQuantity;

    private Context context;

    private MyTreasuryAdapter mAdapter;

    public MyTreasuryHolder(View itemView, MyTreasuryAdapter myTreasuryAdapter) {
        super(itemView);
        context = itemView.getContext();
        this.mAdapter = myTreasuryAdapter;
    }

    @Override
    public void setData(@NonNull MyTreasuryEntity data, int position) {
        TextUtil.setText(mTvQuantity, data.getCount() + "");
        GlideUtil.create().loadLongImage(context,data.getImage_url(),mIvPic);
        if (mAdapter.getSelectEntities() != null) {
            if (mAdapter.getSelectEntities().contains(data)) {
                mRlBg.setBackgroundResource(R.drawable.my_treasury_selected);
            } else {
                mRlBg.setBackgroundResource(R.drawable.my_treasury_unselected);
            }
        }

        mRlBg.setOnClickListener(v -> {  //选择
            mAdapter.onItemCheckChange(data);
        });
    }
}
