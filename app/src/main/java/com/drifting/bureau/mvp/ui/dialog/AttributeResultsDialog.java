package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.view.radar.RadarItem;
import com.drifting.bureau.view.radar.RadarView;
import com.jess.arms.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;

public class AttributeResultsDialog extends BaseDialog {

    private RadarView radarView;
   private Context context;

   private List<RadarItem> radarItemList;;

    public AttributeResultsDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        radarView = findViewById(R.id.radarview);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        radarItemList=new ArrayList<>();
        radarItemList.add(new RadarItem("火焰","0",0));
        radarItemList.add(new RadarItem("土壤","80",80/100.0f));
        radarItemList.add(new RadarItem("能量","80",80/100.0f));
        radarItemList.add(new RadarItem("动物","0",0));
        radarItemList.add(new RadarItem("天空","20",80/100.0f));
        radarItemList.add(new RadarItem("大海","80",80/100.0f));
        radarItemList.add(new RadarItem("风","0",0));
        radarItemList.add(new RadarItem("温度","0",0));
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
