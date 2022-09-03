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
import com.jess.arms.utils.SystemUtil;

/**
 * @author 卫佳琪1
 * @description AR引导页
 * @time 10:22 10:22
 */

public class ArGuideView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private TextView mTvBar, mTvTitle;
    private ArGuideView mGuideView;
    private SpannableStringBuilder passer;
    private OnClickCallback onClickCallback;
    public ArGuideView(Context context) {
        super(context);
    }

    public ArGuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ArGuideView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        passer = SpannableUtil.getBuilder(mContext, "“Hi\n欢迎来到元宇宙漂流局\n").setForegroundColor(R.color.white).setTextSize(12).append("一起开启奇幻之旅”").setForegroundColor(R.color.color_6d).setTextSize(12).build();
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
