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
 * @author 卫佳琪1
 * @description 跳过主星球
 * @time 16:59 16:59
 */

public class JumpPlanetDialog extends BaseDialog implements View.OnClickListener {

    public static final int OPEN_PLAY = 0x01;

    public static final int OPEN_JUMP = 0x02;

    private TextView mTvOpenPlay, mTvJump;
    private RelativeLayout mRlContent;

    public JumpPlanetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        super.initView();
        mTvOpenPlay = findViewById(R.id.tv_open_paly);
        mTvJump = findViewById(R.id.tv_open_jump);
        mRlContent = findViewById(R.id.rl_content);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvOpenPlay.setOnClickListener(this);
        mTvJump.setOnClickListener(this);
        mRlContent.setOnClickListener(this);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_jump_planet;
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
                case R.id.rl_content:
                    dismiss();
                    break;
                case R.id.tv_open_paly:
                    dismiss();
                    if (onClickCallback != null) {
                        onClickCallback.onClickType(OPEN_PLAY);
                    }
                    break;
                case R.id.tv_open_jump:  //跳转到主星球
                    dismiss();
                    if (onClickCallback != null) {
                        onClickCallback.onClickType(OPEN_JUMP);
                    }
                    break;
            }
        }
    }
}
