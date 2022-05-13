package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;

public class RaftingInforDialog  extends BaseDialog implements View.OnClickListener {
   private TextView mTvThrowSpce;

    public static final int SELECT_FINISH = 0x01;

    public RaftingInforDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_rafting_infor;
    }

    @Override
    protected float getDialogWith() {
        return 0.8f;
    }

    @Override
    protected void initView() {
        super.initView();
        mTvThrowSpce= findViewById(R.id.tv_throw_space);
        mTvThrowSpce.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_throw_space:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_FINISH);
                }
                break;
        }

    }
}
