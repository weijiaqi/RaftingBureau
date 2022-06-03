package com.drifting.bureau.mvp.ui.activity.index;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerTeaShopComponent;
import com.drifting.bureau.mvp.model.entity.TeaShopEntity;
import com.drifting.bureau.mvp.ui.adapter.TeaShopAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.TeaShopContract;
import com.drifting.bureau.mvp.presenter.TeaShopPresenter;
import com.rb.core.xrecycleview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/30 09:46
 * @author 附近门店
 * module name is TeaShopActivity
 */
public class TeaShopActivity extends BaseActivity<TeaShopPresenter> implements TeaShopContract.View,XRecyclerView.LoadingListener {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_public)
    XRecyclerView mRcyPublic;
    private TeaShopAdapter teaShopAdapter;
    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, TeaShopActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTeaShopComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState){
        return R.layout.activity_tea_shop; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("附近门店");
        initListener();
    }

    public void initListener() {
        mRcyPublic.setLayoutManager(new LinearLayoutManager(this));
        teaShopAdapter=new TeaShopAdapter(new ArrayList<>());
        mRcyPublic.setAdapter(teaShopAdapter);
        mRcyPublic.setLoadingListener(this);
        teaShopAdapter.setData(getData());
    }

    public List<TeaShopEntity> getData(){
        List<TeaShopEntity> list=new ArrayList<>();
        list.add(new TeaShopEntity("北京丰台茶饮店","北京市丰台区金泽西路8号院","15.2km",1));
        list.add(new TeaShopEntity("北京石景山茶饮店","北京市石景山区石景山路2号(地铁玉泉路A出口西侧)","25km",1));
        list.add(new TeaShopEntity("北京通州奶茶店","北京市通州区梨园镇东六环与G1京哈高速交叉口西北角","32km",1));
        list.add(new TeaShopEntity("北京海淀奶茶店","北京市海淀区新建宫门路19号","12km",2));
        return list;
    }

    @OnClick({R.id.toolbar_back,R.id.ll_location})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.ll_location: //城市选择
                    CitySelectionActivity.start(this,false);
                    break;
            }
        }
    }


    public Activity getActivity(){
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void onRefresh() {
        mRcyPublic.refreshEndComplete();
    }

    @Override
    public void onLoadMore() {
        mRcyPublic.loadEndLine();
    }
}