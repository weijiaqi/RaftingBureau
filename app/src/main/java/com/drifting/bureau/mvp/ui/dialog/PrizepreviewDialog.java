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

public class PrizepreviewDialog extends BaseDialog {
    private RecyclerView mRcyPrize;
    private Context context;
    private PrizeAdapter prizeAdapter;

    public PrizepreviewDialog(@NonNull Context context) {
        super(context);
        context = context;
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
        prizeAdapter.setData(getData());
    }

    public List<PrizeEntity> getData() {
        List<PrizeEntity> list = new ArrayList<>();
        list.add(new PrizeEntity(""));
        list.add(new PrizeEntity(""));
        list.add(new PrizeEntity(""));
        list.add(new PrizeEntity(""));
        list.add(new PrizeEntity(""));
        return list;
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
