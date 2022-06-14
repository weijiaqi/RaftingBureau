package com.drifting.bureau.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.ui.activity.index.MoveAwayPlanetaryActivity;
import com.drifting.bureau.mvp.ui.activity.index.PlanetaryDetailActivity;
import com.drifting.bureau.util.ClickUtil;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 星球分布
 */
public class PlanetaryDisFragment extends BaseFragment {

    @BindView(R.id.tv_planet1)
    ShapeTextView mTvPlanet1;
    @BindView(R.id.tv_planet2)
    ShapeTextView mTvPlanet2;
    @BindView(R.id.tv_planet3)
    ShapeTextView mTvPlanet3;
    @BindView(R.id.tv_planet4)
    ShapeTextView mTvPlanet4;
    @BindView(R.id.tv_planet5)
    ShapeTextView mTvPlanet5;
    @BindView(R.id.tv_planet6)
    ShapeTextView mTvPlanet6;
    @BindView(R.id.tv_planet7)
    ShapeTextView mTvPlanet7;
    @BindView(R.id.tv_planet8)
    ShapeTextView mTvPlanet8;
    @BindView(R.id.tv_planet9)
    ShapeTextView mTvPlanet9;
    @BindView(R.id.tv_planet10)
    ShapeTextView mTvPlanet10;
    @BindView(R.id.tv_planet11)
    ShapeTextView mTvPlanet11;
    @BindView(R.id.tv_planet12)
    ShapeTextView mTvPlanet12;
    @BindView(R.id.tv_planet13)
    ShapeTextView mTvPlanet13;
    @BindView(R.id.tv_planet14)
    ShapeTextView mTvPlanet14;
    @BindView(R.id.tv_planet15)
    ShapeTextView mTvPlanet15;
    @BindView(R.id.tv_planet16)
    ShapeTextView mTvPlanet16;
    @BindView(R.id.tv_planet17)
    ShapeTextView mTvPlanet17;
    @BindView(R.id.iv_location1)
    ImageView mIvLocation1;
    @BindView(R.id.iv_location2)
    ImageView mIvLocation2;
    @BindView(R.id.iv_location3)
    ImageView mIvLocation3;
    @BindView(R.id.iv_location4)
    ImageView mIvLocation4;
    @BindView(R.id.iv_location5)
    ImageView mIvLocation5;
    @BindView(R.id.iv_location6)
    ImageView mIvLocation6;
    @BindView(R.id.iv_location7)
    ImageView mIvLocation7;
    @BindView(R.id.iv_location8)
    ImageView mIvLocation8;
    @BindView(R.id.iv_location9)
    ImageView mIvLocation9;
    @BindView(R.id.iv_location10)
    ImageView mIvLocation10;
    @BindView(R.id.iv_location11)
    ImageView mIvLocation11;
    @BindView(R.id.iv_location12)
    ImageView mIvLocation12;
    @BindView(R.id.iv_location13)
    ImageView mIvLocation13;
    @BindView(R.id.iv_location14)
    ImageView mIvLocation14;
    @BindView(R.id.iv_location15)
    ImageView mIvLocation15;
    @BindView(R.id.iv_location16)
    ImageView mIvLocation16;
    @BindView(R.id.iv_location17)
    ImageView mIvLocation17;
    private static final String BUNDLE_TYPE = "bundle_type";
    private int type;

    public static PlanetaryDisFragment newInstance(int type) {
        PlanetaryDisFragment fragment = new PlanetaryDisFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        type = (args != null) ? args.getInt(BUNDLE_TYPE) : 1;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_planetary_dis, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTextDrawble(type);
    }

    @Override
    public void setData(@Nullable Object data) {

    }


    @OnClick({R.id.ll_planet1, R.id.ll_planet2, R.id.ll_planet3, R.id.ll_planet4,R.id.ll_planet5, R.id.ll_planet6, R.id.ll_planet7, R.id.ll_planet8, R.id.ll_planet9, R.id.ll_planet10,R.id.ll_planet11, R.id.ll_planet12, R.id.ll_planet13, R.id.ll_planet14, R.id.ll_planet15, R.id.ll_planet16, R.id.ll_planet17})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.ll_planet1:
                    startActivity(1);
                    break;
                case R.id.ll_planet2:
                    startActivity(3);
                    break;
                case R.id.ll_planet3:
                    startActivity(2);
                    break;
                case R.id.ll_planet4:
                    startActivity(17);
                    break;
                case R.id.ll_planet5:
                    startActivity(4);
                    break;
                case R.id.ll_planet6:
                    startActivity(5);
                    break;
                case R.id.ll_planet7:
                    startActivity(6);
                    break;
                case R.id.ll_planet8:
                    startActivity(7);
                    break;
                case R.id.ll_planet9:
                    startActivity(8);
                    break;
                case R.id.ll_planet10:
                    startActivity(9);
                    break;
                case R.id.ll_planet11:
                    startActivity(10);
                    break;
                case R.id.ll_planet12:
                    startActivity(11);
                    break;
                case R.id.ll_planet13:
                    startActivity(16);
                    break;
                case R.id.ll_planet14:
                    startActivity(15);
                    break;
                case R.id.ll_planet15:
                    startActivity(14);
                    break;
                case R.id.ll_planet16:
                    startActivity(13);
                    break;
                case R.id.ll_planet17:
                    startActivity(12);
                    break;
            }
        }
    }

    public void startActivity(int type) {
        PlanetaryDetailActivity.start(mContext, type, false);
    }


    public void setTextDrawble(int postion) {

        switch (postion) {
            case 1:
                mIvLocation1.setVisibility(View.VISIBLE);
                mTvPlanet1.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                break;
            case 2:
                mIvLocation3.setVisibility(View.VISIBLE);
                mTvPlanet3.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();

                break;
            case 3:
                mIvLocation2.setVisibility(View.VISIBLE);
                mTvPlanet2.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                break;
            case 4:
                mIvLocation5.setVisibility(View.VISIBLE);
                mTvPlanet5.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                break;
            case 5:
                mIvLocation6.setVisibility(View.VISIBLE);
                mTvPlanet6.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                 break;
            case 6:
                mIvLocation7.setVisibility(View.VISIBLE);
                mTvPlanet7.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                break;
            case 7:
                mIvLocation8.setVisibility(View.VISIBLE);
                mTvPlanet8.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                break;
            case 8:
                mIvLocation9.setVisibility(View.VISIBLE);
                mTvPlanet9.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                break;
            case 9:
                mIvLocation10.setVisibility(View.VISIBLE);
                mTvPlanet10.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                break;
            case 10:
                mIvLocation11.setVisibility(View.VISIBLE);
                mTvPlanet11.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                break;
            case 11:
                mIvLocation12.setVisibility(View.VISIBLE);
                mTvPlanet12.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                break;
            case 12:
                mIvLocation17.setVisibility(View.VISIBLE);
                mTvPlanet17.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                break;
            case 13:
                mIvLocation16.setVisibility(View.VISIBLE);
                mTvPlanet16.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                break;
            case 14:
                mIvLocation15.setVisibility(View.VISIBLE);
                mTvPlanet15.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                break;
            case 15:
                mIvLocation14.setVisibility(View.VISIBLE);
                mTvPlanet14.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                break;
            case 16:
                mIvLocation13.setVisibility(View.VISIBLE);
                mTvPlanet13.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                break;
            case 17:
                mIvLocation4.setVisibility(View.VISIBLE);
                mTvPlanet4.getTextColorBuilder().setTextGradientColors(mContext. getColor(R.color.color_6c), mContext. getColor(R.color.color_6d)).intoTextColor();
                break;
        }
    }
}
