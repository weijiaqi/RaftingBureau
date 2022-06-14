package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.util.EncodingHandler;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.utils.ArmsUtils;

/**
 * @Description: 我得核销码
 * @Author : WeiJiaQI
 * @Time : 2022/5/28 11:50
 */
public class WriteOffCodeDialog extends BaseDialog {

    private Context context;
    private String token;

    private ImageView mIvcode;

    public WriteOffCodeDialog(@NonNull Context context, String token) {
        super(context);
        this.context = context;
        this.token = token;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mIvcode = findViewById(R.id.iv_code);

    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mIvcode.setImageBitmap(EncodingHandler.createQRCode(token, ArmsUtils.dip2px(context, 187), ArmsUtils.dip2px(context, 187), BitmapFactory.decodeResource(context.getResources(), R.mipmap.leak_canary_icon)));
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_write_off_code;
    }

    @Override
    protected float getDialogWith() {
        return 0.8f;
    }
}
