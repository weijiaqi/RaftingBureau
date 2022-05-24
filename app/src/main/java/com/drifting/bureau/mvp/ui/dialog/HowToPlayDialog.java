package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;

/**
 * @author 卫佳琪1
 * @description 玩法说明
 * @time 11:51 11:51
 */

public class HowToPlayDialog extends BaseDialog {

    public HowToPlayDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_how_to_play;
    }

    @Override
    protected float getDialogWith() {
        return 0.8f;
    }
}
