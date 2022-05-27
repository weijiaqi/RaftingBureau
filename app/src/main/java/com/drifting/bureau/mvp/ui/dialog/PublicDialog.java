package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;

/**
 * @description 公共弹框
 */
public class PublicDialog extends BaseDialog  implements View.OnClickListener{

    private TextView mTvClick;

    public static final int SELECT_FINISH = 0x01;

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

    public PublicDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_public;
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

                break;
        }
    }
}
