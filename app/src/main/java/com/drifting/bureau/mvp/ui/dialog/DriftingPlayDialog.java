package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;

import com.drifting.bureau.mvp.model.entity.StarUpIndexEntity;
import com.drifting.bureau.mvp.ui.activity.web.ShowWebViewActivity;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.AppUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BottomDialog;

/**
 * @author 卫佳琪1
 * @description 玩法
 * @time 18:30 18:30
 */

public class DriftingPlayDialog extends BottomDialog implements View.OnClickListener {

    private Context context;
    private TextView mTvStartSpace, mTvOpenPlay, mTvYouthCamp;
    public static final int START_SPACE = 0x01;
    public static final int OPEN_PLAY = 0x02;
    private StarUpIndexEntity starUpIndexEntity;

    public DriftingPlayDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void initView() {
        super.initView();
        mTvStartSpace = findViewById(R.id.tv_start_space);
        mTvOpenPlay = findViewById(R.id.tv_open_paly);
        mTvYouthCamp = findViewById(R.id.tv_youth_camp);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvStartSpace.setOnClickListener(this);
        mTvOpenPlay.setOnClickListener(this);
        mTvYouthCamp.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        RequestUtil.create().startup(entity1 -> {
            if (entity1 != null && entity1.getCode() == 200) {
                starUpIndexEntity = entity1.getData();
                mTvYouthCamp.setVisibility(!TextUtils.isEmpty(starUpIndexEntity.getUrl()) ? View.VISIBLE : View.GONE);
            }
        });
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
                case R.id.tv_start_space:
                    dismiss();
                    if (onClickCallback != null) {
                        onClickCallback.onClickType(START_SPACE);
                    }
                    break;
                case R.id.tv_open_paly:
                    dismiss();
                    if (onClickCallback != null) {
                        onClickCallback.onClickType(OPEN_PLAY);
                    }
                    break;
                case R.id.tv_youth_camp:
<<<<<<< HEAD
                    dismiss();
=======
>>>>>>> origin/dev
                    ShowWebViewActivity.start(context, 0, "青年创业营", starUpIndexEntity.getUrl() + "?Sign=" + StringUtil.formatNullString(AppUtil.getSign(Preferences.getPhone())) + "&token=" + StringUtil.formatNullString(Preferences.getToken()), false);
                    break;
            }
        }
    }
}
