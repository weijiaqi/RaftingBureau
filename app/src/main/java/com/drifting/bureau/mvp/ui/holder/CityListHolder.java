package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.data.entity.CityEntity;
import com.drifting.bureau.util.DigitUtil;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class CityListHolder extends BaseRecyclerHolder {
    @BindView(R.id.item_city_name)
    TextView name;
    @BindView(R.id.item_city_alpha)
    TextView alpha;
    @BindView(R.id.rl_item)
    RelativeLayout rl_item;

    private Context mContext;

    public CityListHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
    }


    public void setData(@NonNull List<CityEntity> data, int position) {
        TextUtil.setText(name, data.get(position).getName());
        String currentStr = DigitUtil.getPinYinFirst(data.get(position).getName());
        String previewStr = (position - 1) >= 0 ? DigitUtil.getPinYinFirst(data.get(position - 1)
                .getName()) : " ";
        if (!previewStr.equals(currentStr)) {
            alpha.setVisibility(View.VISIBLE);
            alpha.setText(currentStr);
        } else {
            alpha.setVisibility(View.GONE);
        }
    }
}
