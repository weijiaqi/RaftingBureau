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
public class TeaShopActivity extends BaseActivity<TeaShopPresenter> implements TeaShopContract.View {
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
        teaShopAdapter.setData(getData());
    }

    public List<TeaShopEntity> getData(){
        List<TeaShopEntity> list=new ArrayList<>();
        list.add(new TeaShopEntity("北京大兴超级好喝茶饮店"));
        list.add(new TeaShopEntity("北京大兴超级好喝茶饮店"));
        list.add(new TeaShopEntity("北京大兴超级好喝茶饮店"));
        list.add(new TeaShopEntity("北京大兴超级好喝茶饮店"));
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
}