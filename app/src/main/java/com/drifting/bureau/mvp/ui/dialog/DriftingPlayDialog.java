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
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseDialog;


/**
 * @author 卫佳琪1
 * @description 玩法
 * @time 18:30 18:30
 */

public class DriftingPlayDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    private TextView mTvOpenJump, mTvOpenPlay,mTvYouthCamp ;
    private RelativeLayout mRlCenter;

    public static final int YOUTH_CAP = 0x01;
    public static final int OPEN_PLAY = 0x02;
    public static final int OPEN_JUMP = 0x03;
    private UserInfoEntity userInfoEntity;

    public DriftingPlayDialog(@NonNull Context context,UserInfoEntity userInfoEntity) {
        super(context);
        this.context = context;
        this.userInfoEntity=userInfoEntity;
    }

    @Override
    protected void initView() {
        super.initView();
        mTvOpenJump = findViewById(R.id.tv_open_jump);
        mTvOpenPlay = findViewById(R.id.tv_open_paly);
        mTvYouthCamp = findViewById(R.id.tv_youth_camp);
        mRlCenter= findViewById(R.id.rl_center);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvOpenJump.setOnClickListener(this);
        mTvOpenPlay.setOnClickListener(this);
        mTvYouthCamp.setOnClickListener(this);
        mRlCenter.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        if (userInfoEntity!=null){
            if (userInfoEntity.getPlanet().getLevel() == 1) {  //荒芜星
                mTvYouthCamp.setVisibility(View.INVISIBLE);
            } else {
                mTvYouthCamp.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_drifting_play;
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
                case R.id.tv_youth_camp:
                    dismiss();
                    if (onClickCallback != null) {
                        onClickCallback.onClickType(YOUTH_CAP);
                    }
                    break;
                case R.id.tv_open_paly:
                    dismiss();
                    if (onClickCallback != null) {
                        onClickCallback.onClickType(OPEN_PLAY);
                    }
                    break;
                case R.id.tv_open_jump:
                    dismiss();
                    if (onClickCallback != null) {
                        onClickCallback.onClickType(OPEN_JUMP);
                    }
                    break;
            }
        }
    }
}
