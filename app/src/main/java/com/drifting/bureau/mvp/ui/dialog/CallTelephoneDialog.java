package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;

public class CallTelephoneDialog extends BaseDialog implements View.OnClickListener {

    private TextView mTvTelePhone, mTvCall, mTvCancel;


    private String tel;
    private Context context;

    public CallTelephoneDialog(@NonNull Context context, String tel) {
        super(context);
        this.context = context;
        this.tel=tel;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_call_telephone;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mTvTelePhone = findViewById(R.id.tv_telephone);
        mTvCall = findViewById(R.id.tv_call);
        mTvCancel = findViewById(R.id.tv_cancel);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvTelePhone.setText(tel);
        mTvCall.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
    }

    @Override
    protected void getWindows(Window window) {
        super.getWindows(window);
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.EnterDialog);
        }
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_call:
                dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
                context.startActivity(intent);
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }
}
