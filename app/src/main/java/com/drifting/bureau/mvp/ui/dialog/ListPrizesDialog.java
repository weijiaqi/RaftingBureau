package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.util.GlideUtil;
import com.hjq.shape.layout.ShapeRelativeLayout;
import com.jess.arms.base.BaseDialog;

/**
 * @Description: 奖品名单
 * @Author : WeiJiaQI
 * @Time : 2022/10/13 10:26
 */
public class ListPrizesDialog extends BaseDialog  implements View.OnClickListener {

    private Context context;
    private ImageView mIvPic;
    private ShapeRelativeLayout mRlNext;
    private String url;

    public ListPrizesDialog(@NonNull Context context, String url) {
        super(context);
        this.context = context;
        this.url = url;
    }


    @Override
    protected void initView() {
        super.initView();
        mIvPic = findViewById(R.id.iv_pic);
        mRlNext=findViewById(R.id.rl_next);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mRlNext.setOnClickListener(this);
        GlideUtil.create().loadLongImage(context,url,mIvPic);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_list_prizes;
    }

    @Override
    protected void getWindows(Window window) {
        super.getWindows(window);
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
           window.setWindowAnimations(R.style.EnterDialog);
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }


    @Override
    protected float getDialogWith() {
        return 1f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_next:
                dismiss();
                break;
        }
    }
}
