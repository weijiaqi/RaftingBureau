package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;

/**
 * @Description: 开启特权
 * @Author : WeiJiaQI
 * @Time : 2022/9/19 15:24
 */
public class EnablePrivilegesDialog extends BaseDialog implements View.OnClickListener {


    public static final int OPEN_PRIVILEGE = 0x01;

    private ImageView mIvOpenPrivilege;

    public EnablePrivilegesDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        super.initView();
        mIvOpenPrivilege = findViewById(R.id.iv_open_privilege);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mIvOpenPrivilege.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_enable_privileges;
    }

    @Override
    protected float getDialogWith() {
        return 0.8f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_open_privilege:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(OPEN_PRIVILEGE);
                }
                break;
        }
    }
}
