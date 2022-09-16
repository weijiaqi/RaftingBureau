package com.drifting.bureau.mvp.ui.activity.index;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;

import com.drifting.bureau.mvp.ui.adapter.ImagePreviewAdapter;

import com.jess.arms.base.RecyclerPagerAdapter;
import com.jess.arms.di.component.AppComponent;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;

/**
 * @Description: 查看大图
 * @Author : WeiJiaQI
 * @Time : 2022/9/16 18:38
 */
public class ImagePreviewActivity extends BaseManagerActivity  implements ViewPager.OnPageChangeListener{
    @BindView(R.id.vp_image_preview_pager)
    ViewPager mViewPager;
    @BindView(R.id.ci_image_preview_indicator)
    CircleIndicator mCircleIndicatorView;
    @BindView(R.id.tv_image_preview_indicator)
    TextView mTextIndicatorView;

    private ImagePreviewAdapter mAdapter;

    private static final String INTENT_KEY_IN_IMAGE_LIST = "imageList";
    private static final String INTENT_KEY_IN_IMAGE_INDEX = "imageIndex";

    public static void start(Context context, String url) {
        ArrayList<String> images = new ArrayList<>(1);
        images.add(url);
        start(context, images);
    }

    public static void start(Context context, List<String> urls) {
        start(context, urls, 0);
    }


    public static void start(Context context, List<String> urls, int index) {
        if (urls == null || urls.isEmpty()) {
            return;
        }
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        if (urls.size() > 2000) {
            // 请注意：如果传输的数据量过大，会抛出此异常，并且这种异常是不能被捕获的
            // 所以当图片数量过多的时候，我们应当只显示一张，这种一般是手机图片过多导致的
            // 经过测试，传入 3121 张图片集合的时候会抛出此异常，所以保险值应当是 2000
            // android.os.TransactionTooLargeException: data parcel size 521984 bytes
            urls = Collections.singletonList(urls.get(index));
        }

        if (urls instanceof ArrayList) {
            intent.putExtra(INTENT_KEY_IN_IMAGE_LIST, (ArrayList<String>) urls);
        } else {
            intent.putExtra(INTENT_KEY_IN_IMAGE_LIST, new ArrayList<>(urls));
        }
        intent.putExtra(INTENT_KEY_IN_IMAGE_INDEX, index);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_image_preview;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        ArrayList<String> images = getIntent().getStringArrayListExtra(INTENT_KEY_IN_IMAGE_LIST);
        if (images == null || images.isEmpty()) {
            finish();
            return;
        }
        mAdapter = new ImagePreviewAdapter(new ArrayList<>());
        mAdapter.setData(images);
        mAdapter.setRecyclerItemClickListner((v, position) -> {
            if (isFinishing() || isDestroyed()) {
                return;
            }
            // 单击图片退出当前的 Activity
            finish();
        });
        mViewPager.setAdapter(new RecyclerPagerAdapter(mAdapter));
        if (images.size() != 1) {
            if (images.size() < 10) {
                // 如果是 10 张以内的图片，那么就显示圆圈指示器
                mCircleIndicatorView.setVisibility(View.VISIBLE);
                mCircleIndicatorView.setViewPager(mViewPager);
            } else {
                // 如果超过 10 张图片，那么就显示文字指示器
                mTextIndicatorView.setVisibility(View.VISIBLE);
                mViewPager.addOnPageChangeListener(this);
            }

            int index = getIntent().getIntExtra(INTENT_KEY_IN_IMAGE_INDEX,0);
            if (index < images.size()) {
                mViewPager.setCurrentItem(index);
                onPageSelected(index);
            }
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @SuppressLint("SetTextI18n")
    @Override
    public void onPageSelected(int position) {
        mTextIndicatorView.setText((position + 1) + "/" + mAdapter.getItemCount());
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mViewPager.removeOnPageChangeListener(this);
    }
}
