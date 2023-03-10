package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.data.event.MessageEvent;
import com.drifting.bureau.mvp.model.entity.SysmessageMineEntity;
import com.drifting.bureau.mvp.ui.activity.index.AnnouncementDetailsActivity;
import com.drifting.bureau.mvp.ui.activity.user.DriftingTrackActivity;
import com.drifting.bureau.mvp.ui.activity.user.MessageCenterActivity;
import com.drifting.bureau.mvp.ui.activity.user.MySpaceStationActivity;
import com.drifting.bureau.mvp.ui.activity.user.OrderRecordActivity;
import com.drifting.bureau.util.DateUtil;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.callback.BaseDataCallBack;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.base.BaseRecyclerHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

public class MessageHolder extends BaseRecyclerHolder {
    @BindView(R.id.rl_content)
    RelativeLayout mRlContent;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.iv_message)
    ImageView mIvMessage;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    private Context context;

    public MessageHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<SysmessageMineEntity.ListBean> listBeanList, int position) {
        TextUtil.setText(mTvTitle, listBeanList.get(position).getTitle());
        TextUtil.setText(mTvDate, DateUtil.unxiToCompanyDateYMDHM(listBeanList.get(position).getCreated_at_int()+""));
        TextUtil.setText(mTvContent, listBeanList.get(position).getContent());
        mIvMessage.setVisibility(listBeanList.get(position).getIs_read() == 0 ? View.VISIBLE : View.GONE);
        mRlContent.setOnClickListener(view -> {
            if (listBeanList.get(position).getIs_read() == 0) {
                RequestUtil.create().markread(listBeanList.get(position).getSys_msg_id(), entity -> {
                    if (entity != null && entity.getCode() == 200) {
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setSys_msg_id((listBeanList.get(position).getSys_msg_id()));
                        EventBus.getDefault().post(messageEvent);
                    }
                });
            }
            if (listBeanList.get(position).getMsy_type() == 1) {  //??????
                AnnouncementDetailsActivity.start(context, listBeanList.get(position).getTitle(), DateUtil.unixToDateWeek(listBeanList.get(position).getCreated_at_int() + ""), listBeanList.get(position).getContent(), false);
            } else if (listBeanList.get(position).getMsy_type() == 2) {  //???????????????
                if (listBeanList.get(position).getMsy_type_sub() == 21) { //?????????????????????
                    MySpaceStationActivity.start(context, false);
                } else if (listBeanList.get(position).getMsy_type_sub() == 22) { //????????????
                    OrderRecordActivity.start(context, false);
                }
            } else if (listBeanList.get(position).getMsy_type() == 3) {  //????????????
                if (listBeanList.get(position).getMsy_type_sub() == 31) { //??????????????????
                    if (MessageCenterActivity.class.isInstance(context)) {
                        MessageCenterActivity activity = (MessageCenterActivity) context;
                        activity.finish();
                    }
                } else if (listBeanList.get(position).getMsy_type_sub() == 32) { //??????????????????
                    DriftingTrackActivity.start(context, false);
                }
            }
        });
    }
}
