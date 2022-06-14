package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.QuestionAssessEntity;
import com.drifting.bureau.util.SpannableUtil;
import com.drifting.bureau.view.radar.RadarItem;
import com.drifting.bureau.view.radar.RadarView;
import com.jess.arms.base.BaseDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AttributeResultsDialog extends BaseDialog {

    private RadarView radarView;
    private Context context;
    private List<RadarItem> radarItemList;
    private TextView username, planetname, desc;
    private QuestionAssessEntity entity;

    public AttributeResultsDialog(@NonNull Context context, QuestionAssessEntity entity) {
        super(context);
        this.context = context;
        this.entity = entity;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        radarView = findViewById(R.id.radarview);
        username = findViewById(R.id.tv_user_name);
        planetname = findViewById(R.id.tv_planet_name);
        desc = findViewById(R.id.tv_desc);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        username.setText("昵称：" + entity.getUser_name());
        SpannableStringBuilder passer = SpannableUtil.getBuilder(context, "适宜居住星球： ").append(entity.getPlanet().getName()).setBold().setTextSize(14).build();
        planetname.setText(passer);
        desc.setText(entity.getPlanet().getDesc());
        List<Integer> list = new LinkedList<>();
        list.add(entity.getE());
        list.add(entity.getI());
        list.add(entity.getS());
        list.add(entity.getN());
        list.add(entity.getT());
        list.add(entity.getF());
        list.add(entity.getJ());
        list.add(entity.getP());
        Integer max = Collections.max(list) + 2;
        radarItemList = new ArrayList<>();
        radarItemList.add(new RadarItem("火焰", entity.getE(), entity.getE() != 0 ? (float) entity.getE() / (float) max : 0));
        radarItemList.add(new RadarItem("土壤", entity.getI(), entity.getI() != 0 ? (float) entity.getI() / (float) max : 0));
        radarItemList.add(new RadarItem("能量", entity.getS(), entity.getS() != 0 ? (float) entity.getS() / (float) max : 0));
        radarItemList.add(new RadarItem("动物", entity.getN(), entity.getN() != 0 ? (float) entity.getN() / (float) max : 0));
        radarItemList.add(new RadarItem("天空", entity.getT(), entity.getT() != 0 ? (float) entity.getT() / (float) max : 0));
        radarItemList.add(new RadarItem("大海", entity.getF(), entity.getF() != 0 ? (float) entity.getF() / (float) max : 0));
        radarItemList.add(new RadarItem("风", entity.getJ(), entity.getJ() != 0 ? (float) entity.getJ() / (float) max : 0));
        radarItemList.add(new RadarItem("温度", entity.getP(), entity.getP() != 0 ? (float) entity.getP() / (float) max : 0));
        radarView.setRadarItemList(radarItemList);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_attribute_results;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }
}
