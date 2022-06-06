package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerRaftingDetailsComponent;
import com.drifting.bureau.mvp.model.entity.BarrageEntity;
import com.drifting.bureau.mvp.ui.adapter.RaftingDetailsAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.SpannableUtil;
import com.drifting.bureau.view.AutoPollRecyclerView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.RaftingDetailsContract;
import com.drifting.bureau.mvp.presenter.RaftingDetailsPresenter;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/19 13:21
 *
 * @author 查看漂流
 * module name is RaftingDetailsActivity
 */
public class RaftingDetailsActivity extends BaseActivity<RaftingDetailsPresenter> implements RaftingDetailsContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_barrage)
    AutoPollRecyclerView mRcyBarrage;
    @BindView(R.id.tv_passer)
    TextView mTvPasser;


    private RaftingDetailsAdapter raftingDetailsAdapter;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, RaftingDetailsActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRaftingDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_rafting_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("漂流详情");
        initListener();
    }

    public void initListener() {
        SpannableStringBuilder passer = SpannableUtil.getBuilder(this, "我是第 ").append("1852").setBold().setForegroundColor(R.color.color_6d).setTextSize(23).append(" 传递人").build();
        mTvPasser.setText(passer);
        mRcyBarrage.setLayoutManager(new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.HORIZONTAL));
        raftingDetailsAdapter = new RaftingDetailsAdapter(new ArrayList<>());
        mRcyBarrage.setAdapter(raftingDetailsAdapter);
        raftingDetailsAdapter.setData(getData());
        mRcyBarrage.start();
    }

    public List<BarrageEntity> getData() {
        List<BarrageEntity> list = new ArrayList<>();
        list.add(new BarrageEntity(""));
        list.add(new BarrageEntity("     "));
        list.add(new BarrageEntity(""));
        list.add(new BarrageEntity("          "));
        list.add(new BarrageEntity(" "));
        list.add(new BarrageEntity("      "));
        list.add(new BarrageEntity("     "));
        list.add(new BarrageEntity("你的小太阳"));
        list.add(new BarrageEntity("你太阳：故事写的真不错"));
        list.add(new BarrageEntity("太阳"));
        list.add(new BarrageEntity("你的小太阳：答应我，下次不要写了"));
        list.add(new BarrageEntity("你的小太阳：发了一段语音~~"));
        list.add(new BarrageEntity("你的太阳：骑扫把的仙"));
        list.add(new BarrageEntity("你的小太阳：lalalala，每天开开心"));
        list.add(new BarrageEntity("你小太阳：故事写的真不错"));
        list.add(new BarrageEntity("东东：你们能看到我嘛？能看到我嘛？"));
        list.add(new BarrageEntity("你的小太阳：你们能看到我嘛？能看到我嘛？"));
        list.add(new BarrageEntity("你的小太阳：lalalala"));
        list.add(new BarrageEntity("你的小太阳：分为"));
        list.add(new BarrageEntity("你的小太阳：愤愤愤愤"));
        list.add(new BarrageEntity("你的小太阳：和一塌糊涂然后"));
        list.add(new BarrageEntity("你的小太阳：而非威威分为氛围分为"));
        list.add(new BarrageEntity("东东：分为氛围分为发"));
        list.add(new BarrageEntity("你的小太阳：二位分为氛围分为氛围分为氛围分为氛围"));
        list.add(new BarrageEntity("你的小太阳：二位分为氛围分为氛围分为分为氛围分为发"));
        list.add(new BarrageEntity("最爱OP：f为分为氛围分为"));
        list.add(new BarrageEntity("你的小太阳：分为氛围分为"));
        list.add(new BarrageEntity("听说要十月革命：：f为分为氛围分为"));
        list.add(new BarrageEntity("你的小太阳：fewfewfew"));
        list.add(new BarrageEntity("最爱OP：分为氛围分为"));
        return list;
    }

    public Activity getActivity() {
        return this;
    }


    @OnClick({R.id.toolbar_back,R.id.tv_passer_num})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_passer_num:   //传递详情
                    DeliveryDetailsActivity.start(this,false);
                    break;
            }
        }
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}