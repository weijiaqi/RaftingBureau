package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.AnnouncementEntity;

import com.drifting.bureau.mvp.ui.adapter.ArAnnouncementDisplayAdapter;

import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.base.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 公告
 * @Author : WeiJiaQI
 * @Time : 2022/8/24 14:08
 */
public class ArAnnouncementDisplayDialog extends BaseDialog {
    private Context context;
    private RecyclerView mRcyTab;
    private ImageView mIvPic;
    private TextView mTvContent;
    private ArAnnouncementDisplayAdapter adapter;
    private List<AnnouncementEntity> list;
    public ArAnnouncementDisplayDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected int getContentView() {
        return R.layout.dialog_announcement_display;
    }


    @Override
    protected void initDatas() {
        super.initDatas();
        mRcyTab = findViewById(R.id.rcy_tab);
        mIvPic=findViewById(R.id.iv_pic);
        mTvContent=findViewById(R.id.tv_content);
    }


    @Override
    protected void initEvents() {
        super.initEvents();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRcyTab.setLayoutManager(linearLayoutManager);
        adapter=new ArAnnouncementDisplayAdapter(new ArrayList<>());
        mRcyTab.setAdapter(adapter);
        getData();

        adapter.setRecyclerItemClickListner(new BaseRecyclerAdapter.OnRecyclerItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {
                GlideUtil.create().loadLongImage(context, list.get(position).getImage(), mIvPic);
                mTvContent.setText(list.get(position).getContent());
            }
        });
    }

    public void getData() {
        RequestUtil.create().latest(entity -> {
            if (entity != null && entity.getCode() == 200) {
                list = entity.getData();
                if (list.size() > 0) {
                    adapter.setData(list);
                    GlideUtil.create().loadLongImage(context, list.get(0).getImage(), mIvPic);
                    mTvContent.setText(list.get(0).getContent());
                }
            }
        });
    }


    @Override
    protected float getDialogWith() {
        return 1f;
    }

}
