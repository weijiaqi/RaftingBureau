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
 * @description 空间站兑换码
 * @author 卫佳琪1
 * @time 18:05 18:05
 */

public class ExchangeCodeDialog extends BaseDialog implements View.OnClickListener {

    private EditText mEtCode;
    private TextView mTvClick;

    public ExchangeCodeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mEtCode = findViewById(R.id.et_code);
        mTvClick = findViewById(R.id.tv_click);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvClick.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_exchange_code;
    }

    @Override
    protected float getDialogWith() {
        return 0.7f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_click:
                if (StringUtil.isEmpty(mEtCode.getText().toString())) {
                    showMessage("请输入兑换码");
                    return;
                }
                dismiss();
                if (onContentClickCallback != null) {
                    onContentClickCallback.onContetClick(mEtCode.getText().toString());
                }
                break;
        }
    }

    public void showMessage(String content) {
        ToastUtil.showToast(content);
    }

}
