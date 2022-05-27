package com.rb.core.xrecycleview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.star.core.R;

import java.util.Date;


/**
 * RecyclerView头布局
 */
public class XRecyclerViewHeader extends LinearLayout {
    public static final String TAG = XRecyclerViewHeader.class.getSimpleName();
    public LinearLayout mContainer;
    private Context mContext;

    public final static int STATE_NORMAL = 0;// 初始状态
    public final static int STATE_RELEASE_TO_REFRESH = 1;    // 释放刷新
    public final static int STATE_REFRESHING = 2;    // 正在刷新

    private ImageView mArrowImageView;//箭头
    private ImageView mProgressBar;    // 正在刷新的图标
    private TextView mStatusTextView;//状态文字
    private int mState = STATE_NORMAL;  // 当前状态（临时保存用）
    private TextView mHeaderTimeView;//刷新时间文本
    AnimationDrawable ad, ad2;//逐帧动画
    //以下是箭头图标动画
    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    private final int ROTATE_ANIM_DURATION = 0;//旋转角度
    public int mMeasuredHeight;//布局的原始高度，用于做状态改变的标志
    // 均匀旋转动画
    private RotateAnimation refreshingAnimation;
    private String prush_content = "别急哦";
    private DistencecCallBack distencecCallBack;

    public XRecyclerViewHeader(Context context) {
        super(context);
        initView(context);
    }

    public XRecyclerViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public XRecyclerViewHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public void setDistencecCallBack(DistencecCallBack distencecCallBack) {
        this.distencecCallBack = distencecCallBack;
    }

    //初始化
    private void initView(Context context) {
        mContext = context;

        // 初始情况，设置下拉刷新view高度为0
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.listview_header, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);

        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);

        mArrowImageView = findViewById(R.id.listview_header_arrow);
        mStatusTextView = findViewById(R.id.refresh_status_textview);
        mProgressBar = findViewById(R.id.listview_header_progressbar);
        mHeaderTimeView = findViewById(R.id.last_refresh_time);

        //箭头改变动画
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
        ///添加匀速转动动画
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.rotating);
        LinearInterpolator lir = new LinearInterpolator();
        refreshingAnimation.setInterpolator(lir);


        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
    }

    //设置状态
    public void setState(int state) {
        if (state == mState) {
            return;
        }
        //判断显示
        switch (state) {
            case STATE_NORMAL:
            case STATE_RELEASE_TO_REFRESH:
                mProgressBar.clearAnimation();
                mArrowImageView.setVisibility(VISIBLE);

                break;
            case STATE_REFRESHING:
                mArrowImageView.clearAnimation();
                mArrowImageView.setVisibility(View.INVISIBLE);

                break;
            default:
                break;
        }
        //判断动画的添加
        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_RELEASE_TO_REFRESH) {
                    mArrowImageView.startAnimation(mRotateDownAnim);
                }
                if (mState == STATE_REFRESHING) {
                    mArrowImageView.clearAnimation();
                }
                if (ad != null && ad.isRunning()) {
                    ad.stop();
                }
                if (ad2 != null && ad2.isRunning()) {
                    ad2.stop();
                }
                mStatusTextView.setText(R.string.down_refresh);
               // mStatusTextView.setText(prush_content);
                break;
            case STATE_RELEASE_TO_REFRESH:
                if (mState != STATE_RELEASE_TO_REFRESH) {
                    if (ad != null && ad.isRunning()) {
                        ad.stop();
                    }
                    if (ad2 != null && ad2.isRunning()) {
                        ad2.stop();
                    }
                    if (mState == STATE_REFRESHING) {
                        mArrowImageView.clearAnimation();
                    }
                     // 设置背景动画
                      mArrowImageView.startAnimation(mRotateUpAnim);
                 //   mArrowImageView.setBackgroundResource(R.drawable.round_spinner_fade);
//                    ad2 = (AnimationDrawable) mArrowImageView.getBackground();
//                    ad2.start();
                    mStatusTextView.setText(R.string.to_refresh);
                   // mStatusTextView.setText(prush_content);
                }
                break;
            case STATE_REFRESHING:
               mProgressBar.startAnimation(refreshingAnimation);
                if (ad != null && ad.isRunning()) {
                    ad.stop();
                }
                if (ad2 != null && ad2.isRunning()) {
                    ad2.stop();
                }
                // 设置背景动画
//                mProgressBar.setBackgroundResource(R.drawable.round_spinner_fade);
//                ad = (AnimationDrawable) mProgressBar.getBackground();
//                ad.start();
               mStatusTextView.setText(R.string.loading);
             //   mStatusTextView.setText(prush_content);
                break;
            default:
                break;
        }
        mState = state;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (ad != null && ad.isRunning()) {
            ad.stop();
        }
        if (ad2 != null && ad2.isRunning()) {
            ad2.stop();
        }
    }

    //返回当前状态
    public int getState() {
        return mState;
    }

    //完成刷新
    public void refreshComplate() {
        //注释的是时间文本  有需要可以去掉  也是在布局里改可见属性
        mHeaderTimeView.setText(friendlyTime(new Date()));
        smoothScrollTo(0);
        setState(STATE_NORMAL);
    }

    /**
     * 刷新头滑动改变
     */
    public void onMove(float dalta) {
        Log.e(TAG, "setRefreshing4: ");
        if (getVisiableHeight() > 0 || dalta > 0) {
            setVisiableHeight((int) dalta + getVisiableHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) {// 未处于刷新状态，更新箭头
                if (getVisiableHeight() > mMeasuredHeight) {
                    setState(STATE_RELEASE_TO_REFRESH);
                } else {
                    setState(STATE_NORMAL);
                }
            }
        }
    }

    /**
     * 释放刷新头的时候，是否满足刷新的要求
     */
    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisiableHeight();
        if (height == 0) {
            isOnRefresh = false;
        }
        //刷新时改变状态
        if (getVisiableHeight() > mMeasuredHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        //刷新时回滚到原始高度
        int destHeight = 0;
        if (mState == STATE_REFRESHING) {
            destHeight = mMeasuredHeight;
        }
        smoothScrollTo(destHeight);
        return isOnRefresh;
    }

    //回滚到顶部
    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisiableHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisiableHeight((Integer) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    //设置刷新头可见的高度
    public void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LayoutParams lp = (LayoutParams) mContainer
                .getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
        if (distencecCallBack!=null){
            distencecCallBack.onDistenceChange(height);
        }
    }

    //获得刷新头可见的高度
    public int getVisiableHeight() {
        int height = 0;
        LayoutParams lp = (LayoutParams) mContainer
                .getLayoutParams();
        height = lp.height;
        return height;
    }

    //刷新时间计算
    public static String friendlyTime(Date time) {
        //获取time距离当前的秒数
        int ct = (int) ((System.currentTimeMillis() - time.getTime()) / 1000);

        if (ct == 0) {
            return "刚刚";
        }

        if (ct > 0 && ct < 60) {
            return ct + "秒前";
        }

        if (ct >= 60 && ct < 3600) {
            return Math.max(ct / 60, 1) + "分钟前";
        }
        if (ct >= 3600 && ct < 86400)
            return ct / 3600 + "小时前";
        if (ct >= 86400 && ct < 2592000) { //86400 * 30
            int day = ct / 86400;
            return day + "天前";
        }
        if (ct >= 2592000 && ct < 31104000) { //86400 * 30
            return ct / 2592000 + "月前";
        }
        return ct / 31104000 + "年前";
    }
    public interface DistencecCallBack{
        void onDistenceChange(int distence);
    }
}
