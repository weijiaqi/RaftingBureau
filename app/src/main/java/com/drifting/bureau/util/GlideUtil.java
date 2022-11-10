package com.drifting.bureau.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.drifting.bureau.R;
import com.jess.arms.utils.ArmsUtils;


/**
 * @Description:
 * @Author: wjq
 * @CreateDate: 2022/2/21 10:53
 */
public class GlideUtil {
    private static GlideUtil mGlideUtil;

    public static GlideUtil create() {
        if (mGlideUtil == null) {
            synchronized (GlideUtil.class) {
                if (mGlideUtil == null) {
                    mGlideUtil = new GlideUtil();
                }
            }
        }
        return mGlideUtil;
    }


    /**
     * 加载约4:3长方形普通网络图片
     *
     * @param context   上下文
     * @param url       图片url链接
     * @param imageView ImageView控件
     */
    public void loadNormalPic(Context context, String url, ImageView imageView) {
        if (context == null || imageView == null) return;

        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.icon_home_page)
                    .error(R.drawable.icon_home_page);
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.icon_home_page);
        }
    }


    /**
     * 加载约4:3长方形普通网络图片（无占位图）
     *
     * @param context   上下文
     * @param url       图片url链接
     * @param imageView ImageView控件
     */
    public void loadDefaultPic(Context context, String url, ImageView imageView) {
        if (context == null || imageView == null) return;

        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.color.transparent)
                    .error(R.color.transparent);
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.color.transparent);
        }
    }

    /**
     * 展示长图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void loadLongImage(Context context, String url, ImageView imageView) {
        if (context == null || imageView == null) return;
        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)//关键代码，加载原始大小
                    .format(DecodeFormat.PREFER_RGB_565)//设置为这种格式去掉透明度通道，可以减少内存占有
                    .placeholder(R.color.transparent)
                    .error(R.color.transparent);
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.color.transparent);
        }
    }

    public void loadViewLongImage(Context context, String url, View view) {
        if (context == null || view == null) return;
        if (!TextUtils.isEmpty(url)) {
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            Drawable drawable = new BitmapDrawable(resource);
                            view.setBackground(drawable);
                        }

                    });

        } else {
            view.setBackgroundResource(context.getResources().getColor(R.color.black));
        }
    }


    /**
     * 3d图片保存
     * 添加disallowHardwareConfig 解决保存图片闪退问题
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void load3dImage(Context context, String url, ImageView imageView) {
        if (context == null || imageView == null) return;
        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .disallowHardwareConfig()
                    .placeholder(R.drawable.icon_home_page)
                    .error(R.drawable.icon_home_page);
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.icon_home_page);
        }
    }


    /**
     * 跳过缓存加载本地长图
     *
     * @param context   上下文
     * @param url       图片url链接
     * @param imageView ImageView控件
     */
    public void loadLongMap(Context context, String url, final ImageView imageView) {
        if (context == null || imageView == null) return;
        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .placeholder(R.drawable.icon_home_page)
                    .error(R.drawable.icon_home_page)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(false)
                    .dontAnimate();

            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.icon_home_page);
        }
    }


    /**
     * 加载drawable-gif
     *
     * @param context    上下文
     * @param drawableId 图片本地id
     * @param imageView  ImageView控件
     */
    public void loadFileGifImage(Context context, int drawableId, ImageView imageView) {
        if (context == null || imageView == null) return;
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context)
                .asGif()
                .load(drawableId)
                .apply(options)
                .into(imageView);
    }


    /**
     * 加载本地gif
     *
     * @param context   上下文
     * @param filePath  图片本地id
     * @param imageView ImageView控件
     */
    public void loadDiskGifImage(Context context, String filePath, ImageView imageView) {
        if (context == null || imageView == null) return;
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .placeholder(R.drawable.icon_home_page)
                .error(R.drawable.icon_home_page);
        Glide.with(context)
                .load(filePath)
                .apply(options)
                .into(imageView);
    }


    /**
     * 加载普通圆形图片（头像）
     *
     * @param context   上下文
     * @param url       图片url链接
     * @param imageView ImageView控件
     */
    public void loadCirclePic(Context context, String url, ImageView imageView) {
        if (context == null || imageView == null) return;
        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = RequestOptions
                    .circleCropTransform()
                    .placeholder(R.drawable.rc_default_portrait)
                    .error(R.drawable.rc_default_portrait)
                    .dontAnimate();

            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.rc_default_portrait);
        }
    }


    /**
     * 加载普通圆角图片
     *
     * @param context   上下文
     * @param url       图片url链接
     * @param imageView ImageView控件
     */
    public void loadCornersPic(Context context, String url, ImageView imageView) {
        if (context == null || imageView == null) return;
        if (!TextUtils.isEmpty(url)) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.color.white)
                    .bitmapTransform(new RoundedCorners(ArmsUtils.dip2px(context, 10)));
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.color.white);
        }
    }
}
