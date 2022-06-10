package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.jess.arms.base.BaseDialog;

/**
 * @Description: 修改昵称
 * @Author : WeiJiaQI
 * @Time : 2022/5/30 14:51
 */
public class ModifyNicknameDialog extends BaseDialog implements View.OnClickListener {

    public static final int SELECT_FINISH = 0x01;

    private TextView mTvClick;
    private EditText mEtName;

    public ModifyNicknameDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mTvClick = findViewById(R.id.tv_click);
        mEtName = findViewById(R.id.et_name);
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
        switch (view.getId()) {
            case R.id.tv_click:
                if (StringUtil.isEmpty(mEtName.getText().toString())) {
                    showMessage(mEtName.getText().toString());
                    return;
                }
                dismiss();
                if (onContentClickCallback != null) {
                    onContentClickCallback.onContetClick(mEtName.getText().toString());
                }
                break;
        }
    }

    public void showMessage(String content) {
        ToastUtil.showToast(content);
    }

    ;
}
