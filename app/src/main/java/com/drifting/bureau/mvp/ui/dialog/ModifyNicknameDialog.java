package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;

/**
 * @Description: 修改昵称
 * @Author     : WeiJiaQI
 * @Time       : 2022/5/30 14:51
 */
public class ModifyNicknameDialog extends BaseDialog {
    public ModifyNicknameDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_modify_nikename;
    }

    @Override
    protected float getDialogWith() {
        return 0.7f;
    }
}
