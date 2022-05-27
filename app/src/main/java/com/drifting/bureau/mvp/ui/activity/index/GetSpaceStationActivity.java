package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerGetSpaceStationComponent;
import com.drifting.bureau.mvp.model.entity.BarrageEntity;
import com.drifting.bureau.mvp.ui.activity.pay.PaymentInfoActivity;
import com.drifting.bureau.mvp.ui.activity.user.BuildGuideActivity;
import com.drifting.bureau.mvp.ui.activity.user.MyBlindBoxActivity;
import com.drifting.bureau.mvp.ui.activity.web.ShowWebViewActivity;
import com.drifting.bureau.mvp.ui.adapter.SpaceBarrageAdapter;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.SpannableUtil;
import com.drifting.bureau.view.AutoPollRecyclerView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.GetSpaceStationContract;
import com.drifting.bureau.mvp.presenter.GetSpaceStationPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/24 12:15
 *
 * @author 获取空间站
 * module name is GetSpaceStationActivity
 */
public class GetSpaceStationActivity extends BaseActivity<GetSpaceStationPresenter> implements GetSpaceStationContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToobarTitle;
    @BindView(R.id.rcy_barrage)
    AutoPollRecyclerView mRcyBarrage;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;
    private SpaceBarrageAdapter spaceBarrageAdapter;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, GetSpaceStationActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGetSpaceStationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_get_space_station; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToobarTitle.setText("获取空间站");
        initListener();
    }

    public void initListener() {
        SpannableStringBuilder passer = SpannableUtil.getBuilder(this, "￥").setTextSize(12).append("999").setBold().setTextSize(17).build();
        mTvPrice.setText(passer);
        setUserComment();
        mRcyBarrage.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        spaceBarrageAdapter = new SpaceBarrageAdapter(new ArrayList<>());
        mRcyBarrage.setAdapter(spaceBarrageAdapter);
        spaceBarrageAdapter.setData(getData());
        mRcyBarrage.start();
    }


    /**
     * @description 给隐私设置颜色
     */
    public void setUserComment() {
        mTvProtocol.setOnLongClickListener(v -> true);
        mTvProtocol.setHighlightColor(getResources().getColor(android.R.color.transparent));
        mTvProtocol.setText("我已阅读并同意");
        mTvProtocol.append(buildPrivacySpannableString("《盲盒购买须知》"));
        mTvProtocol.setMovementMethod(LinkMovementMethod.getInstance());
    }


    /**
     * @description 用户隐私协议
     */
    private SpannableString buildPrivacySpannableString(String privacy) {
        SpannableString userSpannable = new SpannableString(privacy);
        userSpannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (!ClickUtil.isFastClick(widget.getId())) {
                    ShowWebViewActivity.start(GetSpaceStationActivity.this, false);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.white));
                ds.setUnderlineText(false);
            }
        }, 0, userSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return userSpannable;
    }


    public List<BarrageEntity> getData() {
        List<BarrageEntity> list = new ArrayList<>();
        list.add(new BarrageEntity(""));
        list.add(new BarrageEntity(""));
        list.add(new BarrageEntity("恭喜你的小太阳获得初级空间站"));
        list.add(new BarrageEntity("恭喜你的小太阳获得初级空间站"));
        list.add(new BarrageEntity("恭喜你的小太阳获得初级空间站"));
        list.add(new BarrageEntity("恭喜你的小太阳获得初级空间站"));
        return list;
    }


    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }


    @OnClick({R.id.toolbar_back,R.id.tv_buy,R.id.tv_buy_more,R.id.tv_my_blind_box})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_buy:
                    PaymentInfoActivity.start(this,false);
                    break;
                case R.id.tv_buy_more:
                    PaymentInfoActivity.start(this,false);
                    break;
                case R.id.tv_my_blind_box:  //我的盲盒
                    MyBlindBoxActivity.start(this,false);
                    break;
            }
        }
    }
}