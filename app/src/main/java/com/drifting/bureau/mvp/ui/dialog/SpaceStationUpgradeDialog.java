package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.drifting.bureau.R;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseDialog;

/**
 * 升级空间站
 */
public class SpaceStationUpgradeDialog extends BaseDialog implements View.OnClickListener, TextWatcher {

    ImageView mivReduce, mIvPlus;
    EditText mEtNum;
    TextView mTvClick;
    private int count;

    private int index = 1;
    private int object_id;


    public static final int SELECT_FINISH = 0x01;

    public SpaceStationUpgradeDialog(@NonNull Context context, int count, int object_id) {
        super(context);
        this.count = count;
        this.object_id = object_id;
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mivReduce = findViewById(R.id.iv_reduce);
        mIvPlus = findViewById(R.id.iv_plus);
        mEtNum = findViewById(R.id.et_num);
        mTvClick = findViewById(R.id.tv_click);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mivReduce.setOnClickListener(this);
        mIvPlus.setOnClickListener(this);
        mTvClick.setOnClickListener(this);
        mEtNum.setText(index + "");
        mEtNum.addTextChangedListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_space_station_upgrade;
    }

    @Override
    protected float getDialogWith() {
        return 0.7f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_reduce:  //减
                index--;
                if (index == 0) {
                    showMessage("最小数量为1!");
                    index = 1;
                }
                mEtNum.setText(index + "");
                break;
            case R.id.iv_plus: //加
                index++;
                if (index > count) {
                    showMessage("最大数量为" + count + "!");
                    index = count;
                }
                mEtNum.setText(index + "");
                break;
            case R.id.tv_click:
                mEtNum.clearFocus();
                if (!TextUtils.isEmpty(mEtNum.getText().toString())) {
                    if (index > count || index == 0) {
                        showMessage("请输入正确的数量!");
                    } else {
                        RequestUtil.create().storageusing(object_id, index, entity -> {
                            if (entity != null) {
                                showMessage(entity.getMsg());
                                if (entity.getCode() == 200) {
                                    if (onClickCallback != null) {
                                        onClickCallback.onClickType(SELECT_FINISH);
                                    }
                                }
                            }
                        });
                    }
                } else {
                    showMessage("请输入数量!");
                }

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!TextUtils.isEmpty(editable.toString())) {
            index = Integer.parseInt(editable.toString());
        }
    }

    public void showMessage(String content) {
        ToastUtil.showBottomToast(content);
    }
}
