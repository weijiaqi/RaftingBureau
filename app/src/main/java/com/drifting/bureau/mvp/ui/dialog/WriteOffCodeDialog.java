package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;

/**
 * @Description: 我得核销码
 * @Author : WeiJiaQI
 * @Time : 2022/5/28 11:50
 */
public class WriteOffCodeDialog extends BaseDialog {
    public WriteOffCodeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_write_off_code;
    }

    @Override
    protected float getDialogWith() {
        return 0.8f;
    }
}
