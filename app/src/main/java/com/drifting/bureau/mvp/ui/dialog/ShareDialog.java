package com.drifting.bureau.mvp.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.util.BitmapUtil;
import com.drifting.bureau.util.EncodingHandler;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.utils.ArmsUtils;

/**
 * @Description: 分享
 * @Author : WeiJiaQI
 * @Time : 2022/5/29 16:40
 */
public class ShareDialog extends BaseDialog implements View.OnClickListener {
    private TextView mTvSavePic, mTvName, mTvIdentity, mTvAddress, mTvNum;
    private ProgressBar mPrUploadValue;
    private LinearLayout mLltop;
    private ImageView mIvCode;
    private Handler mHandler = new Handler();
    private Context context;
    private UserInfoEntity userInfoEntity;
    private String code;

    public ShareDialog(@NonNull Context context, UserInfoEntity userInfoEntity, String code) {
        super(context);
        this.context = context;
        this.userInfoEntity = userInfoEntity;
        this.code = code;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mTvSavePic = findViewById(R.id.tv_save_pic);
        mLltop = findViewById(R.id.ll_top);
        mTvName = findViewById(R.id.tv_name);
        mTvIdentity = findViewById(R.id.tv_identity);
        mTvAddress = findViewById(R.id.tv_address);
        mPrUploadValue = findViewById(R.id.pr_upload_value);
        mTvNum = findViewById(R.id.tv_num);
        mIvCode=findViewById(R.id.iv_code);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvSavePic.setOnClickListener(this);
        mTvName.setText(userInfoEntity.getUser().getName());
        mTvIdentity.setText(userInfoEntity.getUser().getLevel_name());
        mTvAddress.setText(userInfoEntity.getPlanet().getName());
        mTvNum.setText(userInfoEntity.getPlanet().getSchedule() + "%");
        mPrUploadValue.setProgress(userInfoEntity.getPlanet().getSchedule());
        mIvCode.setImageBitmap(EncodingHandler.createQRCode(code, ArmsUtils.dip2px(context,67), ArmsUtils.dip2px(context,67), BitmapFactory.decodeResource(context.getResources(), R.drawable.plus)));
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_share;
    }

    @Override
    protected float getDialogWith() {
        return 0.85f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_save_pic:
                showLoading();
                mHandler.postDelayed(() -> {
                    Bitmap bitmap = BitmapUtil.captureView(mLltop);
                    PermissionDialog.requestPermissions((Activity) context, new PermissionDialog.PermissionCallBack() {
                        @Override
                        public void onSuccess() {
                            dismiss();
                            BitmapUtil.saveImageToGallery(bitmap, context);
                            hideLoading();
                            ToastUtil.showToast("保存相册成功");
                        }

                        @Override
                        public void onFailure() {
                        }

                        @Override
                        public void onAlwaysFailure() {
                            PermissionDialog.showDialog((Activity) context, "android.permission.WRITE_EXTERNAL_STORAGE");
                        }
                    });
                }, 500);
                break;
        }
    }


    public void showLoading() {
        ViewUtil.create().show((Activity) context);
    }

    public void hideLoading() {
        ViewUtil.create().dismiss();
    }

}
