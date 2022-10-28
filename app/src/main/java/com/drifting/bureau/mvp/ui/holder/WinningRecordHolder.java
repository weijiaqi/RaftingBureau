package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.MyBlindBoxRefreshEvent;
import com.drifting.bureau.data.event.WinningRecordEvent;
import com.drifting.bureau.mvp.model.entity.OpenBoxListEntity;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;
import com.drifting.bureau.mvp.ui.dialog.WinningAddressDialog;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.base.BaseRecyclerHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

public class WinningRecordHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_title)
    TextView mTvTile;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_to_perfect)
    TextView mTvToPerfect;
    @BindView(R.id.ll_address)
    LinearLayout mLlAddress;
    @BindView(R.id.rl_no_address)
    RelativeLayout mRlNoAddress;
    @BindView(R.id.rl_bottom)
    RelativeLayout mRlBottom;
    private Context context;

    private WinningAddressDialog winningAddressDialog;
    private PublicDialog publicDialog;

    public WinningRecordHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<OpenBoxListEntity.ListBean> listBeanList, int position) {
        TextUtil.setText(mTvTime, "时间：" + DateUtil.unxiToDateYMDHM(listBeanList.get(position).getCreate_time() + ""));
        TextUtil.setText(mTvTile, listBeanList.get(position).getGoods_name());
        GlideUtil.create().loadLongImage(context, listBeanList.get(position).getSmall_image(), mIvPic);
        if (listBeanList.get(position).getIs_fictitious() == 1) { ////1虚拟商品0实体商品
            mRlBottom.setVisibility(View.GONE);
        } else {
            mRlBottom.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(listBeanList.get(position).getAddress())) {
                mLlAddress.setVisibility(View.VISIBLE);
                mRlNoAddress.setVisibility(View.GONE);
                TextUtil.setText(mTvName, "姓名：" + listBeanList.get(position).getPeople_name());
                TextUtil.setText(mTvPhone, "电话：" + listBeanList.get(position).getMobile());
                TextUtil.setText(mTvAddress, "地址：" + listBeanList.get(position).getAddress());
            } else {
                mLlAddress.setVisibility(View.GONE);
                mRlNoAddress.setVisibility(View.VISIBLE);
                mTvToPerfect.setOnClickListener(view -> {
                    winningAddressDialog = new WinningAddressDialog(context, listBeanList.get(position).getImage());
                    winningAddressDialog.show();
                    winningAddressDialog.setOnAddressClickCallback((name, phone, address) -> {
                        RequestUtil.create().addexpress(listBeanList.get(position).getSafe_box_open_record_id(), name, phone, address, entity2 -> {
                            if (entity2 != null && entity2.getCode() == 200) {
                                winningAddressDialog.dismiss();
                                publicDialog = new PublicDialog(context);
                                publicDialog.show();
                                publicDialog.setCancelable(false);
                                publicDialog.setTitleText("恭喜");
                                publicDialog.setContentText("您的奖品已发放");
                                publicDialog.setButtonText("确定");
                                publicDialog.setOnClickCallback(type -> {
                                    EventBus.getDefault().post(new WinningRecordEvent());
                                });
                            } else {
                                ToastUtil.showToast(entity2.getMsg());
                            }
                        });
                    });
                });
            }
        }
    }
}
