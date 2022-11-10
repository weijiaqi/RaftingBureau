package com.drifting.bureau.mvp.ui.dialog;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.ui.activity.index.StarDistributionActivity;
import com.drifting.bureau.mvp.ui.activity.user.NewAboutMeActivity;
import com.drifting.bureau.mvp.ui.adapter.KeyboardAdapter;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.base.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @Description: 开启宝箱密码
 * @Author : WeiJiaQI
 * @Time : 2022/10/11 19:22
 */
public class BoxPasswordDialog extends BaseDialog implements BaseRecyclerAdapter.OnRecyclerItemClickListner, View.OnClickListener {

    private RecyclerView mRecyclerView;
    private TextView mTvOne, mTvTwo, mTvThree, mTvFour;
    private ImageView mIvGetPwd, mIvSubmit;

    private KeyboardAdapter mAdapter;
    private Handler handler = new Handler();

    /**
     * 输入键盘文本
     */
    private static final String[] KEYBOARD_TEXT = new String[]{"7", "8", "9", "4", "5", "6", "1", "2", "3", "", "0", ""};

    private Context context;

    private final LinkedList<String> mRecordList = new LinkedList<>();



    public BoxPasswordDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void initView() {
        super.initView();
        mTvOne = findViewById(R.id.tv_one);
        mTvTwo = findViewById(R.id.tv_two);
        mTvThree = findViewById(R.id.tv_three);
        mTvFour = findViewById(R.id.tv_four);
        mIvGetPwd = findViewById(R.id.iv_get_pwd);
        mIvSubmit = findViewById(R.id.iv_submit);
        mRecyclerView = findViewById(R.id.rv_pay_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        mAdapter = new KeyboardAdapter(new ArrayList<>());
        mAdapter.setData(Arrays.asList(KEYBOARD_TEXT));
        mAdapter.setRecyclerItemClickListner(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mIvGetPwd.setOnClickListener(this);
        mIvSubmit.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_box_password;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }

    @Override
    public void onItemClickListner(View v, int position) {
        switch (mAdapter.getItemViewType(position)) {
            case KeyboardAdapter.TYPE_DELETE:
                // 点击回退按钮删除
                if (mRecordList.size() != 0) {
                    mRecordList.removeLast();
                }
                break;
            case KeyboardAdapter.TYPE_EMPTY:
                // 点击空白的地方不做任何操作
                break;
            default:
                // 判断密码是否已经输入完毕
                if (mRecordList.size() < 4) {
                    // 点击数字，显示在密码行
                    mRecordList.add(KEYBOARD_TEXT[position]);
                }


                break;
        }
        mTvOne.setText("");
        mTvTwo.setText("");
        mTvThree.setText("");
        mTvFour.setText("");
        for (int i = 0; i < mRecordList.size(); i++) {
            if (i == 0) {
                mTvOne.setText(mRecordList.get(0));
            } else if (i == 1) {
                mTvTwo.setText(mRecordList.get(1));
            } else if (i == 2) {
                mTvThree.setText(mRecordList.get(2));
            } else if (i == 3) {
                mTvFour.setText(mRecordList.get(3));
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.iv_get_pwd:
                    RequestUtil.create().userplayer(Preferences.getUserId(), entity -> {
                        if (entity!=null &&entity.getData()!=null &&entity.getCode()==200){
                            if (entity.getData().getPlanet().getLevel() == 1) {
                                ToastUtil.showToast("心理测试参与完成之后才可以获取口令哦!");
                                StarDistributionActivity.start(context, false);
                            } else {
                                NewAboutMeActivity.start(context, false);
                            }
                        }
                    });
                    break;
                case R.id.iv_submit:
                    //     判断密码是否已经输入完毕
                    if (mRecordList.size() == 4) {
                        handler.postDelayed(() -> {
                            // 获取输入的支付密码
                            StringBuilder password = new StringBuilder();
                            for (String s : mRecordList) {
                                password.append(s);
                            }
                            if (onContentClickCallback != null) {
                                onContentClickCallback.onContetClick(password.toString());
                            }
                        }, 300);
                    } else {
                        ToastUtil.showToast("请输入正确的口令!");
                    }
                    break;
            }
        }
    }
}
