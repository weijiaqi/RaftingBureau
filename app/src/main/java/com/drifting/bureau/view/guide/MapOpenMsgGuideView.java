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
 * @author 卫佳琪1
 * @description 开启漂流引导
 * @time 19:19 19:19
 */

public class MapOpenMsgGuideView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private TextView mTvBar, mTvTitle;
    private MapOpenMsgGuideView mGuideView;
    private SpannableStringBuilder passer;
    private OnClickCallback onClickCallback;

    public MapOpenMsgGuideView(Context context) {
        super(context);
    }

    public MapOpenMsgGuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public MapOpenMsgGuideView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        passer = SpannableUtil.getBuilder(mContext, "“点击开启漂流”\n").setForegroundColor(R.color.color_6d).setTextSize(12).append("输入想要漂流的话题内容").setForegroundColor(R.color.white).setTextSize(12).build();
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
