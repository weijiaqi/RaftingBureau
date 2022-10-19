package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.OpenBoxEntity;
import com.drifting.bureau.util.GlideUtil;
import com.jess.arms.base.BaseDialog;

/**
 * @Description: 福利券
 * @Author : WeiJiaQI
 * @Time : 2022/10/12 11:11
 */
public class WelfareVoucherDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    private ImageView mIvPic, mIvClose, mIvRightNow;
    private OpenBoxEntity openBoxEntity;

    public WelfareVoucherDialog(@NonNull Context context, OpenBoxEntity openBoxEntity) {
        super(context);
        this.context = context;
        this.openBoxEntity = openBoxEntity;
    }


    @Override
    protected void initView() {
        super.initView();
        mIvPic = findViewById(R.id.iv_pic);
        mIvClose = findViewById(R.id.iv_close);
        mIvRightNow = findViewById(R.id.iv_right_now);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mIvClose.setOnClickListener(this);
        mIvRightNow.setOnClickListener(this);
        GlideUtil.create().loadNormalPic(context, openBoxEntity.getImage(), mIvPic);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_welfare_voucher;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
            case R.id.iv_right_now:
                dismiss();
                break;
        }
    }
}
