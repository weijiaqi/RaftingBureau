package com.drifting.bureau.mvp.ui.activity.index.vr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseActivity;
import com.drifting.bureau.util.ClickUtil;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.jess.arms.di.component.AppComponent;

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

    private static String TAG = SpaceStationVRActivity.class.getSimpleName();
    private static final String AR_URL = "ar_url";
    private String arurl;

    public static void start(Context context, String arurl, boolean closePage) {
        Intent intent = new Intent(context, SpaceStationVRActivity.class);
        intent.putExtra(AR_URL, arurl);
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
        if (getIntent() != null) {
            arurl = getIntent().getStringExtra(AR_URL);
        }
        mVRPanoramaView.setTouchTrackingEnabled(true);
        mVRPanoramaView.setFullscreenButtonEnabled(false);
        mVRPanoramaView.setInfoButtonEnabled(false);
        mVRPanoramaView.setStereoModeButtonEnabled(false);

        try {
            new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... params) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = Glide.with(SpaceStationVRActivity.this)
                                .asBitmap()
                                .load(arurl)
                                .submit(1080, 1920).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return bitmap;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    if (bitmap != null) {
                        VrPanoramaView.Options options = new VrPanoramaView.Options();
                        //设置图片类型为单通道图片
                        options.inputType = VrPanoramaView.Options.TYPE_MONO;
                        mVRPanoramaView.loadImageFromBitmap(bitmap, options);
                    }
                }
            }.execute();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

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
