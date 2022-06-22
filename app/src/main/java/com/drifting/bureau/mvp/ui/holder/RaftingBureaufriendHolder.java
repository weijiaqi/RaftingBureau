package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.d.lib.slidelayout.SlideLayout;
import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.RaftingBureaufriendEntity;
import com.drifting.bureau.mvp.ui.adapter.RaftingBureaufriendAdapter;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.TextUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseRecyclerHolder;
import java.util.List;
import butterknife.BindView;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.model.Conversation;


public class RaftingBureaufriendHolder extends BaseRecyclerHolder {

    @BindView(R.id.sl_slide)
    SlideLayout mSlSlide;
    @BindView(R.id.tv_delete)
    TextView mTvDelete;
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_identity)
    TextView mTvIdentity;
    @BindView(R.id.rl_content)
    RelativeLayout mRlContent;
    private RaftingBureaufriendAdapter adapter;
    private Context context;

    public RaftingBureaufriendHolder(View itemView, RaftingBureaufriendAdapter adapter) {
        super(itemView);
        context = itemView.getContext();
        this.adapter = adapter;
    }


    public void setData(@NonNull List<RaftingBureaufriendEntity.FriendsBean> data, int position) {
        GlideUtil.create().loadCirclePic(context, data.get(position).getProfile_photo(), mIvHead);
        TextUtil.setText(mTvName, data.get(position).getFriend_user_name());
        TextUtil.setText(mTvIdentity, data.get(position).getPlanet_name() + "  " + data.get(position).getFriend_level_name());

        mTvDelete.setOnClickListener(v -> {
            RequestUtil.create().frienddelete(data.get(position).getUser_id(), entity -> {
                if (entity != null && entity.getCode() == 200) {
                    mSlSlide.close();
                    adapter.remove(position);
                }
            });
        });
        mRlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //启动单聊会话页面 在哪里点击的就在哪里调用
                Conversation.ConversationType conversationType =Conversation.ConversationType.PRIVATE;
                //RongyunUserID RongyunUserName 对方信息
                String targetId =data.get(position).getFriend_user_id()+"";//这个是对方的id
                String title =data.get(position).getFriend_user_name();//这个可以设置对方的名字
                Bundle bundle = new Bundle();
                if (!TextUtils.isEmpty(title)) {
                    bundle.putString(RouteUtils.TITLE, title); //会话页面标题
                }
                RouteUtils.routeToConversationActivity(context, Conversation.ConversationType.PRIVATE,targetId,bundle );
            }
        });
    }
}
