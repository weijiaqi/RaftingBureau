package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerPlanetaryDetailComponent;
import com.drifting.bureau.mvp.model.entity.PlanetaryDetailEntity;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.PlanetaryDetailContract;
import com.drifting.bureau.mvp.presenter.PlanetaryDetailPresenter;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/01 14:50
 *
 * @author 星球详情
 * module name is PlanetaryDetailActivity
 */
public class PlanetaryDetailActivity extends BaseActivity<PlanetaryDetailPresenter> implements PlanetaryDetailContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_planet)
    ImageView mIvPlanet;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_attribute)
    TextView mTvAttribute;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    private static String EXTRA_TYPE = "extra_type";

    private int type;

    public static void start(Context context, int type, boolean closePage) {
        Intent intent = new Intent(context, PlanetaryDetailActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPlanetaryDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_planetary_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("星球详情");
        if (getIntent() != null) {
            type = getIntent().getIntExtra(EXTRA_TYPE, 0);
        }

        if (mPresenter != null) {
            mPresenter.planetdetails(type);
        }


        initListener();
    }

    public void initListener() {
        switch (type) {
            case 1:
                mIvPlanet.setImageResource(R.drawable.planetary_detail01);
                mTvName.setText("荒芜星球");
                mTvDesc.setText("元宇宙基础星球，任何进入元宇宙漂流局的用户都会短暂居住在这里，这里不含有任何基础元素");
                mTvAttribute.setText("暂无属性");
                break;
            case 2:
                mIvPlanet.setImageResource(R.drawable.planetary_detail02);
                mTvName.setText("神农星球");
                mTvDesc.setText("神农星球⼈是严肃的、有责任⼼的和通情达理的社会坚定分⼦。他们值得信赖，他们重视承诺，对他们来说，⾔语就是庄严的宣誓");
                mTvAttribute.setText("土壤、能量、天空、风");
                break;
            case 3:
                mIvPlanet.setImageResource(R.drawable.planetary_detail03);
                mTvName.setText("女娲星球");
                mTvDesc.setText("女娲星球⼈忠诚、有奉献精神和同情⼼，理解别⼈的感受。他们意志清醒⽽有责任⼼，乐于为⼈所需。");
                mTvAttribute.setText("土壤、能量、大海、风");
                break;
            case 4:
                mIvPlanet.setImageResource(R.drawable.planetary_detail04);
                mIvPlanet.setImageResource(R.drawable.planetary_detail03);
                mTvName.setText("炎帝星球");
                mTvDesc.setText("炎帝星球⼈热爱⼈类，他们认为⼈的感情是最重要的。⽽且他们很⾃然地关⼼别⼈，以热情的态度对待⽣命，感受与个⼈相关的所有事物。");
                mTvAttribute.setText("火焰、动物、大海、风");
                break;
            case 5:
                mIvPlanet.setImageResource(R.drawable.planetary_detail05);
                mTvName.setText("盘古星球");
                mTvDesc.setText("盘古星球⼈⽣活在思想的世界⾥。他们是独⽴的、有独创性的思想家，具有强烈的感情、坚定的原则和正直的⼈性。");
                mTvAttribute.setText("土壤、动物、大海、风");
                break;
            case 6:
                mIvPlanet.setImageResource(R.drawable.planetary_detail06);
                mTvName.setText("仓颉星球");
                mTvDesc.setText("仓颉星球⼈是完美主义者。他们强烈地要求个⼈⾃由和能⼒，同时在他们独创的思想中，不可动摇的信仰促使他们达到⽬标。");
                mTvAttribute.setText("土壤、动物、天空、风");
                break;
            case 7:
                mIvPlanet.setImageResource(R.drawable.planetary_detail07);
                mTvName.setText("姮娥星球");
                mTvDesc.setText("姮娥星球⼈乐意与⼈相处，有⼀种真正的⽣活热情。他们顽⽪活泼，通过真诚和玩笑使别⼈感到事情更加有趣。");
                mTvAttribute.setText("火焰、能量、大海、温度");
                break;
            case 8:
                mIvPlanet.setImageResource(R.drawable.planetary_detail08);
                mTvName.setText("夸父星球");
                mTvDesc.setText("夸父星球⼈不会焦虑，因为他们是快乐的。夸父星球人⼈活跃、随遇⽽安、天真率直。他们乐于享受现在的⼀切⽽不是为将来计划什么。");
                mTvAttribute.setText("火焰、能量、天空、温度");
                break;
            case 9:
                mIvPlanet.setImageResource(R.drawable.planetary_detail09);
                mTvName.setText("嫘祖星球");
                mTvDesc.setText("嫘祖星球⼈是解决理性问题者。他们很有才智和条理性，以及创造才华的突出表现。");
                mTvAttribute.setText("土壤、动物、天空、温度");
                break;
            case 10:
                mIvPlanet.setImageResource(R.drawable.planetary_detail10);
                mTvName.setText("玄女星球");
                mTvDesc.setText("玄女星球人把内在的和谐视为⾼于其他⼀切。他们敏感、理想化、忠诚，对于个⼈价值具有⼀种强烈的荣誉感。");
                mTvAttribute.setText("土壤、动物、大海、温度");
                break;
            case 11:
                mIvPlanet.setImageResource(R.drawable.planetary_detail11);
                mTvName.setText("伏羲星球");
                mTvDesc.setText("伏羲星球⼈平和、敏感，他们保持着许多强烈的个⼈理想和⾃⼰的价值观念。他们更多地是通过⾏为⽽不是⾔辞表达⾃⼰深沉的情感。");
                mTvAttribute.setText("土壤、能量、大海、温度");
                break;
            case 12:
                mIvPlanet.setImageResource(R.drawable.planetary_detail12);
                mTvName.setText("后裔星球");
                mTvDesc.setText("后裔星球⼈坦率、诚实、讲求实效，他们喜欢⾏动⽽⾮漫谈。他们很谦逊，对于完成⼯作的⽅法有很好的理解⼒。");
                mTvAttribute.setText("土壤、能量、天空、温度");
                break;
            case 13:
                mIvPlanet.setImageResource(R.drawable.planetary_detail13);
                mTvName.setText("燧人星球");
                mTvDesc.setText("遂人星球⼈充满热情和新思想。他们乐观、⾃然、富有创造性和⾃信，具有独创性的思想和对可能性的强烈感受。");
                mTvAttribute.setText("火焰、动物、大海、温度");
                break;
            case 14:
                mIvPlanet.setImageResource(R.drawable.planetary_detail14);
                mTvName.setText("黄帝星球");
                mTvDesc.setText("黄帝星球⼈喜欢兴奋与挑战。他们热情开放、⾜智多谋、健谈⽽聪明，擅长于许多事情，不断追求增加能⼒和个⼈权⼒。");
                mTvAttribute.setText("火焰、动物、天空、温度");
                break;
            case 15:
                mIvPlanet.setImageResource(R.drawable.planetary_detail15);
                mTvName.setText("大禹星球");
                mTvDesc.setText("大禹星球人能⾼效率地⼯作，⾃我负责，监督他⼈⼯作，合理分配和处置资源，主次分明，井井有条；能制定和遵守规则");
                mTvAttribute.setText("火焰、能量、天空、风");
                break;
            case 16:
                mIvPlanet.setImageResource(R.drawable.planetary_detail16);
                mTvName.setText("吕尚星球");
                mTvDesc.setText("吕尚星球⼈通过直接的⾏动和合作积极地以真实、实际的⽅法帮助别⼈。他们友好、富有同情⼼和责任感。");
                mTvAttribute.setText("火焰、能量、大海、风");
                break;

        }
    }

    @Override
    public void onPlanetaryDetailSuccess(PlanetaryDetailEntity entity) {
        if (entity != null) {
            mTvNum.setText(entity.getPeople()+"");
        }
    }

    @Override
    public void onNetError() {

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
        ToastUtil.showToast(message);
    }
}