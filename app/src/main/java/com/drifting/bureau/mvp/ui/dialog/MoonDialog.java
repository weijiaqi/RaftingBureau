package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.view.guide.ArAirplaneDialogGuiView;
import com.jess.arms.base.BaseDialog;
/**
 * @Description: 月亮dialog
 * @Author     : WeiJiaQI
 * @Time       : 2022/9/9 18:15
 */
public class MoonDialog  extends BaseDialog {

    public static final int OPEN_PLAY = 0x01;

    private TextView mTvOpenPlay;
    private RelativeLayout mRlCenter;
    private ArAirplaneDialogGuiView mGuideView;

    public MoonDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void initView() {
        super.initView();
        mTvOpenPlay = findViewById(R.id.tv_open_paly);
        mRlCenter = findViewById(R.id.rl_center);
        mGuideView = findViewById(R.id.guide_view);
    }




    @Override
    protected int getContentView() {
        return R.layout.dialog_moon;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }


    @Override
    protected void getWindows(Window window) {
        super.getWindows(window);
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.EnterDialog);
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

}

