package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;


/**
 * @author 卫佳琪1
 * @description 覆盖弹框
 * @time 16:45 16:45
 */

public class CoverDialog extends BaseDialog implements View.OnClickListener {

    private TextView mTvCofim, mTvCancel;
    private float mDialogWith = 0.8f;
    public static final int SELECT_FINISH = 0x01;
    public static final int SELECT_CANCEL = 0x02;

    public CoverDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_cover;
    }

    @Override
    protected float getDialogWith() {
        return mDialogWith;
    }

    @Override
    protected void initView() {
        super.initView();
        mTvCofim = findViewById(R.id.tv_cofim);
        mTvCancel = findViewById(R.id.tv_cancel);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvCancel.setOnClickListener(this);
        mTvCofim.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_CANCEL);
                }
                break;
            case R.id.tv_cofim:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_FINISH);
                }
                break;
        }
    }


}
