package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.ui.activity.user.MySpaceStationActivity;
import com.drifting.bureau.mvp.ui.dialog.HowToPlayDialog;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 太空舱
 * @Author : WeiJiaQI
 * @Time : 2022/5/24 9:39
 */
public class SpaceCapsuleActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    private HowToPlayDialog howToPlayDialog;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, SpaceCapsuleActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_space_capsule;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("空间站");
    }


    @OnClick({R.id.toolbar_back, R.id.tv_how_to_play,R.id.tv_space_obtain,R.id.tv_space_enter})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_how_to_play:   //玩法说明
                    howToPlayDialog = new HowToPlayDialog(this);
                    howToPlayDialog.show();
                    break;
                case R.id.tv_space_obtain:  //获取空间站
                    GetSpaceStationActivity.start(this,false);
                    break;
                case R.id.tv_space_enter:  //进入空间站
                    MySpaceStationActivity.start(this,false);
                    break;
            }
        }
    }
}
