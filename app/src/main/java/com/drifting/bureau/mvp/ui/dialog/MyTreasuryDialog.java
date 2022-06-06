package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.DonationEvent;
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

    public static final int SELECT_USE = 0x01;

    private Context context;
    private RecyclerView mRcyMyTrea;
    private ImageView mIvPicture;
    private TextView mTvTitle, mTvNum, mTvUse, mTvGiveAway, mTvContent;

    private int object_id;
    private DonationDialog donationDialog;

    private MyTreasuryAdapter myTreasuryAdapter;
    private List<MyTreasuryEntity> list;

    public MyTreasuryDialog(@NonNull Context context, List<MyTreasuryEntity> entities) {
        super(context);
        this.context = context;
        this.list = entities;
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
            mTvNum.setText("数量  " + entity.getCount());
            mTvContent.setText(entity.getDesc());
        });
        mRcyMyTrea.setAdapter(myTreasuryAdapter);
        myTreasuryAdapter.setData(list);
        myTreasuryAdapter.onItemCheckChange(list.get(0));

        mTvGiveAway.setOnClickListener(this);
        mTvUse.setOnClickListener(this);
    }


    @Override
    protected float getDialogWith() {
        return 0.9f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_use: //使用
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_USE);
                }
                break;
            case R.id.tv_give_away: //转赠
                donationDialog = new DonationDialog(context);
                donationDialog.show();
                donationDialog.setOnContentClickCallback(content -> {
                    RequestUtil.create().mysteryboxtransfer(object_id, content, entity -> {
                        if (entity != null) {
                            ToastUtil.showToast(entity.getMsg());
                            if (entity.getCode() == 200) {
                                dismiss();
                                EventBus.getDefault().post(new DonationEvent());
                            }
                        }
                    });
                });
                break;
        }
    }
}
