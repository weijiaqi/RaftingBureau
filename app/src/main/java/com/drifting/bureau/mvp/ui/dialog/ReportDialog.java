package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseDialog;

public class ReportDialog extends BaseDialog implements View.OnClickListener {

    private CheckBox mCkYellow, mCkPolitics, mCkVerbal, mCkOther;
    private TextView mTvCofim;
    private EditText mEtReason;

    private int type = 0;
    private int id;

    public ReportDialog(@NonNull Context context, int id) {
        super(context);
        this.id = id;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mCkYellow = findViewById(R.id.ck_yellow_related);
        mCkPolitics = findViewById(R.id.ck_politics);
        mCkVerbal = findViewById(R.id.ck_verbal_abuse);
        mCkOther = findViewById(R.id.ck_other);
        mTvCofim = findViewById(R.id.tv_cofim);
        mEtReason = findViewById(R.id.et_reason);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mCkYellow.setOnClickListener(this);
        mCkPolitics.setOnClickListener(this);
        mCkVerbal.setOnClickListener(this);
        mCkOther.setOnClickListener(this);
        mTvCofim.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_report;
    }

    @Override
    protected float getDialogWith() {
        return 0.8f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ck_yellow_related:
                setCheckStatus(1);
                break;
            case R.id.ck_politics:
                setCheckStatus(2);
                break;
            case R.id.ck_verbal_abuse:
                setCheckStatus(3);
                break;
            case R.id.ck_other:
                setCheckStatus(4);
                break;
            case R.id.tv_cofim:
                if (type == 0) {
                    showMessage("请选择举报类型");
                    return;
                }
                if (StringUtil.isEmpty(mEtReason.getText().toString())) {
                    showMessage("请在此处留下举报的原因");
                    return;
                }
                RequestUtil.create().reportcommit(id, type, mEtReason.getText().toString(), entity -> {
                    if (entity != null && entity.getCode() == 200) {
                        dismiss();
                        showMessage("提交成功");
                    } else {
                        showMessage(entity.getMsg());
                    }
                });

                break;
        }
    }

    public void setCheckStatus(int status) {
        type = status;
        mCkYellow.setChecked(status == 1 ? true : false);
        mCkPolitics.setChecked(status == 2 ? true : false);
        mCkVerbal.setChecked(status == 3 ? true : false);
        mCkOther.setChecked(status == 4 ? true : false);
    }

    public void showMessage(String content) {
        ToastUtil.showToast(content);
    }
}
