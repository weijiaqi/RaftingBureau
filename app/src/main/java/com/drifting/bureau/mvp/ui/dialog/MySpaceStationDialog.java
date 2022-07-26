package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.MySpaceStationEntity;
import com.drifting.bureau.mvp.ui.adapter.MySpaceStationAdapter;
import com.drifting.bureau.util.SpannableUtil;
import com.jess.arms.base.BaseDialog;

import java.util.ArrayList;

/**
 * @Description: 我的空间站升级
 * @Author : WeiJiaQI
 * @Time : 2022/5/26 12:05
 */
public class MySpaceStationDialog extends BaseDialog implements View.OnClickListener {

    private TextView mTvCofim, mTvLevelName, mTvTotalNum, mTvDistance;
    private ProgressBar mPrUpgrade;
    private RecyclerView mRcyInterests;
    private Context context;
    private MySpaceStationAdapter mySpaceStationAdapter;

    private MySpaceStationEntity mySpaceStationEntity;

    public static final int SELECT_FINISH = 0x01;

    public MySpaceStationDialog(@NonNull Context context, MySpaceStationEntity mySpaceStationEntity) {
        super(context);
        this.context = context;
        this.mySpaceStationEntity = mySpaceStationEntity;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mTvCofim = findViewById(R.id.tv_cofim);
        mTvLevelName = findViewById(R.id.tv_levle_name);
        mTvTotalNum = findViewById(R.id.tv_total_num);
        mPrUpgrade = findViewById(R.id.pr_upgrade);
        mRcyInterests = findViewById(R.id.rcy_interests);
        mTvDistance = findViewById(R.id.tv_distance_level);
    }


    @Override
    protected void initEvents() {
        super.initEvents();
        mRcyInterests.setLayoutManager(new GridLayoutManager(context, 4));
        mySpaceStationAdapter = new MySpaceStationAdapter(new ArrayList<>());
        mRcyInterests.setAdapter(mySpaceStationAdapter);
        mTvCofim.setOnClickListener(this);
        if (mySpaceStationEntity.getScore() == mySpaceStationEntity.getTop_score()) {
            mTvDistance.setText("已升至满级");
        } else {
            mTvDistance.setText("距离下一等级");
        }
        mPrUpgrade.setMax(mySpaceStationEntity.getTop_score());
        mPrUpgrade.setProgress(mySpaceStationEntity.getScore());
        mTvTotalNum.setText(mySpaceStationEntity.getScore() + "/" + mySpaceStationEntity.getTop_score());
        SpannableStringBuilder passer = SpannableUtil.getBuilder(context, "当前空间站等级： ").append(mySpaceStationEntity.getSpace_level_name()).setBold().build();
        mTvLevelName.setText(passer);
        mySpaceStationAdapter.setData(mySpaceStationEntity.getOwn_rights());
    }


    @Override
    protected int getContentView() {
        return R.layout.dialog_my_space_station;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cofim:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_FINISH);
                }
                break;
        }
    }
}
