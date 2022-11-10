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
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseDialog;

/**
 * @Description: 领取专属星球
 * @Author : WeiJiaQI
 * @Time : 2022/8/17 19:08
 */
public class ExclusivePlanetDialog extends BaseDialog implements View.OnClickListener {

    public static final int OPEN_PLAY = 0x01;

    private RelativeLayout mRlCenter;
    private TextView mTvOpenPlay;


    public ExclusivePlanetDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void initView() {
        super.initView();
        mRlCenter=findViewById(R.id.rl_center);
        mTvOpenPlay = findViewById(R.id.tv_open_paly);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mRlCenter.setOnClickListener(this);
        mTvOpenPlay.setOnClickListener(this);
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
                case R.id.rl_center:
                    dismiss();
                    break;
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
