package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;

/**
 * @description 公共弹框
 */
public class PublicDialog extends BaseDialog {

    public PublicDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_public;
    }

    @Override
    protected float getDialogWith() {
        return 0.7f;
    }
}
