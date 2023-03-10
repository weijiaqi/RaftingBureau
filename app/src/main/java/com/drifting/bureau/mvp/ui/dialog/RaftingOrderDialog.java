package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.ui.adapter.RaftingOrderAdapter;
import com.drifting.bureau.util.DateUtil;
import com.jess.arms.base.BaseDialog;

import java.util.ArrayList;

/**
 * @author 卫佳琪1
 * @description 奶茶订单
 * @time 9:43 9:43
 */

public class RaftingOrderDialog extends BaseDialog implements View.OnClickListener {

    public static final int SELECT_FINISH = 0x01;

    private RecyclerView mRecycleView;
    private TextView mTvBuyNow,mTvSum,mTvTime,mTvWarning;
    private Context context;
    private RaftingOrderAdapter raftingOrderAdapter;

    private SkuListEntity skuListEntity;

    public RaftingOrderDialog(@NonNull Context context, SkuListEntity skuListEntity) {
        super(context);
        this.context = context;
        this.skuListEntity = skuListEntity;
    }

    @Override
    protected void initView() {
        super.initView();
        mRecycleView = findViewById(R.id.rcy_order);
        mTvBuyNow = findViewById(R.id.tv_buy_now);
        mTvSum= findViewById(R.id.tv_sum);
        mTvTime= findViewById(R.id.tv_time);
        mTvWarning=findViewById(R.id.tv_warning);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mTvBuyNow.setOnClickListener(this);
        mTvSum.setText("￥"+skuListEntity.getTotalAmount());
        mRecycleView.setLayoutManager(new LinearLayoutManager(context));
        raftingOrderAdapter = new RaftingOrderAdapter(new ArrayList<>());
        mRecycleView.setAdapter(raftingOrderAdapter);
        raftingOrderAdapter.setData(skuListEntity.getGoods_sku());
        mTvTime.setText(DateUtil.unxiToDateYMD(System.currentTimeMillis()+""));

        if (!TextUtils.isEmpty(skuListEntity.getWarning())){
            mTvWarning.setVisibility(View.VISIBLE);
            mTvWarning.setText(skuListEntity.getWarning());
        }else {
            mTvWarning.setVisibility(View.GONE);
        }
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
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_FINISH);
                }
                break;
        }
    }
}
