package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.ui.activity.index.ViewRaftingActivity;
import com.jess.arms.base.BaseDialog;
/**
 * @Description:  收到的漂流瓶消息
 * @Author     : WeiJiaQI
 * @Time       : 2022/5/18 9:39
 */
public class RaftingInforDialog  extends BaseDialog implements View.OnClickListener {
   private TextView mTvThrowSpce,mTvSelect;
    public static final int CLICK_SELECT = 0x01;
    public static final int CLICK_FINISH = 0x02;
    private Context mContext;
    public RaftingInforDialog(@NonNull Context context) {
        super(context);
        mContext=context;
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
        mTvThrowSpce= findViewById(R.id.tv_throw_space);
        mTvSelect= findViewById(R.id.tv_select);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvThrowSpce.setOnClickListener(this);
        mTvSelect.setOnClickListener(this);
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
