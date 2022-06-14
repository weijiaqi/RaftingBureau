package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.MyTreasuryEntity;
import com.drifting.bureau.mvp.ui.adapter.MyTreasuryAdapter;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.callback.BaseDataCallBack;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.base.BaseEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 卫佳琪1
 * @description 我的库藏
 * @time 9:36 9:36
 */

public class MyTreasuryDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    private RecyclerView mRcyMyTrea;
    private ImageView mIvPicture;
    private TextView mTvTitle, mTvNum, mTvUse, mTvGiveAway, mTvContent;

    private int object_id, count;
    private DonationDialog donationDialog;
    private SpaceStationUpgradeDialog spaceStationUpgradeDialog;
    private MySpaceStationDialog mySpaceStationDialog;
    private MyTreasuryAdapter myTreasuryAdapter;
    private List<MyTreasuryEntity> list;

    public MyTreasuryDialog(@NonNull Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_my_treasury;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mRcyMyTrea = findViewById(R.id.rcy_my_trea);
        mIvPicture = findViewById(R.id.iv_picture);
        mTvTitle = findViewById(R.id.tv_title);
        mTvNum = findViewById(R.id.tv_num);
        mTvUse = findViewById(R.id.tv_use);
        mTvGiveAway = findViewById(R.id.tv_give_away);
        mTvContent = findViewById(R.id.tv_content);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mRcyMyTrea.setLayoutManager(new GridLayoutManager(context, 4));
        myTreasuryAdapter = new MyTreasuryAdapter(new ArrayList<>(), entity -> {
            object_id = entity.getObject_id();
            GlideUtil.create().loadNormalPic(context, entity.getImage_url(), mIvPicture);
            mTvTitle.setText(entity.getName());
            count = entity.getCount();
            mTvNum.setText("数量  " + count);
            mTvContent.setText(entity.getDesc());
        });
        mRcyMyTrea.setAdapter(myTreasuryAdapter);
        getData();
        mTvGiveAway.setOnClickListener(this);
        mTvUse.setOnClickListener(this);
    }

    public void getData() {
        RequestUtil.create().storagemine(entity -> {
            if (entity != null && entity.getCode() == 200) {
                list = entity.getData();
                myTreasuryAdapter.setData(list);
                myTreasuryAdapter.onItemCheckChange(list.get(0));
            }
        });
    }


    @Override
    protected float getDialogWith() {
        return 0.9f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_use: //使用
                spaceStationUpgradeDialog = new SpaceStationUpgradeDialog(context, count, object_id);
                spaceStationUpgradeDialog.show();
                spaceStationUpgradeDialog.setOnClickCallback(type -> {
                    if (type == SpaceStationUpgradeDialog.SELECT_FINISH) {
                        spaceStationUpgradeDialog.dismiss();
                        mySpaceStationDialog = new MySpaceStationDialog(context);
                        mySpaceStationDialog.show();
                        getData();
                    }
                });

                break;
            case R.id.tv_give_away: //转赠
                donationDialog = new DonationDialog(context);
                donationDialog.show();
                donationDialog.setOnContentClickCallback(content -> {
                    RequestUtil.create().mysteryboxtransfer(object_id, content, entity -> {
                        if (entity != null) {
                            ToastUtil.showToast(entity.getMsg());
                            if (entity.getCode() == 200) {
                                donationDialog.dismiss();
                                getData();
                            }
                        }
                    });
                });
                break;
        }
    }
}
