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
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.view.guide.ArAirplaneDialogGuiView;
import com.drifting.bureau.view.guide.ArDialogGuiView;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.base.BottomDialog;

/**
 * @Description: 领取专属星球
 * @Author : WeiJiaQI
 * @Time : 2022/8/17 19:08
 */
public class ExclusivePlanetDialog extends BottomDialog implements View.OnClickListener {

    public static final int OPEN_PLAY = 0x01;

    private TextView mTvOpenPlay;
    private RelativeLayout mRlCenter;
    private ArAirplaneDialogGuiView mGuideView;

    public ExclusivePlanetDialog(@NonNull Context context) {
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
    protected void initEvents() {
        super.initEvents();
        mTvOpenPlay.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
//        if (!Preferences.isArAirplane()) {  //启动引导页
//            //设置距离底部为true
//            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT); // or wrap_content
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//            mRlCenter.setLayoutParams(layoutParams);
//            mGuideView.setVisibility(View.VISIBLE);
//        }
//
//        mGuideView.setOnClickCallback(() -> {
//            Preferences.setArAirplane(true);
//            dismiss();
//            if (onClickCallback != null) {
//                onClickCallback.onClickType(OPEN_PLAY);
//            }
//        });
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_exclusive_planet;
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

    @Override
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_open_paly:
                    dismiss();
                    if (onClickCallback != null) {
                        onClickCallback.onClickType(OPEN_PLAY);
                    }
                    break;
            }
        }
    }
}
