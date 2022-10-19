package com.drifting.bureau.mvp.ui.dialog;

import static com.drifting.bureau.app.api.Api.WEB_BASEURL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    private TextView mTvSavePic;
    private RelativeLayout mRltop;
    private ImageView mIvCode;
    private Handler mHandler = new Handler();
    private Context context;
    private UserInfoEntity userInfoEntity;


    public ShareDialog(@NonNull Context context, UserInfoEntity userInfoEntity) {
        super(context);
        this.context = context;
        this.userInfoEntity = userInfoEntity;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mTvSavePic = findViewById(R.id.tv_save_pic);
        mRltop = findViewById(R.id.rl_top);
        mIvCode = findViewById(R.id.iv_code);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvSavePic.setOnClickListener(this);
        mIvCode.setImageBitmap(EncodingHandler.createQRCode(WEB_BASEURL + "?share_code=" + userInfoEntity.getUser().getShare_code(), ArmsUtils.dip2px(context, 67), ArmsUtils.dip2px(context, 67), BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_logo)));
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_share;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_save_pic:
                showLoading();
                mHandler.postDelayed(() -> {
                    Bitmap bitmap = BitmapUtil.captureView(mRltop);
                    Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null, null));
                    //系统调用分享
                    hideLoading();
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    shareIntent.setType("image/*");
                    shareIntent = Intent.createChooser(shareIntent, "Share");
                    context.startActivity(shareIntent);

//                    PermissionDialog.requestPermissions((Activity) context, new PermissionDialog.PermissionCallBack() {
//                        @Override
//                        public void onSuccess() {
//                            BitmapUtil.saveImageToGallery(bitmap, context);
//                            dismiss();
//                            hideLoading();
//                            ToastUtil.showToast("保存相册成功");
//                        }
//
//                        @Override
//                        public void onFailure() {
//                        }
//
//                        @Override
//                        public void onAlwaysFailure() {
//                            PermissionDialog.showDialog((Activity) context, "android.permission.WRITE_EXTERNAL_STORAGE");
//                        }
//                    });
                }, 500);
                break;
        }
    }


    public void showLoading() {
        ViewUtil.create().show((Activity) context);
    }

    public void hideLoading() {
        ViewUtil.create().dismiss();
        dismiss();
    }

}
