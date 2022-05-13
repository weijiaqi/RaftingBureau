package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drifting.bureau.R;
import com.drifting.bureau.di.component.DaggerDriftingBottleComponent;
import com.drifting.bureau.mvp.ui.activity.user.AboutMeActivity;
import com.drifting.bureau.mvp.ui.dialog.RecordingDialog;
import com.drifting.bureau.util.ClickUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.DriftingBottleContract;
import com.drifting.bureau.mvp.presenter.DriftingBottlePresenter;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/05/12 16:01
 *
 * @author 谢况
 * module name is DriftingBottleActivity
 */
public class DriftingBottleActivity extends BaseActivity<DriftingBottlePresenter> implements DriftingBottleContract.View {
    @BindView(R.id.rl_info)
    RelativeLayout mRlInfo;
    @BindView(R.id.toolbar_title)
    TextView mToobarTitle;
    @BindView(R.id.tv_word)
    TextView mTvWord;
    @BindView(R.id.tv_voice)
    TextView mTvVoice;
    @BindView(R.id.tv_video)
    TextView mTvVideo;
    @BindView(R.id.et_word)
    EditText mEtWord;
    @BindView(R.id.iv_voice)
    ImageView mIvVoice;
    @BindView(R.id.iv_video)
    ImageView mIvVideo;
   private RecordingDialog recordingDialog;
    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, DriftingBottleActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDriftingBottleComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_drifting_bottle; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mRlInfo.setVisibility(View.VISIBLE);
        mToobarTitle.setText("传递漂");
        setSelected(1);
    }


    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.toolbar_back, R.id.tv_word, R.id.tv_voice, R.id.tv_video,R.id.iv_voice})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_word:  //文字
                    setSelected(1);
                    break;
                case R.id.tv_voice: //语音
                    setSelected(2);
                    break;
                case R.id.tv_video://视频
                    setSelected(3);
                    break;
                case R.id.iv_voice:  //语音


                    recordingDialog=new RecordingDialog(this);
                    recordingDialog.show();
                    break;
            }
        }
    }

    public void setSelected(int type) {
        mTvWord.setBackgroundResource(type == 1 ? R.drawable.drifting_selected : R.drawable.drifting_unselected);
        mTvVoice.setBackgroundResource(type == 2 ? R.drawable.drifting_selected : R.drawable.drifting_unselected);
        mTvVideo.setBackgroundResource(type == 3 ? R.drawable.drifting_selected : R.drawable.drifting_unselected);
        mEtWord.setVisibility(type == 1 ? View.VISIBLE : View.GONE);
        mIvVoice.setVisibility(type==2?View.VISIBLE:View.GONE);
        mIvVideo.setVisibility(type == 3?View.VISIBLE:View.GONE);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}