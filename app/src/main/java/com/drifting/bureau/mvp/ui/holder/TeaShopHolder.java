package com.drifting.bureau.mvp.ui.holder;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.TeaShopEntity;
import com.drifting.bureau.mvp.ui.dialog.CallTelephoneDialog;
import com.drifting.bureau.mvp.ui.dialog.NavigationDialog;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.TextUtil;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class TeaShopHolder extends BaseRecyclerHolder {
    @BindView(R.id.iv_call)
    ImageView mIvCall;
    @BindView(R.id.iv_navigation)
    ImageView mIvNavigation;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_distance)
    TextView mTvDistance;
    @BindView(R.id.tv_shop)
    TextView mTvShop;
    @BindView(R.id.tv_status)
    ShapeTextView mTvStatus;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    private Context context;
    private CallTelephoneDialog callTelephoneDialog;
    private NavigationDialog navigationDialog;

    public TeaShopHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<TeaShopEntity.ListBean> listBeanList, int position) {
        TextUtil.setText(mTvShop, listBeanList.get(position).getBusiness_name());
        TextUtil.setText(mTvTime, listBeanList.get(position).getOpening() + "-" + listBeanList.get(position).getOpening_end());
        TextUtil.setText(mTvAddress, listBeanList.get(position).getAddress());
        TextUtil.setText(mTvDistance,  StringUtil.distanceFormat(Double.parseDouble(listBeanList.get(position).getDistance())));
        if (listBeanList.get(position).getOpen_status() == 1) {
            TextUtil.setText(mTvStatus, "营业中");
            mTvStatus.getTextColorBuilder().setTextGradientColors(context.getColor(R.color.color_6c), context.getColor(R.color.color_6d)).intoTextColor();
        }else if (listBeanList.get(position).getOpen_status() == 2){
            TextUtil.setText(mTvStatus, "配料中");
            mTvStatus.getTextColorBuilder().setTextGradientColors(context.getColor(R.color.color_ff), context.getColor(R.color.color_ff)).intoTextColor();
        } else {
            TextUtil.setText(mTvStatus, "未营业");
            mTvStatus.getTextColorBuilder().setTextGradientColors(context.getColor(R.color.color_ff), context.getColor(R.color.color_ff)).intoTextColor();
        }

        mIvCall.setOnClickListener(v -> {
            PermissionDialog.requestPhonePermissions((Activity) context, new PermissionDialog.PermissionCallBack() {
                @Override
                public void onSuccess() {
                    callTelephoneDialog = new CallTelephoneDialog(context, listBeanList.get(position).getTel());
                    callTelephoneDialog.show();
                }

                @Override
                public void onFailure() {

                }

                @Override
                public void onAlwaysFailure() {
                    PermissionDialog.showDialog((Activity) context, "android.permission.CALL_PHONE");
                }
            });
        });
        mIvNavigation.setOnClickListener(view -> {
            navigationDialog = new NavigationDialog(context, listBeanList.get(position).getLng(), listBeanList.get(position).getLat());
            navigationDialog.show();
        });

    }
}
