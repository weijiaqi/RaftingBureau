package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;

import com.drifting.bureau.mvp.model.entity.OrderRecordEntity;

import com.drifting.bureau.mvp.ui.dialog.WriteOffCodeDialog;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;


public class OrderRecordHolder extends BaseRecyclerHolder {
   @BindView(R.id.ll_content)
    LinearLayout mLlContent;

   private WriteOffCodeDialog writeOffCodeDialog;
   private Context context;
    public OrderRecordHolder(View itemView) {
        super(itemView);
        this.context=itemView.getContext();
    }

    public void setData(@NonNull List<OrderRecordEntity> listBeanList, int position) {
        mLlContent.setOnClickListener(v->{
            writeOffCodeDialog=new WriteOffCodeDialog(context);
            writeOffCodeDialog.show();
        });
    }
}
