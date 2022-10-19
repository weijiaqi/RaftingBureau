package com.drifting.bureau.mvp.ui.dialog;

import static com.drifting.bureau.app.api.Api.WEB_BASEURL;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.OpenBoxEntity;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.EncodingHandler;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.utils.ArmsUtils;

/**
 * @Description: 中奖地址
 * @Author : WeiJiaQI
 * @Time : 2022/10/11 16:06
 */
public class WinningAddressDialog extends BaseDialog implements View.OnClickListener {
    private Context context;
    private ImageView mIvPic, mIvSubmit, mIvClose;
    private EditText mEtName, mEtPhone, mEtAddress;
    private String url;


    public WinningAddressDialog(@NonNull Context context, String url) {
        super(context, R.style.alpha_base_dialog);
        this.context = context;
        this.url = url;
    }


    @Override
    protected void initDatas() {
        super.initDatas();
        mIvPic = findViewById(R.id.iv_pic);
        mIvClose = findViewById(R.id.iv_close);
        mEtName = findViewById(R.id.et_name);
        mEtPhone = findViewById(R.id.et_phone);
        mEtAddress = findViewById(R.id.et_address);
        mIvSubmit = findViewById(R.id.iv_submit);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mIvClose.setOnClickListener(this);
        mIvSubmit.setOnClickListener(this);
        GlideUtil.create().loadNormalPic(context, url, mIvPic);
    }


    @Override
    protected int getContentView() {
        return R.layout.dialog_winning_address;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }

    @Override
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.iv_close:
                    dismiss();
                    break;
                case R.id.iv_submit:
                    if (StringUtil.isEmpty(mEtName.getText().toString())) {
                        ShowMessage("请输入姓名!");
                        return;
                    }
                    if (StringUtil.isEmpty(mEtPhone.getText().toString())) {
                        ShowMessage("请输入联系电话!");
                        return;
                    }

                    if (StringUtil.isEmpty(mEtAddress.getText().toString())) {
                        ShowMessage("请输入收货地址!");
                        return;
                    }

                    if (onAddressClickCallback != null) {
                        onAddressClickCallback.onAddressClick(mEtName.getText().toString(), mEtPhone.getText().toString(), mEtAddress.getText().toString());
                    }
                    break;
            }
        }
    }

    public void ShowMessage(String message) {
        ToastUtil.showToast(message);
    }
}
