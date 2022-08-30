package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.data.event.TagEvent;
import com.drifting.bureau.mvp.model.entity.TopicTagsEntity;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.callback.BaseDataCallBack;
import com.drifting.bureau.util.request.RequestUtil;
import com.drifting.bureau.view.flowlayout.FlowLayout;
import com.drifting.bureau.view.flowlayout.TagAdapter;
import com.drifting.bureau.view.flowlayout.TagFlowLayout;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 卫佳琪1
 * @description 添加话题
 * @time 16:28 16:28
 */

public class AddTopicActivity extends BaseManagerActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.scrap_layout)
    TagFlowLayout mScrapLayout;

    private List<TopicTagsEntity> topicTagsEntityList;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, AddTopicActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_topic;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("添加话题标签");

        RequestUtil.create().tags(entity -> {
            if (entity != null && entity.getCode() == 200) {
                topicTagsEntityList=entity.getData();
                mScrapLayout.setAdapter(new TagAdapter<TopicTagsEntity>(topicTagsEntityList) {
                    @Override
                    public View getView(FlowLayout parent, int position, TopicTagsEntity entity1) {
                        TextView tv = (TextView) LayoutInflater.from(AddTopicActivity.this).inflate(R.layout.layout_for_scrap,
                                mScrapLayout, false);
                        tv.setText(entity1.getTag_name());
                        return tv;
                    }
                });

            }
        });


        mScrapLayout.setOnTagClickListener((view, position, parent) -> {
            if (topicTagsEntityList.size() > 0) {
                TagEvent tagEvent=new TagEvent();
                tagEvent.setTag(topicTagsEntityList.get(position).getTag());
                tagEvent.setTagname(topicTagsEntityList.get(position).getTag_name());
                EventBus.getDefault().post(tagEvent);
                finish();
            }
            return true;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.create().disDispose();
    }
}
