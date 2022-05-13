package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.LogInOutDataUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.OnClick;

public class SetUpActivity  extends BaseActivity {

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, SetUpActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_set_up;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.tv_exit})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_exit:
                    LogInOutDataUtil.successOutClearData();
                    BuildGuideActivity.start(this,true);
                    break;
            }
        }
    }
}
