package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;

public class RecordingDialog extends BaseDialog  implements View.OnClickListener {

    private ImageView mIvNext;

    public RecordingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        super.initView();
        mIvNext= findViewById(R.id.iv_next);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mIvNext.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_recording;
    }

    @Override
    protected float getDialogWith() {
        return 1;
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_next:
                dismiss();
                break;
        }
    }
}
