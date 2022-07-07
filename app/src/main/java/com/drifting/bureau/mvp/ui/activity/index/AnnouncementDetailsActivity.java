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
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 卫佳琪1
 * @description 公告详情
 * @time 16:40 16:40
 */
public class AnnouncementDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_time)
    TextView mTvTime;

    private static String EXTRA_TITLE = "extra_title";
    private static String EXTRA_CONTENT = "extra_content";
    private static String EXTRA_TIME = "extra_time";

    private String title, content, time;

    public static void start(Context context, String title, String time, String content, boolean closePage) {
        Intent intent = new Intent(context, AnnouncementDetailsActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_CONTENT, content);
        intent.putExtra(EXTRA_TIME, time);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_announcement_details;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        if (getIntent() != null) {
            title = getIntent().getExtras().getString(EXTRA_TITLE);
            content = getIntent().getExtras().getString(EXTRA_CONTENT);
            time = getIntent().getExtras().getString(EXTRA_TIME);
        }

        mTvTitle.setText(title);
        mTvContent.setText(content);
        mTvTime.setText(time);
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

}
