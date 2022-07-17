package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.util.GlideUtil;
import com.jess.arms.base.BaseDialog;

/**
 * @Description: 收到的漂流瓶消息
 * @Author : WeiJiaQI
 * @Time : 2022/5/18 9:39
 */
public class RaftingInforDialog extends BaseDialog implements View.OnClickListener {
    private TextView mTvThrowSpce, mTvSelect, mTvName, mTvPlanet, mTvIdentity, mTvRaftingType;
    private ImageView mIvMastor;
    public static final int CLICK_SELECT = 0x01;
    public static final int CLICK_FINISH = 0x02;
    private Context mContext;
    private UserInfoEntity userInfoEntity;
    private int explore_id;

    public RaftingInforDialog(@NonNull Context context, UserInfoEntity userInfoEntity, int explore_id) {
        super(context);
        this.mContext = context;
        this.userInfoEntity = userInfoEntity;
        this.explore_id = explore_id;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_rafting_infor;
    }

    @Override
    protected float getDialogWith() {
        return 0.8f;
    }

    @Override
    protected void initView() {
        super.initView();
        mTvThrowSpce = findViewById(R.id.tv_throw_space);
        mTvSelect = findViewById(R.id.tv_select);
        mTvName = findViewById(R.id.tv_name);
        mTvPlanet = findViewById(R.id.tv_planet);
        mTvIdentity = findViewById(R.id.tv_identity);
        mTvRaftingType = findViewById(R.id.tv_rafting_type);
        mIvMastor=findViewById(R.id.iv_mastor);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvThrowSpce.setOnClickListener(this);
        mTvSelect.setOnClickListener(this);
        mTvName.setText("昵称：" + userInfoEntity.getUser().getName());
        mTvPlanet.setText(userInfoEntity.getPlanet().getName());
        mTvIdentity.setText(userInfoEntity.getUser().getLevel_name());
        GlideUtil.create().loadLongImage(mContext,userInfoEntity.getUser().getMascot(),mIvMastor);
        if (explore_id == 1) {
            mTvRaftingType.setText("传递漂");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_throw_space:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(CLICK_FINISH);
                }
                break;
            case R.id.tv_select:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(CLICK_SELECT);
                }
                break;
        }

    }
}
