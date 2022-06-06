package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;

/**
 * @Description: 修改昵称
 * @Author     : WeiJiaQI
 * @Time       : 2022/5/30 14:51
 */
public class ModifyNicknameDialog extends BaseDialog implements View.OnClickListener {

    public static final int SELECT_FINISH = 0x01;

    TextView mTvClick;

    public ModifyNicknameDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mTvClick=findViewById(R.id.tv_click);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvClick.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_modify_nikename;
    }

    @Override
    protected float getDialogWith() {
        return 0.7f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_click:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_FINISH);
                }
                break;
        }
    }
}
