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
 * 转赠
 */
public class DonationDialog extends BaseDialog implements View.OnClickListener {

    private TextView mTvTitle, mTvClick;
    private EditText mEtPhone;

    public DonationDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void initDatas() {
        super.initDatas();
        mTvTitle = findViewById(R.id.tv_title);
        mEtPhone = findViewById(R.id.et_phone);
        mTvClick = findViewById(R.id.tv_click);
    }


    @Override
    protected void initEvents() {
        super.initEvents();
        mTvClick.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_donation;
    }


    @Override
    protected float getDialogWith() {
        return 0.7f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_click:
                dismiss();
                if (!StringUtil.isEmpty(StringUtil.checkMobile(mEtPhone.getText().toString()))) {
                    showMessage(StringUtil.checkMobile(mEtPhone.getText().toString()));
                    return;
                }
                if (onContentClickCallback != null) {
                    onContentClickCallback.onContetClick(mEtPhone.getText().toString());
                }
                break;
        }
    }

    public void showMessage(String content) {
        ToastUtil.showToast(content);
    }
}
