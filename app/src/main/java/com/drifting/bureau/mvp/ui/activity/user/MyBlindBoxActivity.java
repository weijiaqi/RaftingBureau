package com.drifting.bureau.mvp.ui.activity.user;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerMyBlindBoxComponent;
import com.drifting.bureau.mvp.model.entity.MyBlindBoxEntity;
import com.drifting.bureau.mvp.ui.adapter.MyBlindBoxAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.MyBlindBoxContract;
import com.drifting.bureau.mvp.presenter.MyBlindBoxPresenter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnMultiListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/24 14:44
 *
 * @author 谢况
 * module name is MyBlindBoxActivity
 */
public class MyBlindBoxActivity extends BaseActivity<MyBlindBoxPresenter> implements MyBlindBoxContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
//    @BindView(R.id.refreshLayout)
//    RefreshLayout refreshLayout;
    @BindView(R.id.rcy_public)
    RecyclerView mRcyPublic;
    private MyBlindBoxAdapter myBlindBoxAdapter;
    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, MyBlindBoxActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyBlindBoxComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_refresh_layout; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("我的盲盒");
        initListener();
    }

    public void initListener() {

        mRcyPublic.setLayoutManager(new LinearLayoutManager(this));
        myBlindBoxAdapter=new MyBlindBoxAdapter(new ArrayList<>());
        mRcyPublic.setAdapter(myBlindBoxAdapter);
        myBlindBoxAdapter.setData(getData());
//        refreshLayout.setOnRefreshListener(refreshlayout -> {
//            refreshLayout.getLayout().postDelayed(() -> {
//                refreshLayout.finishRefresh();
//                refreshLayout.resetNoMoreData();//setNoMoreData(false);//恢复上拉状态
//            }, 2000);
//        });
//        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
//            refreshLayout.getLayout().postDelayed(() -> {
//                refreshLayout.finishLoadMore();
//            }, 1000);

//        });
    }

    public List<MyBlindBoxEntity> getData(){
        List<MyBlindBoxEntity> list=new ArrayList<>();
        list.add(new MyBlindBoxEntity("初级空间站必中盲盒"));
        list.add(new MyBlindBoxEntity("初级空间站必中盲盒"));
        list.add(new MyBlindBoxEntity("初级空间站必中盲盒"));
        list.add(new MyBlindBoxEntity("初级空间站必中盲盒"));
        return list;
    }

    public Activity getActivity() {
        return this;
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
    public void showMessage(@NonNull String message) {

    }

}