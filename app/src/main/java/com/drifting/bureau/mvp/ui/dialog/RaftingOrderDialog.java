package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.RaftingOrderEntity;
import com.drifting.bureau.mvp.ui.activity.pay.PaymentInfoActivity;
import com.drifting.bureau.mvp.ui.adapter.RaftingOrderAdapter;
import com.jess.arms.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 卫佳琪1
 * @description 奶茶订单
 * @time 9:43 9:43
 */

public class RaftingOrderDialog extends BaseDialog implements View.OnClickListener {
    private RecyclerView mRecycleView;
    private TextView mTvBuyNow;
    private Context context;
    private RaftingOrderAdapter raftingOrderAdapter;

    public RaftingOrderDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void initView() {
        super.initView();
        mRecycleView = findViewById(R.id.rcy_order);
        mTvBuyNow = findViewById(R.id.tv_buy_now);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mTvBuyNow.setOnClickListener(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(context));
        raftingOrderAdapter = new RaftingOrderAdapter(new ArrayList<>());
        mRecycleView.setAdapter(raftingOrderAdapter);
        raftingOrderAdapter.setData(setData());
    }

    public List<RaftingOrderEntity> setData() {
        List<RaftingOrderEntity> list = new ArrayList<>();
        list.add(new RaftingOrderEntity("11"));
        list.add(new RaftingOrderEntity("11"));
        return list;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_rafting_order;
    }

    @Override
    protected float getDialogWith() {
        return 0.8f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_buy_now:
                dismiss();
                PaymentInfoActivity.start(context, false);
                break;
        }
    }
}
