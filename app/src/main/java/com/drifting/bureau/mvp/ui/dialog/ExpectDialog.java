package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;

/**
 * @Description: 敬请期待
 * @Author     : WeiJiaQI
 * @Time       : 2022/10/25 16:46
 */
public class ExpectDialog  extends BaseDialog {

    public ExpectDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_expect;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }
}
