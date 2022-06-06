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

    private TextView  mTvTitle,mTvContent,  mTvClick;

    public static final int SELECT_FINISH = 0x01;

    @Override
    protected void initDatas() {
        super.initDatas();
        mTvTitle=findViewById(R.id.tv_title);
        mTvContent=findViewById(R.id.tv_content);
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
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_FINISH);
                }
                break;
        }
    }

    /**
     * 大标题文案设置
     *
     * @param title
     */
    public PublicDialog setTitleText(String title) {
        mTvTitle.setText(title);
        return this;
    }

    /**
     * 内容
     *
     * @param content
     */
    public PublicDialog setContentText(String content) {
        mTvContent.setText(content);
        return this;
    }


    /**
     * 按钮
     *
     * @param content
     */
    public PublicDialog setButtonText(String content) {
        mTvClick.setText(content);
        return this;
    }
}
