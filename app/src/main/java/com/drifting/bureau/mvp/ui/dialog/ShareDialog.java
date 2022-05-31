package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;

/**
 * @Description: 分享
 * @Author : WeiJiaQI
 * @Time : 2022/5/29 16:40
 */
public class ShareDialog extends BaseDialog {
    public ShareDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_share;
    }

    @Override
    protected float getDialogWith() {
        return 0.9f;
    }
}
