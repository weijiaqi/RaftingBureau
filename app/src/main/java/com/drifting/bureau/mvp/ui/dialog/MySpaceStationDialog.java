package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.MySpaceStationEntity;
import com.drifting.bureau.mvp.ui.adapter.MySpaceStationAdapter;
import com.jess.arms.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 我的空间站升级
 * @Author     : WeiJiaQI
 * @Time       : 2022/5/26 12:05
 */
public class MySpaceStationDialog extends BaseDialog implements View.OnClickListener {

    private TextView mTvCofim;
    private ProgressBar mPrUpgrade;
    private RecyclerView mRcyInterests;
    private Context context;
    private MySpaceStationAdapter mySpaceStationAdapter;
    public MySpaceStationDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mTvCofim=findViewById(R.id.tv_cofim);
        mPrUpgrade=findViewById(R.id.pr_upgrade);
        mRcyInterests=findViewById(R.id.rcy_interests);
    }


    @Override
    protected void initEvents() {
        super.initEvents();
        mTvCofim.setOnClickListener(this);
        mRcyInterests.setLayoutManager(new GridLayoutManager(context,4));
        mySpaceStationAdapter=new MySpaceStationAdapter(new ArrayList<>());
        mRcyInterests.setAdapter(mySpaceStationAdapter);
        mySpaceStationAdapter.setData(getdata());

        mPrUpgrade.setProgress(50);

    }

    public List<MySpaceStationEntity> getdata(){
        List<MySpaceStationEntity> list=new ArrayList<>();
        list.add(new MySpaceStationEntity("拥有玩法"));
        list.add(new MySpaceStationEntity("拥有玩法"));
        list.add(new MySpaceStationEntity("拥有玩法"));
        list.add(new MySpaceStationEntity("拥有玩法"));
        list.add(new MySpaceStationEntity("拥有玩法"));
        list.add(new MySpaceStationEntity("拥有玩法"));
        list.add(new MySpaceStationEntity("拥有玩法"));
        list.add(new MySpaceStationEntity("拥有玩法"));
        list.add(new MySpaceStationEntity("拥有玩法"));
        list.add(new MySpaceStationEntity("拥有玩法"));
        return list;
    }


    @Override
    protected int getContentView() {
        return R.layout.dialog_my_space_station;
    }

    @Override
    protected float getDialogWith() {
        return 0.8f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cofim:
                dismiss();
                break;
        }
    }
}
