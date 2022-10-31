package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.SpaceAboutEntity;
import com.drifting.bureau.mvp.ui.adapter.HowToPlayAdapter;
import com.jess.arms.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 卫佳琪1
 * @description 玩法说明
 * @time 11:51 11:51
 */

public class HowToPlayDialog extends BaseDialog {

    RecyclerView mRcyList;
    private Context context;
    private HowToPlayAdapter howToPlayAdapter;
    private List<SpaceAboutEntity> list;

    public HowToPlayDialog(@NonNull Context context, List<SpaceAboutEntity> list) {
        super(context);
        this.context = context;
        this.list = list;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mRcyList = findViewById(R.id.rcy_play);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mRcyList.setLayoutManager(new LinearLayoutManager(context));
        mRcyList.setHasFixedSize(true);
        howToPlayAdapter = new HowToPlayAdapter(new ArrayList<>());
        mRcyList.setAdapter(howToPlayAdapter);
        howToPlayAdapter.setData(list);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_how_to_play;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }
}
