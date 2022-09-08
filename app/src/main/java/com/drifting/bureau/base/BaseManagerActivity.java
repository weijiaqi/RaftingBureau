package com.drifting.bureau.base;

import static com.jess.arms.utils.ThirdViewUtil.convertAutoView;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.drifting.bureau.app.application.RBureauApplication;
import com.drifting.bureau.mvp.model.entity.VersionUpdateEntity;
import com.drifting.bureau.mvp.ui.activity.SplashActivity;
import com.drifting.bureau.mvp.ui.dialog.VersionUpdateDialog;
import com.drifting.bureau.util.AppUtil;
import com.drifting.bureau.util.StringUtil;
import com.drifting.bureau.util.callback.BaseDataCallBack;
import com.drifting.bureau.util.request.RequestUtil;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.ActivityLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.StatusBarUtil;
import com.drifting.bureau.util.SystemUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public abstract class BaseManagerActivity<P extends IPresenter> extends AppCompatActivity implements IActivity, ActivityLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null
    private Cache<String, Object> mCache;
    private Unbinder mUnbinder;
    private boolean initFlag;
    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            //noinspection unchecked
            mCache = ArmsUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }

    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = convertAutoView(name, context, attrs);
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        try {
            int layoutResID = initView(savedInstanceState);
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            if (e instanceof InflateException) {
                throw e;
            }
            e.printStackTrace();
        }
        initData(savedInstanceState);
    }


    /**
     * 状态栏背景及文字颜色
     *
     * @param textBarColor 状态栏文字 true 白色  false 黑色
     */
    public void setStatusBar(boolean textBarColor) {
        StatusBarUtil.setTransparentStatusBar(this, false);
        if (textBarColor) {
            StatusBarUtil.setDarkMode(this);
        } else {
            StatusBarUtil.setLightMode(this);
        }
    }

    /**
     * 状态栏背景及文字颜色
     *
     * @param textBarColorWhite   状态栏文字 true 白色  false 黑色
     * @param statusBarColorTheme true 主题色  false 透明
     */
    public void setStatusBar(boolean textBarColorWhite, boolean statusBarColorTheme) {
        if (statusBarColorTheme) {
            StatusBarUtil.setColor(this, getResources().getColor(com.jess.arms.R.color.color_01), 0);
        } else {
            StatusBarUtil.setTransparentStatusBar(this, false);
        }
        if (textBarColorWhite) {
            StatusBarUtil.setDarkMode(this);
        } else {
            StatusBarUtil.setLightMode(this);
        }
    }

    /**
     * 动态设置状态栏背景及文字颜色
     *
     * @param textBarColor   状态栏文字 true 白色  false 黑色
     * @param statusBarColor 状态栏背景颜色
     */
    public void setStatusBar(boolean textBarColor, int statusBarColor) {
        if (textBarColor) {
            StatusBarUtil.setDarkMode(this);
        } else {
            StatusBarUtil.setLightMode(this);
        }
        StatusBarUtil.setColor(this, getResources().getColor(statusBarColor), 0);
    }


    /**
     * 动态设置状态栏的高度
     *
     * @param tvBar
     */
    public void setStatusBarHeight(TextView tvBar) {
        if (tvBar == null) {
            return;
        }
        tvBar.setHeight(SystemUtil.getStatusBarHeight(this));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            tvBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 动态设置状态栏的高度颜色
     *
     * @param tvBar
     * @param isThemeColor
     */
    public void setStatusBarHeight(TextView tvBar, boolean isThemeColor) {
        tvBar.setHeight(SystemUtil.getStatusBarHeight(this));
        if (isThemeColor) {
            tvBar.setBackgroundColor(getResources().getColor(com.jess.arms.R.color.color_01));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            tvBar.setVisibility(View.VISIBLE);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (!initFlag) {
            Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                @Override
                public boolean queueIdle() {
                    initVisible();
                    return false;
                }
            });
            initFlag = true;
        } else {
            onReVisible();
        }

        if (RBureauApplication.isForeground == false) {
            //由后台切换到前台
            RBureauApplication.isForeground = true;
            if (!TextUtils.equals(getClass().getName(), SplashActivity.class.getName())) {
                baseCheckVersion();
            }
        }

    }

    public void baseCheckVersion() {
        RequestUtil.create().checkVersion(new BaseDataCallBack<VersionUpdateEntity>() {
            @Override
            public void getData(BaseEntity<VersionUpdateEntity> entity) {
                if (entity != null) {
                    if (entity.getData() != null) {
                        if (StringUtil.compareVersions(entity.getData().getVersion(), StringUtil.getVersion(RBureauApplication.getContext()))) {
                            showVersionDialog(entity.getData());
                        }
                    }
                }
            }
        });
    }

    /**
     * 版本更新dialog
     *
     * @param
     */
    public void showVersionDialog(VersionUpdateEntity data) {
        VersionUpdateDialog versionUpdateDialog = new VersionUpdateDialog(this, data.getUrl() + "?" + System.currentTimeMillis(), data.getStatus(), data.getMessage(), data.getVersion());
        versionUpdateDialog.show();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (!AppUtil.isAppOnForeground()) {
            //由前台切换到后台
            RBureauApplication.isForeground = false;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.create().disDispose();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
        }
        this.mPresenter = null;
    }


    /**
     * Activity初始化，Activity可见用于网路请求等耗时方法，第一次
     */
    protected void initVisible() {

    }

    /**
     * Activity第二次及以后
     */
    protected void onReVisible() {
    }


    /**
     * 是否使用 EventBus
     * Arms 核心库现在并不会依赖某个 EventBus, 要想使用 EventBus, 还请在项目中自行依赖对应的 EventBus
     * 现在支持两种 EventBus, greenrobot 的 EventBus 和畅销书 《Android源码设计模式解析与实战》的作者 何红辉 所作的 AndroidEventBus
     * 确保依赖后, 将此方法返回 true, Arms 会自动检测您依赖的 EventBus, 并自动注册
     * 这种做法可以让使用者有自行选择三方库的权利, 并且还可以减轻 Arms 的体积
     *
     * @return 返回 {@code true} (默认为 {@code true}), Arms 会自动注册 EventBus
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

    /**
     * 这个 {@link Activity} 是否会使用 {@link Fragment}, 框架会根据这个属性判断是否注册 {@link FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回 {@code false}, 那意味着这个 {@link Activity} 不需要绑定 {@link Fragment}, 那你再在这个 {@link Activity} 中绑定继承于 {@link BaseFragment} 的 {@link Fragment} 将不起任何作用
     *
     * @return 返回 {@code true} (默认为 {@code true}), 则需要使用 {@link Fragment}
     */
    @Override
    public boolean useFragment() {
        return true;
    }
}
