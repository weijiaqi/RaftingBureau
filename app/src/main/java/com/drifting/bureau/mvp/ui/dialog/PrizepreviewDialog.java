package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.PrizeEntity;
import com.drifting.bureau.mvp.ui.adapter.PrizeAdapter;
import com.jess.arms.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @description  空间站奖品预览
 * @author 卫佳琪1
 * @time 18:06 18:06
 */

public class PrizepreviewDialog extends BaseDialog {
    private RecyclerView mRcyPrize;
    private Context context;
    private PrizeAdapter prizeAdapter;
    private List<PrizeEntity> list;

    public PrizepreviewDialog(@NonNull Context context, List<PrizeEntity> list) {
        super(context);
        this.context = context;
        this.list = list;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mRcyPrize = findViewById(R.id.rcy_prize);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mRcyPrize.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        prizeAdapter = new PrizeAdapter(new ArrayList<>());
        mRcyPrize.setAdapter(prizeAdapter);
        prizeAdapter.setData(list);
    }


    @Override
    protected int getContentView() {
        return R.layout.dialog_prize_preview;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }
}
