package com.drifting.bureau.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.ui.activity.index.PlanetaryDetailActivity;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.animator.AnimatorUtil;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 星球分布
 */
public class PlanetaryDisFragment extends BaseFragment {
    @BindView(R.id.iv_planet1)
    ImageView mIvPlanet1;
    @BindView(R.id.iv_planet2)
    ImageView mIvPlanet2;
    @BindView(R.id.iv_planet3)
    ImageView mIvPlanet3;
    @BindView(R.id.iv_planet4)
    ImageView mIvPlanet4;
    @BindView(R.id.iv_planet5)
    ImageView mIvPlanet5;
    @BindView(R.id.iv_planet6)
    ImageView mIvPlanet6;
    @BindView(R.id.iv_planet7)
    ImageView mIvPlanet7;
    @BindView(R.id.iv_planet8)
    ImageView mIvPlanet8;
    @BindView(R.id.iv_planet9)
    ImageView mIvPlanet9;
    @BindView(R.id.iv_planet10)
    ImageView mIvPlanet10;
    @BindView(R.id.iv_planet11)
    ImageView mIvPlanet11;
    @BindView(R.id.iv_planet12)
    ImageView mIvPlanet12;
    @BindView(R.id.iv_planet13)
    ImageView mIvPlanet13;
    @BindView(R.id.iv_planet14)
    ImageView mIvPlanet14;
    @BindView(R.id.iv_planet15)
    ImageView mIvPlanet15;
    @BindView(R.id.iv_planet16)
    ImageView mIvPlanet16;
    @BindView(R.id.iv_planet17)
    ImageView mIvPlanet17;
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


    @OnClick({R.id.iv_planet1, R.id.iv_planet2, R.id.iv_planet3, R.id.iv_planet4, R.id.iv_planet5, R.id.iv_planet6, R.id.iv_planet7, R.id.iv_planet8, R.id.iv_planet9, R.id.iv_planet10, R.id.iv_planet11, R.id.iv_planet12, R.id.iv_planet13, R.id.iv_planet14, R.id.iv_planet15, R.id.iv_planet16, R.id.iv_planet17})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.iv_planet1:
                    startActivity(1);
                    break;
                case R.id.iv_planet2:
                    startActivity(9);
                    break;
                case R.id.iv_planet3:
                    startActivity(8);
                    break;
                case R.id.iv_planet4:
                    startActivity(3);
                    break;
                case R.id.iv_planet5:
                    startActivity(2);
                    break;
                case R.id.iv_planet6:
                    startActivity(6);
                    break;
                case R.id.iv_planet7:
                    startActivity(11);
                    break;
                case R.id.iv_planet8:
                    startActivity(17);
                    break;
                case R.id.iv_planet9:
                    startActivity(10);
                    break;
                case R.id.iv_planet10:
                    startActivity(12);
                    break;
                case R.id.iv_planet11:
                    startActivity(7);
                    break;
                case R.id.iv_planet12:
                    startActivity(4);
                    break;
                case R.id.iv_planet13:
                    startActivity(15);
                    break;
                case R.id.iv_planet14:
                    startActivity(13);
                    break;
                case R.id.iv_planet15:
                    startActivity(14);
                    break;
                case R.id.iv_planet16:
                    startActivity(5);
                    break;
                case R.id.iv_planet17:
                    startActivity(16);
                    break;
            }
        }
    }

    public void startActivity(int type) {
        PlanetaryDetailActivity.start(mContext, type, false);
    }

    public void statFloatAnim(View view) {
        AnimatorUtil.floatAnim(view, 1000, 6);
    }

    public void setTextDrawble(int postion) {
        switch (postion) {
            case 1:
                mIvPlanet1.setAlpha(1f);
                statFloatAnim(mIvPlanet1);
                break;
            case 2:
                mIvPlanet5.setAlpha(1f);
                statFloatAnim(mIvPlanet5);
                break;
            case 3:
                mIvPlanet4.setAlpha(1f);
                statFloatAnim(mIvPlanet4);
                break;
            case 4:
                mIvPlanet12.setAlpha(1f);
                statFloatAnim(mIvPlanet12);
                break;
            case 5:
                mIvPlanet16.setAlpha(1f);
                statFloatAnim(mIvPlanet16);
                break;
            case 6:
                mIvPlanet6.setAlpha(1f);
                statFloatAnim(mIvPlanet6);
                break;
            case 7:
                mIvPlanet11.setAlpha(1f);
                statFloatAnim(mIvPlanet11);
                break;
            case 8:
                mIvPlanet3.setAlpha(1f);
                statFloatAnim(mIvPlanet3);
                break;
            case 9:
                mIvPlanet2.setAlpha(1f);
                statFloatAnim(mIvPlanet2);
                break;
            case 10:
                mIvPlanet9.setAlpha(1f);
                statFloatAnim(mIvPlanet9);
                break;
            case 11:
                mIvPlanet7.setAlpha(1f);
                statFloatAnim(mIvPlanet7);
                break;
            case 12:
                mIvPlanet10.setAlpha(1f);
                statFloatAnim(mIvPlanet10);
                break;
            case 13:
                mIvPlanet14.setAlpha(1f);
                statFloatAnim(mIvPlanet14);
                break;
            case 14:
                mIvPlanet15.setAlpha(1f);
                statFloatAnim(mIvPlanet15);
                break;
            case 15:
                mIvPlanet13.setAlpha(1f);
                statFloatAnim(mIvPlanet13);
                break;
            case 16:
                mIvPlanet17.setAlpha(1f);
                statFloatAnim(mIvPlanet17);
                break;
            case 17:
                mIvPlanet8.setAlpha(1f);
                statFloatAnim(mIvPlanet8);
                break;
        }
    }

}
