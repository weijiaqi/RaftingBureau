package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/10/13 09:57
 *
 * @author 规则说明
 * module name is RulesDescriptionActivity
 */
public class RulesDescriptionActivity extends BaseManagerActivity {

    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, RulesDescriptionActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_rules_description; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        setStatusBarHeight(mTvBar);
        initListener();
    }

    public void initListener() {
        RequestUtil.create().previewBox(entity -> {
            if (entity != null && entity.getCode() == 200) {
                GlideUtil.create().loadLongImage(RulesDescriptionActivity.this,entity.getData().getImage2(),mIvPic);
            }
        });
    }


    @OnClick({R.id.toolbar_back})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
            }
        }
    }

    public Activity getActivity() {
        return this;
    }

}