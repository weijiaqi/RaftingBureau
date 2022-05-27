package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.MyTreasuryEntity;
import com.drifting.bureau.mvp.ui.adapter.MyTreasuryAdapter;
import com.jess.arms.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 卫佳琪1
 * @description 我的库藏
 * @time 9:36 9:36
 */

public class MyTreasuryDialog extends BaseDialog {
    private Context context;
    private RecyclerView mRcyMyTrea;
    private ImageView mIvPicture;
    private TextView mTvTitle, mTvNum, mTvUse, mTvGiveAway, mTvContent;


    private MyTreasuryAdapter myTreasuryAdapter;
    private List<MyTreasuryEntity> list;

    public MyTreasuryDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_my_treasury;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mRcyMyTrea = findViewById(R.id.rcy_my_trea);
        mIvPicture = findViewById(R.id.iv_picture);
        mTvTitle = findViewById(R.id.tv_title);
        mTvNum = findViewById(R.id.tv_num);
        mTvUse = findViewById(R.id.tv_use);
        mTvGiveAway = findViewById(R.id.tv_give_away);
        mTvContent = findViewById(R.id.tv_content);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mRcyMyTrea.setLayoutManager(new GridLayoutManager(context, 4));
        myTreasuryAdapter = new MyTreasuryAdapter(new ArrayList<>(), entity -> {
            mTvTitle.setText(entity.getTitle());
            mTvNum.setText("数量  "+entity.getNum() );
            mTvContent.setText(entity.getContent());
        });
        mRcyMyTrea.setAdapter(myTreasuryAdapter);
        myTreasuryAdapter.setData(getData());
        myTreasuryAdapter.onItemCheckChange(list.get(0));
    }

    public List<MyTreasuryEntity> getData() {
        list = new ArrayList<>();
        list.add(new MyTreasuryEntity(10, "初级空间站", "可用来创建和升级自己的空间站"));
        list.add(new MyTreasuryEntity(20, "中级空间站", "可用来创建和升级自己的空间站"));
        list.add(new MyTreasuryEntity(30, "初级空间站", "可用来创建和升级自己的空间站"));
        list.add(new MyTreasuryEntity(40, "高级空间站", "可用来创建和升级自己的空间站"));
        list.add(new MyTreasuryEntity(50, "初级空间站", "可用来创建和升级自己的空间站"));
        list.add(new MyTreasuryEntity(60, "中级空间站", "可用来创建和升级自己的空间站"));
        list.add(new MyTreasuryEntity(70, "高级空间站", "可用来创建和升级自己的空间站"));
        return list;
    }

    @Override
    protected float getDialogWith() {
        return 0.9f;
    }
}
