package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.drifting.bureau.R;
<<<<<<< HEAD
import com.drifting.bureau.base.BaseManagerActivity;
=======
import com.drifting.bureau.base.BaseActivity;
>>>>>>> origin/dev
import com.drifting.bureau.di.component.DaggerNebulaeComponent;
import com.drifting.bureau.mvp.model.entity.NebulaListEntity;
import com.drifting.bureau.mvp.ui.adapter.NebulaeListAdapter;
import com.drifting.bureau.mvp.ui.dialog.PermissionDialog;
import com.drifting.bureau.util.AssessUtil;
import com.drifting.bureau.util.BitmapUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GsonUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;
import com.drifting.bureau.view.chart.EnergyChartView;
<<<<<<< HEAD
=======
import com.jess.arms.base.BaseRecyclerAdapter;
>>>>>>> origin/dev
import com.jess.arms.di.component.AppComponent;
import com.drifting.bureau.mvp.contract.NebulaeContract;
import com.drifting.bureau.mvp.presenter.NebulaePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2022/07/26 16:44
 *
 * @author 了解星云
 * module name is NebulaeActivity
 */
<<<<<<< HEAD
public class NebulaeActivity extends BaseManagerActivity<NebulaePresenter> implements NebulaeContract.View {
=======
public class NebulaeActivity extends BaseActivity<NebulaePresenter> implements NebulaeContract.View {
>>>>>>> origin/dev
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.line_chart_view)
    EnergyChartView lineChartView;
    @BindView(R.id.rl_pic)
    RelativeLayout mRlpic;
    @BindView(R.id.vscroll_view)
    ScrollView VscrollView;
    @BindView(R.id.scroll_view)
    HorizontalScrollView HscrollView;
    @BindView(R.id.rcy_list)
    RecyclerView mRcyList;
    private List<EnergyChartView.Data> datas, newList;
    private List<NebulaListEntity.ListBean> list;
    private NebulaeListAdapter nebulaeListAdapter;
    private Handler mHandler = new Handler();
    private int total;
    private static String EXTRA_NEBULA = "extra_nebula";
    private String nebula;
    private int x, y;

    public static void start(Context context, String nebula, boolean closePage) {
        Intent intent = new Intent(context, NebulaeActivity.class);
        intent.putExtra(EXTRA_NEBULA, nebula);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNebulaeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_nebulae; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        if (getIntent() != null) {
            nebula = getIntent().getExtras().getString(EXTRA_NEBULA);
        }
        mRcyList.setLayoutManager(new LinearLayoutManager(this));
        nebulaeListAdapter = new NebulaeListAdapter(new ArrayList<>(),nebula);
        mRcyList.setAdapter(nebulaeListAdapter);

        String chartjson = AssessUtil.getJson("chart.json", NebulaeActivity.this);
        datas = GsonUtil.getObjectList(chartjson, EnergyChartView.Data.class);

        if (mPresenter != null) {
            mPresenter.nebulalist();
        }


    }


    @OnClick({R.id.toolbar_back, R.id.tv_nebula})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_nebula:  //星云列表
                    mHandler.postDelayed(() -> {
                        Bitmap bitmap = BitmapUtil.captureView(mRlpic);
                        PermissionDialog.requestPermissions(this, new PermissionDialog.PermissionCallBack() {
                            @Override
                            public void onSuccess() {
                                BitmapUtil.saveImageToGallery(bitmap, NebulaeActivity.this);
                                hideLoading();
                                ToastUtil.showToast("保存相册成功");
                            }

                            @Override
                            public void onFailure() {
                            }

                            @Override
                            public void onAlwaysFailure() {
                                PermissionDialog.showDialog(NebulaeActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE");
                            }
                        });
                    }, 500);

                    break;
            }
        }
    }


    public Activity getActivity() {
        return this;
    }

    @Override
    public void onNebulaListSuccess(NebulaListEntity data) {
        if (data != null) {
            if (data.getUnlock_total() > 0) {
                if (data.getUnlock_total() > 120) {
                    total = 120;
                } else {
                    total = data.getUnlock_total();
                }
                list = data.getList();
                if (list.size()>0){
                    for (int i = 0; i < total; i++) {
                        datas.get(i).setNebula(list.get(i).getNebula());
                        datas.get(i).setIntro(list.get(i).getIntro());
                        datas.get(i).setNebula_code(list.get(i).getNebula_code());
                        if (TextUtils.equals(nebula, list.get(i).getNebula_code())) {
                            x = datas.get(i).getX();
                            y = datas.get(i).getY();
                        }
                    }
                    newList = datas.subList(0, total);
                    lineChartView.setData(newList, nebula);
                    nebulaeListAdapter.setData(list);
                    if (x > 0) {
                        initListener();
                    }
                }
            }


            nebulaeListAdapter.setRecyclerItemClickListner((v, position) -> {
                if (position < newList.size()) {
                    if (list.get(position).getUnlock()==1){  //解锁了
                        nebulaeListAdapter.update(list.get(position).getNebula_code());
                        lineChartView.setData(newList, list.get(position).getNebula_code());
                        x = newList.get(position).getX();
                        y = datas.get(position).getY();
                        initListener();
                    }
                }
            });
        }
    }


    public void initListener() {
        HscrollView.post(() -> {
            //滚动到右边
            HscrollView.scrollTo((int) (x / 1.2), 0);
        });
        VscrollView.post(() -> {
            //滚动到右边
            VscrollView.scrollTo(0, (int) (y / 3));
        });
    }


    @Override
    public void onNetError() {

    }

    public void showLoading() {
        ViewUtil.create().show(this);
    }

    public void hideLoading() {
        ViewUtil.create().dismiss();
    }


    @Override
    public void showMessage(@NonNull String message) {

    }
}