package com.drifting.bureau.view.guide;

import android.content.Context;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.util.SpannableUtil;
import com.drifting.bureau.util.SystemUtil;

/**
 * @description  AR 点击飞机进入星球引导
 * @author 卫佳琪1
 * @time 15:00 15:00
 */

public class ArAirplaneDialogGuiView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private TextView mTvBar, mTvTitle;
    private ArAirplaneDialogGuiView mGuideView;
    private SpannableStringBuilder passer;
    private OnClickCallback onClickCallback;
    public ArAirplaneDialogGuiView(Context context) {
        super(context);
    }

    public ArAirplaneDialogGuiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ArAirplaneDialogGuiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mGuideView = findViewById(R.id.guide_view);
        mTvBar = findViewById(R.id.tv_bar);
        mTvTitle = findViewById(R.id.tv_title);
        mGuideView.setOnClickListener(this);
        initDatas();
    }

    public void initDatas() {
        passer = SpannableUtil.getBuilder(mContext, "“").setForegroundColor(R.color.white).setTextSize(12).append("寻找").setForegroundColor(R.color.color_6d).setTextSize(12).append("你的星球吧！”").setForegroundColor(R.color.white).setTextSize(12).build();
        mTvTitle.setText(passer);
        mTvBar.setHeight(SystemUtil.getStatusBarHeight(mContext));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTvBar.setVisibility(View.VISIBLE);
        } else {
            mTvBar.setVisibility(GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guide_view:
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
