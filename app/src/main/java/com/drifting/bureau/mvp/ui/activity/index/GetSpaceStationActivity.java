package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerGetSpaceStationComponent;
import com.drifting.bureau.mvp.model.entity.BarrageEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.PrizeEntity;
import com.drifting.bureau.mvp.model.entity.SpaceCheckEntity;
import com.drifting.bureau.mvp.model.entity.SpaceStationEntity;
import com.drifting.bureau.mvp.ui.activity.home.DiscoveryTourActivity;
import com.drifting.bureau.mvp.ui.activity.pay.PaymentInfoActivity;
import com.drifting.bureau.mvp.ui.activity.user.MyBlindBoxActivity;
import com.drifting.bureau.mvp.ui.activity.web.ShowWebViewActivity;
import com.drifting.bureau.mvp.ui.adapter.SpaceBarrageAdapter;
import com.drifting.bureau.mvp.ui.dialog.PrizepreviewDialog;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.SpannableUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.animator.AnimatorUtil;
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
    @BindView(R.id.iv_blind_box)
    ImageView mIvbLindBox;
    @BindView(R.id.iv_blind_box2)
    ImageView mIvbLindBox2;
    @BindView(R.id.iv_blind_box3)
    ImageView mIvbLindBox3;
    @BindView(R.id.rl_check)
    RelativeLayout mRlCheck;
    @BindView(R.id.ck_protocol)
    CheckBox mCkProtocol;
    @BindView(R.id.tv_blind_box_name)
    TextView mBlindBoxName;
    private SpaceBarrageAdapter spaceBarrageAdapter;
    private int index = 0;
    private GestureDetector gestureDetector;
    private SpaceGestureDetector spaceGestureDetector;
    //定义滑动的最小距离
    private static final int MIN_DISTANCE = 100;
    private List<SpaceStationEntity> list;
    private String skuCode;
    private SpannableStringBuilder passer;
    private PrizepreviewDialog prizepreviewDialog;

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
        //协议选中
        mCkProtocol.setOnCheckedChangeListener((compoundButton, ischecked) -> {
            mRlCheck.setVisibility(ischecked ? View.GONE : View.VISIBLE);
        });

        //实例化滑动监听
        spaceGestureDetector = new SpaceGestureDetector();
        //实例化GestureDetector并将UserGestureDetector实例传入
        gestureDetector = new GestureDetector(this, spaceGestureDetector);
        mIvbLindBox2.setAlpha(0.4f);
        mIvbLindBox3.setAlpha(0.4f);
        statFloatAnim();

        if (mPresenter != null) {
            mPresenter.getSpaceList();
        }

        setUserComment();
        mRcyBarrage.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        spaceBarrageAdapter = new SpaceBarrageAdapter(new ArrayList<>());
        mRcyBarrage.setAdapter(spaceBarrageAdapter);
        spaceBarrageAdapter.setData(getData());
        mRcyBarrage.start();
    }

    public void statFloatAnim() {
        AnimatorUtil.floatAnim(mIvbLindBox, 2000);
        new Handler().postDelayed(() -> {
            AnimatorUtil.floatAnim(mIvbLindBox2, 2000);
            AnimatorUtil.floatAnim(mIvbLindBox3, 2000);
        }, 500);
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
        list.add(new BarrageEntity("             "));
        list.add(new BarrageEntity("恭喜芭芘零食屋获得MED空间站"));
        list.add(new BarrageEntity("恭喜唇边回味奶茶浓香获得SUP空间站"));
        list.add(new BarrageEntity("恭喜无所谓的记忆获得TN空间站"));
        list.add(new BarrageEntity("恭喜陽咣丅啲憂喐获得HIGH空间站"));
        list.add(new BarrageEntity("恭喜数学不好有错么获得TN空间站"));
        list.add(new BarrageEntity("恭喜嘴角残留的余香获得SUP空间站"));
        list.add(new BarrageEntity("恭喜我就说是巧克力获得TN空间站"));
        list.add(new BarrageEntity("恭喜纯净的眸子获得HIGH空间站"));
        return list;
    }


    @Override
    public void onGetSpaceList(List<SpaceStationEntity> data) {
        list = data;
        setFrame();
    }


    @Override
    public void onNetError() {

    }

    public void setFrame() {
        if (list != null) {
            if (index < 0) {
                index = list.size() - 1;
            }
            if (index >= list.size()) {
                index = 0;
            }
            for (int i = 0; i < 5; i++) {
                int row = 0;
                if (i == 0) {
                    row = index - 1;
                    if (row < 0) {
                        row = list.size() - 1;
                    }
                } else {
                    row = index + i - 1;
                    if (row >= list.size()) {
                        row = row - list.size();
                    }
                }
                switch (i) {
                    case 0:
                        mIvbLindBox3.setImageResource(R.drawable.space_blind_box);
                        break;
                    case 1:
                        mIvbLindBox.setImageResource(R.drawable.space_blind_box);
                        passer= SpannableUtil.getBuilder(this, "￥").setTextSize(12).append(list.get(row).getPrice()+"").setBold().setTextSize(17).build();
                        mTvPrice.setText(passer);
                        mBlindBoxName.setText(list.get(row).getSku_name());
                        skuCode = list.get(row).getSku_code();
                        break;
                    case 2:
                        mIvbLindBox2.setImageResource(R.drawable.space_blind_box);
                        break;
                }
            }
        }
    }


    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }


    @OnClick({R.id.toolbar_back, R.id.tv_buy, R.id.tv_buy_more, R.id.tv_my_blind_box, R.id.iv_blind_box2, R.id.iv_blind_box3,R.id.tv_prize_preview})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_buy:
                    if (list!=null){
                        createOrder(skuCode, "1");
                    }

                    break;
                case R.id.tv_buy_more:
                    createOrder(skuCode, "10");
                    break;
                case R.id.tv_my_blind_box:  //我的盲盒
                    MyBlindBoxActivity.start(this, false);
                    break;
                case R.id.iv_blind_box2:
                    index--;
                    setFrame();
                    break;
                case R.id.iv_blind_box3:
                    index++;
                    setFrame();
                    break;
                case R.id.tv_prize_preview:  //奖品预览
                    if (mPresenter!=null){
                        mPresenter.getAwardList();
                    }
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
        PaymentInfoActivity.start(this,2, entity.getSn(), entity.getTotal_amount(),false);
    }
    @Override
    public void onSpaceCheck(SpaceCheckEntity entity) {

    }

    @Override
    public void onAwardPreviewSuccess(List<PrizeEntity> list) {
        if (list!=null &&list.size()>0){
            prizepreviewDialog=new PrizepreviewDialog(this,list);
            prizepreviewDialog.show();
        }

    }

    /**
     * 重写onTouchEvent返回一个gestureDetector的屏幕触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * 自定义SpaceGestureDetector类继承SimpleOnGestureListener
     */
    class SpaceGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > MIN_DISTANCE || e2.getY() - e1.getY() > MIN_DISTANCE) { //下滑或者左滑动
                index--;
                setFrame();
            } else { //上滑或者右滑
                index++;
                setFrame();
            }
            return true;
        }
    }
}