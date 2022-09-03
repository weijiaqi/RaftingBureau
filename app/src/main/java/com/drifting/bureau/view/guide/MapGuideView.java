package com.drifting.bureau.view.guide;

import android.content.Context;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.SpannableUtil;
import com.jess.arms.utils.SystemUtil;

/**
 * @Description: 发送消息引导
 * @Author : WeiJiaQI
 * @Time : 2022/9/1 16:55
 */
public class MapGuideView extends LinearLayout implements View.OnClickListener {
    private Context mContext;

    private MapGuideView mapGuideView;

    private TextView mTvBar, mTvTop, mTvBottom;
    private LinearLayout mLlTop, mLlBottom;
    private SpannableStringBuilder passer;
    private OnClickCallback onClickCallback;
    public MapGuideView(Context context) {
        super(context);
    }

    public MapGuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public MapGuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mapGuideView = findViewById(R.id.guide_view);
        mTvBar = findViewById(R.id.tv_bar);
        mTvTop = findViewById(R.id.tv_top);
        mTvBottom = findViewById(R.id.tv_bottom);
        mLlTop = findViewById(R.id.ll_top);
        mLlBottom = findViewById(R.id.ll_bottom);
        mLlTop.setOnClickListener(this);
        mLlBottom.setOnClickListener(this);
        initDatas();
    }

    public void initDatas() {
        mTvBar.setHeight(SystemUtil.getStatusBarHeight(mContext));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTvBar.setVisibility(View.VISIBLE);
        } else {
            mTvBar.setVisibility(GONE);
        }
        showType(1);
    }


    public void showType(int type) {
        if (type == 1) {
            mLlTop.setVisibility(View.VISIBLE);
            passer = SpannableUtil.getBuilder(mContext, "“录入一段语音，").setForegroundColor(R.color.white).setTextSize(12).append("漂到\n下一个星云").setForegroundColor(R.color.color_6d).setTextSize(12).append("”").setForegroundColor(R.color.white).setTextSize(12).build();
            mTvTop.setText(passer);
        } else if (type == 2) {
            mLlTop.setVisibility(View.GONE);
            mLlBottom.setVisibility(View.VISIBLE);
            passer = SpannableUtil.getBuilder(mContext, "“输写你的故事\n").setForegroundColor(R.color.white).setTextSize(12).append("传递").setForegroundColor(R.color.color_6d).setTextSize(12).append("到下一个星云”").setForegroundColor(R.color.white).setTextSize(12).build();
            mTvBottom.setText(passer);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_top:
                showType(2);
                break;
            case R.id.ll_bottom:
               Preferences.setPostGuide(true);
                if (onClickCallback != null) {
                    onClickCallback.onClickType();
                }
                break;
        }
    }


    public interface OnClickCallback {
        void onClickType();
    }

    public void setOnClickCallback(OnClickCallback onClickCallback) {
        this.onClickCallback = onClickCallback;
    }
}
