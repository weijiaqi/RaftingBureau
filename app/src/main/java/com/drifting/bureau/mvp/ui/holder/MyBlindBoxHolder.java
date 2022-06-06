package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.MyBlindBoxRefreshEvent;

import com.drifting.bureau.mvp.model.entity.BoxOpenEntity;
import com.drifting.bureau.mvp.model.entity.MyBlindBoxEntity;
import com.drifting.bureau.mvp.ui.dialog.DonationDialog;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;
import com.drifting.bureau.util.ColorUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.callback.BaseDataCallBack;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.base.BaseRecyclerHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

public class MyBlindBoxHolder extends BaseRecyclerHolder {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_open_blind_box)
    TextView mTvOpen;
    @BindView(R.id.tv_give_blind_box)
    TextView mTvGiveBlindBox;
    private Context context;

    private PublicDialog publicDialog;
    private DonationDialog donationDialog;

    public MyBlindBoxHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<MyBlindBoxEntity.ListBean> listBeanList, int position) {
        TextUtil.setText(mTvTitle, listBeanList.get(position).getSku_name());
        GlideUtil.create().loadNormalPic(context, listBeanList.get(position).getImage(), mIvPic);
        mTvOpen.setOnClickListener(v -> { //开启盲盒
            RequestUtil.create().mysteryboxopen(listBeanList.get(position).getId() + "", (BaseDataCallBack<BoxOpenEntity>) entity -> {
                if (entity.getCode() == 200) {
                    if (entity.getData() != null) {
                        publicDialog = new PublicDialog(context);
                        publicDialog.show();
                        publicDialog.setCancelable(false);
                        if (entity.getData().getStatus() == 0) {
                            publicDialog.setTitleText("很遗憾");
                            publicDialog.setContentText("盒子里面空空如也");
                        } else {
                            publicDialog.setTitleText("恭喜你");
                            if (entity.getData().getSpace_times() > 0) {
                                if (entity.getData().getSpace_times() == 1) {
                                    publicDialog.setContentText("获得“" + entity.getData().getName() + "”\n第一次获得已自动为你使用");
                                } else {
                                    publicDialog.setContentText("获得“" + entity.getData().getName() + "”\n可在空间中列表操作");
                                }
                            } else {
                                publicDialog.setContentText("获得“" + entity.getData().getName() + "”");
                            }
                        }
                        publicDialog.setOnClickCallback(type -> {
                            EventBus.getDefault().post(new MyBlindBoxRefreshEvent());
                        });
                    }
                }
            });
        });

        mTvGiveBlindBox.setOnClickListener(v -> {  //转赠
            donationDialog = new DonationDialog(context);
            donationDialog.show();
            donationDialog.setOnContentClickCallback(content -> {
                RequestUtil.create().mysteryboxtransfer(listBeanList.get(position).getId() + "", content, entity -> {
                    if (entity != null) {
                        ToastUtil.showToast(entity.getMsg());
                        if (entity.getCode() == 200) {
                            EventBus.getDefault().post(new MyBlindBoxRefreshEvent());
                        }
                    }
                });
            });
        });
    }
}
