package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.data.entity.LoginLocallyEntity;
import com.drifting.bureau.mvp.ui.activity.user.ClaimPlanetActivity;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class LoginListPlanetHolder extends BaseRecyclerHolder {
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_claim_planet)
    TextView mTvClaimPlanet;
    private Context context;

    public LoginListPlanetHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(List<LoginLocallyEntity> mDatas, int position) {
        mTvContent.setText(mDatas.get(position).getContent());
        mTvClaimPlanet.setOnClickListener(v -> {
            ClaimPlanetActivity.start(context, true);
        });
    }
}
