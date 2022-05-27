package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.jess.arms.base.BaseDialog;

/**
 * @description 查看订单
 * @author 卫佳琪1
 * @time 10:49 10:49
 */

public class SelectOrderDialog  extends BaseDialog implements View.OnClickListener{

    public static final int SELECT_FINISH = 0x01;
    public static final int SELECT_CANCEL = 0x02;

    private TextView mTvLeaveSpace,mTvMadeForHim;


    public SelectOrderDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mTvLeaveSpace=findViewById(R.id.tv_leave_space);
        mTvMadeForHim=findViewById(R.id.tv_made_for_him);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvLeaveSpace.setOnClickListener(this);
        mTvMadeForHim.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_select_order;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }

    @Override
    public void onClick(View view) {
      switch (view.getId()){
          case R.id.tv_leave_space:
              dismiss();
              if (onClickCallback != null) {
                  onClickCallback.onClickType(SELECT_CANCEL);
              }
              break;
          case  R.id.tv_made_for_him:
              dismiss();
              if (onClickCallback != null) {
                  onClickCallback.onClickType(SELECT_FINISH);
              }
              break;
      }
    }
}
