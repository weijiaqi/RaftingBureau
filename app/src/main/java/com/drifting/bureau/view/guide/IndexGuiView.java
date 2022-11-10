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
import com.drifting.bureau.util.SystemUtil;

/**
 * @Description: 首页引导
 * @Author : WeiJiaQI
 * @Time : 2022/9/1 15:58
 */
public class IndexGuiView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private IndexGuiView mGuideView;
    private TextView mTvBar, mTvTitle;
    private SpannableStringBuilder passer;
    private OnClickCallback onClickCallback;
    public IndexGuiView(Context context) {
        super(context);
    }

    public IndexGuiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public IndexGuiView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        passer = SpannableUtil.getBuilder(mContext, "欢迎来到元宇宙漂流局,\n接下来请开启你的传递之旅\n").setForegroundColor(R.color.white).setTextSize(12).setBold().append("点击“传递漂”").setForegroundColor(R.color.color_6d).setBold().setTextSize(12).build();
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
                Preferences.setOrdinaryGuide(true);
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
