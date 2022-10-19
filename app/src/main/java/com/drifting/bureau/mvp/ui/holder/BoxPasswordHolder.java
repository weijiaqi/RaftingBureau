package com.drifting.bureau.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class BoxPasswordHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_pay_key)
    TextView mTvPayKey;

    public BoxPasswordHolder(View itemView) {
        super(itemView);
    }

    public void setData(@NonNull List<String> listBeanList, int position) {
        TextUtil.setText(mTvPayKey, listBeanList.get(position));
    }
}
