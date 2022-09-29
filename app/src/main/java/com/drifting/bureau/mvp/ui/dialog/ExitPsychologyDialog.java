package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;

/**
 * @author 卫佳琪1
 * @description 退出心里实验室
 * @time 16:31 16:31
 */

public class ExitPsychologyDialog extends BaseDialog implements View.OnClickListener {

    public static final int SELECT_FINISH = 0x01;
    private TextView mTvCofim, mTvCancel;

    public ExitPsychologyDialog(@NonNull Context context) {
        super(context);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_cofim:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_FINISH);
                }
                break;
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_exit_psychology;
    }

    @Override
    protected float getDialogWith() {
        return 0.7f;
    }
}
