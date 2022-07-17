package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.BarrageEntity;
import com.drifting.bureau.mvp.ui.adapter.RaftingDetailsAdapter;
import com.drifting.bureau.util.TextUtil;
import com.jess.arms.base.BaseRecyclerHolder;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;

public class RaftingDetailsHolder extends BaseRecyclerHolder {
    @BindView(R.id.tv_content)
    TextView mTvContent;

    private Context context;

   private RaftingDetailsAdapter adapter;

    public RaftingDetailsHolder(View itemView, RaftingDetailsAdapter adapter) {
        super(itemView);
        context = itemView.getContext();
        this.adapter=adapter;
    }

    public void setData(@NonNull List<BarrageEntity.CommentsBean> listBeanList, int position) {

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        if (listBeanList.get(position % listBeanList.size()).getContent().contains("\u3000")) {
            RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(width, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutparams.setMargins(0, 0, 0, 0);
            mTvContent.setLayoutParams(layoutparams);
            mTvContent.setVisibility(View.INVISIBLE);
        } else {
            RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutparams.setMargins(0, 0, ArmsUtils.dip2px(context, 19), 0);
            mTvContent.setLayoutParams(layoutparams);
            mTvContent.setVisibility(View.VISIBLE);
        }

        if (listBeanList.get(position % listBeanList.size()).getType_id() == 1) {
            TextUtil.setText(mTvContent, listBeanList.get(position % listBeanList.size()).getUser_name() + "：" + listBeanList.get(position % listBeanList.size()).getContent());
            mTvContent.setTextColor(context.getColor(R.color.white));
        } else if (listBeanList.get(position % listBeanList.size()).getType_id() == 2) {
            TextUtil.setText(mTvContent, listBeanList.get(position % listBeanList.size()).getUser_name() + "：发了一段语音~~");
            mTvContent.setTextColor(context.getColor(R.color.color_6d));
        } else {
            TextUtil.setText(mTvContent, listBeanList.get(position % listBeanList.size()).getUser_name() + "：发了一段视频");
            mTvContent.setTextColor(context.getColor(R.color.color_e69));
        }
        mTvContent.setOnClickListener(v->{
            adapter.onItemCheckChange(listBeanList.get(position % listBeanList.size()));
        });

    }
}
