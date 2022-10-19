package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
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
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerGetSpaceStationComponent;
import com.drifting.bureau.mvp.contract.GetSpaceStationContract;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.MysteryboxEntity;
import com.drifting.bureau.mvp.model.entity.PrizeEntity;
import com.drifting.bureau.mvp.model.entity.SpaceAboutEntity;
import com.drifting.bureau.mvp.model.entity.SpaceCheckEntity;
import com.drifting.bureau.mvp.model.entity.SpaceStationEntity;
import com.drifting.bureau.mvp.presenter.GetSpaceStationPresenter;
import com.drifting.bureau.mvp.ui.activity.pay.PaymentInfoActivity;
import com.drifting.bureau.mvp.ui.activity.user.MyBlindBoxActivity;
import com.drifting.bureau.mvp.ui.activity.web.ShowWebViewActivity;
import com.drifting.bureau.mvp.ui.adapter.SpaceBarrageAdapter;
import com.drifting.bureau.mvp.ui.dialog.PrizepreviewDialog;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.SpannableUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.view.AutoPollRecyclerView;
import com.drifting.bureau.view.ScaleInTransformer;
import com.drifting.bureau.base.BaseManagerActivity;
import com.jess.arms.di.component.AppComponent;

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
public class GetSpaceStationActivity extends BaseManagerActivity<GetSpaceStationPresenter> implements GetSpaceStationContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToobarTitle;
    @BindView(R.id.rcy_barrage)
    AutoPollRecyclerView mRcyBarrage;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;
    @BindView(R.id.rl_check)
    RelativeLayout mRlCheck;
    @BindView(R.id.ck_protocol)
    CheckBox mCkProtocol;
    @BindView(R.id.tv_blind_box_name)
    TextView mBlindBoxName;
    @BindView(R.id.vp_img)
    ViewPager mViewPager;
    @BindView(R.id.frame)
    FrameLayout frame;
    private SpaceBarrageAdapter spaceBarrageAdapter;

    private List<SpaceStationEntity> list;

    private String skuCode;
    private SpannableStringBuilder passer;
    private PrizepreviewDialog prizepreviewDialog;
    private int limit = 50;

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
        setUserComment();
        //协议选中
        mCkProtocol.setOnCheckedChangeListener((compoundButton, ischecked) -> {
            mRlCheck.setVisibility(ischecked ? View.INVISIBLE : View.VISIBLE);
        });
        mRcyBarrage.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        spaceBarrageAdapter = new SpaceBarrageAdapter(new ArrayList<>());
        if (mPresenter != null) {
            mPresenter.getSpaceList();
            mPresenter.mysterybox(limit);
        }

        frame.setOnTouchListener((view, motionEvent) -> mViewPager.onTouchEvent(motionEvent));
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
                    ShowWebViewActivity.start(GetSpaceStationActivity.this, 3, false);
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


    @Override
    public void onGetMysterybox(MysteryboxEntity entity) {
        if (entity != null && entity.getList().size() > 0) {
            List<MysteryboxEntity.ListBean> list = entity.getList();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 2; i++) {
                sb.append("\u3000");
                MysteryboxEntity.ListBean map = new MysteryboxEntity.ListBean();
                map.setEvent(sb.toString());
                //向List最前面插入元素
                list.add(0, map);
            }
            mRcyBarrage.setAdapter(spaceBarrageAdapter);
            spaceBarrageAdapter.setData(list);
            mRcyBarrage.start();
        } else {
            mRcyBarrage.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onGetSpaceList(List<SpaceStationEntity> data) {
        list = data;
        if (list.size() == 1) {
            list.add(list.get(0));
        }
        setText(0);
        MyAdapter myAdapter = new MyAdapter();
        mViewPager.setOffscreenPageLimit(list.size());
        mViewPager.setClipChildren(false);
        mViewPager.setAdapter(myAdapter);
        mViewPager.setCurrentItem(list.size() * 100);
        mViewPager.setPageTransformer(true, new ScaleInTransformer());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setText(position % list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void setText(int postion) {
        passer = SpannableUtil.getBuilder(this, "￥").setTextSize(12).append(list.get(postion).getPrice() + "").setBold().setTextSize(17).build();
        mTvPrice.setText(passer);
        mBlindBoxName.setText(list.get(postion).getSku_name());
        skuCode = list.get(postion).getSku_code();
    }


    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView iv = new ImageView(getApplication());
            GlideUtil.create().loadLongImage(GetSpaceStationActivity.this, list.get(position % list.size()).getSmall_image(), iv);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }


    @Override
    public void onNetError() {

    }


    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }


    @OnClick({R.id.toolbar_back, R.id.tv_buy, R.id.tv_buy_more, R.id.iv_my_blind_box, R.id.iv_prize_preview})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_buy:
                    if (list != null) {
                        createOrder(skuCode, "1");
                    }
                    break;
                case R.id.tv_buy_more:
                    createOrder(skuCode, "10");
                    break;
                case R.id.iv_prize_preview:   //奖品预览
                    if (mPresenter != null) {
                        mPresenter.getAwardList();
                    }
                    break;
                case R.id.iv_my_blind_box: //我的盲盒
                    MyBlindBoxActivity.start(this, false);
                    break;
            }
        }
    }

    public void createOrder(String sku_code, String sku_num) {
        if (!StringUtil.isEmpty(StringUtil.checkProtocol(mCkProtocol))) {
            mRlCheck.setVisibility(View.VISIBLE);
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.anim_jitter);
            mRlCheck.startAnimation(shake);
            return;
        }
        if (mPresenter != null) {
            mPresenter.createOrderSpace(sku_code, sku_num);
        }
    }

    @Override
    public void onCreateOrderSpaceSuccess(CreateOrderEntity entity) {
        PaymentInfoActivity.start(this, 5, entity.getSn(), entity.getTotal_amount(), false);
    }

    @Override
    public void onSpaceCheck(SpaceCheckEntity entity) {

    }

    @Override
    public void onAwardPreviewSuccess(List<PrizeEntity> list) {
        if (list != null && list.size() > 0) {
            prizepreviewDialog = new PrizepreviewDialog(this, list);
            prizepreviewDialog.show();
        }

    }

    @Override
    public void onSpaceAbout(List<SpaceAboutEntity> list) {

    }


}