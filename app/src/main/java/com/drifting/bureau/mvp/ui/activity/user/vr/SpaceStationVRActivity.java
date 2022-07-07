package com.drifting.bureau.mvp.ui.activity.user.vr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseActivity;
import com.drifting.bureau.mvp.ui.activity.user.FeedBackActivity;
import com.drifting.bureau.util.ClickUtil;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.jess.arms.di.component.AppComponent;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 空间站
 * @Author : WeiJiaQI
 * @Time : 2022/7/7 14:25
 */
public class SpaceStationVRActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.vrPanoramaView)
    VrPanoramaView mVRPanoramaView;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, SpaceStationVRActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_vr_space_station;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("空间站");
        mVRPanoramaView = (VrPanoramaView) findViewById(R.id.vrPanoramaView);
        mVRPanoramaView.setInfoButtonEnabled(false);//隐藏信息按钮
        mVRPanoramaView.setStereoModeButtonEnabled(false);//隐藏cardboard按钮
        mVRPanoramaView.setFullscreenButtonEnabled(false);//隐藏全屏按钮
        loadPhotoSphere();
    }


    @OnClick({R.id.toolbar_back})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
            }
        }
    }

    private void loadPhotoSphere() {
        VrPanoramaView.Options options = new VrPanoramaView.Options();
        InputStream inputStream = null;
        AssetManager assetManager = getAssets();
        try {
            inputStream = assetManager.open("image.png");
            options.inputType = VrPanoramaView.Options.TYPE_MONO;
            mVRPanoramaView.loadImageFromBitmap(BitmapFactory.decodeStream(inputStream), options);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mVRPanoramaView.pauseRendering();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVRPanoramaView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        mVRPanoramaView.shutdown();
        super.onDestroy();
    }


}
