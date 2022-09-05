package com.drifting.bureau.mvp.ui.activity.index;

import static com.drifting.bureau.app.FilePathConstant.STAR_PATH;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.di.component.DaggerPlanetaryDetailComponent;
import com.drifting.bureau.mvp.contract.PlanetaryDetailContract;
import com.drifting.bureau.mvp.model.entity.PlanetaryDetailEntity;
import com.drifting.bureau.mvp.presenter.PlanetaryDetailPresenter;
import com.drifting.bureau.mvp.ui.activity.index.ar.ARActivity;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.util.ARCoreUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.FileUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.NotificationUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.downloadutil.DownloadRequest;
import com.drifting.bureau.util.manager.NotificationManager;
import com.jess.arms.di.component.AppComponent;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/01 14:50
 *
 * @author 星球详情
 * module name is PlanetaryDetailActivity
 */
public class PlanetaryDetailActivity extends BaseManagerActivity<PlanetaryDetailPresenter> implements PlanetaryDetailContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_planet)
    ImageView mIvPlanet;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_attribute)
    TextView mTvAttribute;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.tv_Ar)
    TextView mTvAr;
    private static String EXTRA_TYPE = "extra_type";
    private String ArUrl;
    private int type;


    public static void start(Context context, int type, boolean closePage) {
        Intent intent = new Intent(context, PlanetaryDetailActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPlanetaryDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_planetary_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mToolbarTitle.setText("星球详情");
        if (getIntent() != null) {
            type = getIntent().getIntExtra(EXTRA_TYPE, 0);
        }

        if (mPresenter != null) {
            mPresenter.planetdetails(type);
        }

    }



    @Override
    public void onPlanetaryDetailSuccess(PlanetaryDetailEntity entity) {
        if (entity != null) {
            mTvNum.setText(entity.getPeople() + "");
            ArUrl = entity.getAr_url();
            mTvAr.setVisibility(!TextUtils.isEmpty(ArUrl) ? View.VISIBLE : View.GONE);
            GlideUtil.create().loadDefaultPic(this, entity.getImage_url(), mIvPlanet);
            mTvName.setText(entity.getName());
            mTvDesc.setText(entity.getDesc());
            mTvAttribute.setText(entity.getAttr());
        }
    }

    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
    }


    @OnClick({R.id.toolbar_back, R.id.tv_Ar})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_Ar:  //AR体验
                    if (!TextUtils.isEmpty(ArUrl)) {
                        if (ARCoreUtil.checkArCoreAvailability(getActivity())) {
                            startCamrea(ArUrl); //开启AR
                        }
                    }
                    break;
            }
        }
    }

    public void startCamrea(String url) {
        PermissionDialog.requestCodePermissions(this, new PermissionDialog.PermissionCallBack() {
            @Override
            public void onSuccess() {
                String file = STAR_PATH + FileUtil.getUrlFileName(url);
                if (FileUtil.fileIsExists(file)) {
                    FileUtil.getNetworkFileSize(url, new Handler(Looper.myLooper()) {
                        @Override
                        public void handleMessage(Message msg) {
                            int fileSize = msg.getData().getInt("fileSize");
                            if (fileSize == new File(file).length()) {
                                ARActivity.start(PlanetaryDetailActivity.this, file, false);
                            } else {
                                showNotificationDialog(url);
                            }
                        }
                    });
                } else {
                    showNotificationDialog(url);
                }
            }

            @Override
            public void onFailure() {
            }

            @Override
            public void onAlwaysFailure() {
                PermissionDialog.showDialog(PlanetaryDetailActivity.this, "android.permission.CAMERA");
            }
        });
    }

    public void showNotificationDialog(String url) {
        if (NotificationManager.isOpenNotification(PlanetaryDetailActivity.this)) {
            DownloadRequest.whith().downloadWithNotification(PlanetaryDetailActivity.this, url);
        } else {
            NotificationUtil.showNotificationDialog(PlanetaryDetailActivity.this);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

}