package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
<<<<<<< HEAD
import com.drifting.bureau.base.BaseManagerActivity;
=======
import com.drifting.bureau.base.BaseActivity;
>>>>>>> origin/dev
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GlideUtil;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.picture.photoview.PhotoView;

/**
 * @Description: 图片查看
 * @Author : WeiJiaQI
 * @Time : 2022/7/12 15:37
 */
<<<<<<< HEAD
public class SelectImageActivity extends BaseManagerActivity {
=======
public class SelectImageActivity extends BaseActivity {
>>>>>>> origin/dev
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.photo_view)
    PhotoView mPhotoView;
    private static String EXTRA_URL = "extra_url";

    private String url;

    public static void start(Context context, String url, boolean closePage) {
        Intent intent = new Intent(context, SelectImageActivity.class);
        intent.putExtra(EXTRA_URL, url);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_select_image;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("图片查看");
        if (getIntent() != null) {
            url = getIntent().getStringExtra(EXTRA_URL);
        }
        GlideUtil.create().loadLongMap(this,url,mPhotoView);
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
}
