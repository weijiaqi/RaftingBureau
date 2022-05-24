package com.drifting.bureau.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.drifting.bureau.mvp.ui.activity.index.DriftingBottleActivity;
import com.drifting.bureau.mvp.ui.activity.index.VideoActivity;
import com.drifting.bureau.mvp.ui.activity.index.VideoRecordingActivity;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.mvp.ui.dialog.RaftingInforDialog;
import com.drifting.bureau.mvp.ui.dialog.RecordingDialog;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.drifting.bureau.mvp.contract.DriftingBottleContract;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/05/12 12:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class DriftingBottlePresenter extends BasePresenter<DriftingBottleContract.Model, DriftingBottleContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private RecordingDialog recordingDialog;

    @Inject
    public DriftingBottlePresenter(DriftingBottleContract.Model model, DriftingBottleContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}