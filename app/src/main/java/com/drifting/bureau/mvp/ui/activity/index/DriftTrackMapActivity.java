package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.ArcOptions;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;

import com.baidu.mapapi.map.OverlayOptions;

import com.baidu.mapapi.map.PolylineOptions;

import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.map.track.TraceAnimationListener;
import com.baidu.mapapi.map.track.TraceOptions;
import com.baidu.mapapi.map.track.TraceOverlay;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;

import com.drifting.bureau.R;
import com.drifting.bureau.app.application.RBureauApplication;
import com.drifting.bureau.base.BaseManagerActivity;
import com.drifting.bureau.data.event.BackSpaceEvent;
import com.drifting.bureau.data.event.PaymentEvent;
import com.drifting.bureau.data.event.TagEvent;
import com.drifting.bureau.data.event.VideoEvent;
import com.drifting.bureau.di.component.DaggerDriftTrackMapComponent;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;

import com.drifting.bureau.mvp.model.entity.MoreDetailsForMapEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;

import com.drifting.bureau.mvp.ui.activity.pay.PaymentInfoActivity;
import com.drifting.bureau.mvp.ui.dialog.MapSendDriftDialog;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;
import com.drifting.bureau.mvp.ui.dialog.RaftingOrderDialog;
import com.drifting.bureau.mvp.ui.dialog.ReleaseDriftingDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.BitmapUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;

import com.drifting.bureau.util.request.RequestUtil;

import com.jess.arms.di.component.AppComponent;

import com.drifting.bureau.mvp.contract.DriftTrackMapContract;
import com.drifting.bureau.mvp.presenter.DriftTrackMapPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.model.Conversation;


/**
 * Created on 2022/08/26 20:04
 *
 * @author 地图轨迹
 * module name is DriftTrackMapActivity
 */
public class DriftTrackMapActivity extends BaseManagerActivity<DriftTrackMapPresenter> implements DriftTrackMapContract.View, TraceAnimationListener {

    @BindView(R.id.mFrameLayout)
    FrameLayout mFrameLayout;
    @BindView(R.id.tv_open)
    TextView mTvOpen;
    @BindView(R.id.tv_from_name)
    TextView mTvFromName;
    @BindView(R.id.tv_to_name)
    TextView mTvToName;
    @BindView(R.id.tv_left_name)
    TextView mTvLeftName;
    @BindView(R.id.tv_right_name)
    TextView mTvRightName;
    @BindView(R.id.tv_add_left_friend)
    TextView mTvAddLeftFriend;
    @BindView(R.id.tv_add_right_friend)
    TextView mTvAddRightFriend;
    @BindView(R.id.iv_mastor_left)
    ImageView mIvMastorLeft;
    @BindView(R.id.iv_mastor_right)
    ImageView mIvMastorRight;
    @BindView(R.id.fl_to_name)
    FrameLayout mFlToName;
    @BindView(R.id.rl_to_right)
    RelativeLayout mRlRight;
    @BindView(R.id.ll_to_right)
    LinearLayout mLlRight;
    @BindView(R.id.iv_left_mask)
    ImageView mIvLeftMask;
    @BindView(R.id.iv_right_mask)
    ImageView mIvRightMask;
    @BindView(R.id.rl_left_add_friend)
    RelativeLayout mRlLeftAddFriends;
    @BindView(R.id.rl_right_add_friend)
    RelativeLayout mRlRIghtAddFriends;
    // 地图View实例
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private MapStatus.Builder builder;
    private DistrictSearch mDistrictSearch;
    private List<OverlayOptions> options;
    private LayoutInflater inflater;
    private List<InfoWindow> infoWindowList;
    private int status, explore_id, message_id, attend, message_id2, user_id, user_id2, total, postion, Msgtype, leftStatus, rightStatus;
    private String path;
    private UiSettings mUiSettings;
    private static String EXTRA_EXPLORE_ID = "extra_explore_id";
    private static String EXTRA_MESSAGE_ID = "extra_message_id";
    private static String EXTRA_TYPE = "extra_type";
    private boolean isNew = false;
    // 用于设置个性化地图的样式文件
    private static final String CUSTOM_FILE_NAME_GRAY = "dark.sty";
    private TraceOverlay mTraceOverlay;
    private InfoWindow mInfoWindow, mInfoWindow2;
    //发布漂流
    private MapSendDriftDialog mapSendDriftDialog;
    private PublicDialog publicDialog;
    private RaftingOrderDialog raftingOrderDialog;
    private BitmapDescriptor mbpStart = BitmapDescriptorFactory.fromResource(R.drawable.track_start);
    private BitmapDescriptor mbpCenter = BitmapDescriptorFactory.fromResource(R.drawable.track_center);
    private BitmapDescriptor mbpEnd = BitmapDescriptorFactory.fromResource(R.drawable.track_end);
    private BitmapDescriptor mBlueTexture = BitmapDescriptorFactory.fromAsset("Icon_road_blue_arrow.png");

    private MoreDetailsForMapEntity.MessageBean messageBean;
    private MoreDetailsForMapEntity.RelevanceBean relevanceBean;
    private MoreDetailsForMapEntity.FutureBean futureBea;
    private List<MoreDetailsForMapEntity.MessagePathBean> messagePathBeanList;


    public static void start(Context context, int type, int explore_id, int message_id, boolean closePage) {
        Intent intent = new Intent(context, DriftTrackMapActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_EXPLORE_ID, explore_id);
        intent.putExtra(EXTRA_MESSAGE_ID, message_id);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    public static void start(Context context, int explore_id, int message_id, boolean closePage) {
        Intent intent = new Intent(context, DriftTrackMapActivity.class);
        intent.putExtra(EXTRA_EXPLORE_ID, explore_id);
        intent.putExtra(EXTRA_MESSAGE_ID, message_id);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDriftTrackMapComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_drift_track_map; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true, R.color.color_01);
        if (getIntent() != null) {
            Msgtype = getIntent().getExtras().getInt(EXTRA_TYPE);
            explore_id = getIntent().getExtras().getInt(EXTRA_EXPLORE_ID);
            message_id = getIntent().getExtras().getInt(EXTRA_MESSAGE_ID);
        }
        initListener();
    }

    public void initListener() {
        mMapView = new MapView(this, new BaiduMapOptions());
        mBaiduMap = mMapView.getMap();
        mUiSettings = mBaiduMap.getUiSettings();
        mFrameLayout.addView(mMapView);
        //通过设置enable为true或false 选择是否启用地图旋转功能
        mUiSettings.setRotateGesturesEnabled(false);

        // 获取.sty文件路径
        String customStyleFilePath = getCustomStyleFilePath(this, CUSTOM_FILE_NAME_GRAY);
        // 设置个性化地图样式文件的路径和加载方式
        mMapView.setMapCustomStylePath(customStyleFilePath);
        // 动态设置个性化地图样式是否生效
        mMapView.setMapCustomStyleEnable(true);
        //设置当前缩放等级
        builder = new MapStatus.Builder();
        builder.zoom(10.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //通过设置enable为true或false 选择是否显示比例尺
        mMapView.showScaleControl(false);
        mMapView.showZoomControls(false);  //隐藏加减缩放按钮
        //隐藏底图标注
        mBaiduMap.showMapPoi(false);
        //隐藏底部+-按钮
        mBaiduMap.setMyLocationEnabled(true);

        //删除logo
        mMapView.removeViewAt(1);


        //设置缩放等级范围
        mBaiduMap.setMaxAndMinZoomLevel(13, 6);
        mDistrictSearch = DistrictSearch.newInstance();
        mDistrictSearch.setOnDistrictSearchListener(listener);

        getDetail(1);

        AddListener();
    }


    public void getDetail(int type) {
        if (mPresenter != null) {
            mPresenter.moreDetails(message_id, type);
        }
    }


    public void AddListener() {
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng latLng = marker.getPosition();
                if (marker.getZIndex() != options.size() - 1) {
                    mBaiduMap.hideInfoWindow();
                    showInfoWindow(latLng, marker.getZIndex());
                    if (messagePathBeanList.get(marker.getZIndex()).getUser_id() == messageBean.getUser_id()) {
                        setIsVisible(false);
                    } else {
                        setIsVisible(true);
                        getToUser(messagePathBeanList.get(marker.getZIndex()).getUser_id());
                    }
                }
                return true;
            }
        });
    }

    /**
     * @description 展示信息窗
     */
    public void showInfoWindow(LatLng latLng, int index) {
        this.postion = index;
        infoWindowList = new ArrayList<>();
        inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.layout_show_message, null, false);
        TextView mTvTitle = view.findViewById(R.id.tv_title);
        TextView mTvReceiveTime = view.findViewById(R.id.tv_receive_time);
        TextView mTvShopNo = view.findViewById(R.id.tv_shop_no);
        mTvTitle.setText(getString(R.string.from_city, messagePathBeanList.get(index).getName_city()));
        mTvShopNo.setText(messagePathBeanList.get(index).getShop_no());


        mInfoWindow = new InfoWindow(view, latLng, -80);
        infoWindowList.add(mInfoWindow);
        mTvReceiveTime.setOnClickListener(v -> {
            showDetails(postion);
        });

        TextView textView = new TextView(getApplicationContext());
        textView.setText("已传递等待漂出");
        textView.setTextSize(12);
        textView.setTextColor(getColor(R.color.white));
        mInfoWindow2 = new InfoWindow(textView, new LatLng(Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 1).getLat()), Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 1).getLng())), 80);
        infoWindowList.add(mInfoWindow2);

        mBaiduMap.showInfoWindows(infoWindowList);
    }


    public void showDetails(int type) {
        if (type == 0) {
            mPresenter.details(messageBean.getId(), 0, messagePathBeanList.get(0).getUser_id());
        } else {
            mPresenter.details(messagePathBeanList.get(type).getComment_id(), 1, messagePathBeanList.get(type).getUser_id());
        }
    }

    /**
     * 更新地图
     */
    private void upDataMapStatus() {
        if (null == mTraceOverlay || null == mBaiduMap) {
            return;
        }
        LatLngBounds latLngBounds = mTraceOverlay.getLatLngBounds();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(latLngBounds));
    }


    @Override
    public void onCommentDetailsSuccess(CommentDetailsEntity entity) {
        if (entity != null) {
            RequestUtil.create().platformtimes(explore_id, entity1 -> {
                if (entity1 != null && entity1.getCode() == 200) {
                    total = entity1.getData().getAttend_times() + entity1.getData().getCommon_times();
                    mapSendDriftDialog = new MapSendDriftDialog(getActivity(), 2, entity, relevanceBean, total);
                    mapSendDriftDialog.show();
                    mapSendDriftDialog.setOnContentClickCallback(content -> {
                        if (total > 0) {   //有免费次数
                            if (mPresenter != null) {
                                mPresenter.messageattending(entity.getMessage_id());
                            }
                        } else {
                            if (mPresenter != null) {
                                mPresenter.skulist(explore_id, entity.getMessage_id());
                            }
                        }
                    });

                    mapSendDriftDialog.setOnStarrySkyClickCallback((type, word, path, list, cover, tag) -> {
                        status = 2;
                        if (mPresenter != null) {
                            showLoading();
                            if (type == 1) {//视频
                                mPresenter.compressVideo(DriftTrackMapActivity.this, type, word, 0, path, list != null ? new File(list.get(0).toString()) : null, cover != null ? BitmapUtil.saveBitmapFile(getActivity(), cover) : null, tag);
                            } else {
                                mPresenter.createwithword(type, word, entity.getMessage_id(), path != null ? new File(path) : null, list != null ? new File(list.get(0).toString()) : null, cover != null ? BitmapUtil.saveBitmapFile(this, cover) : null, tag);
                            }
                        }
                    });
                    mapSendDriftDialog.setOnClickCallback(type -> {
                        if (type == ReleaseDriftingDialog.SELECT_FINISH) {

                            RequestUtil.create().messagethrow(message_id, entity2 -> {
                                if (entity2.getCode() == 200) {
                                    mapSendDriftDialog.dismiss();
                                    publicDialog = new PublicDialog(this);
                                    publicDialog.show();
                                    publicDialog.setCancelable(false);
                                    publicDialog.setTitleText("已丢回太空");
                                    publicDialog.setContentText("丢回太空的漂流信息\n将不会再次收到");
                                    publicDialog.setButtonText("返回星际");
                                    publicDialog.setOnClickCallback(type2 -> {
                                        if (type2 == PublicDialog.SELECT_FINISH) {
                                            finish();
                                            EventBus.getDefault().post(new BackSpaceEvent());
                                        }
                                    });
                                } else {
                                    showMessage(entity2.getMsg());
                                }
                            });

                        }
                    });

                }
            });
        }

    }

    @Override
    public void onMoreDetailsForMapSuccess(MoreDetailsForMapEntity entity, int type) {
        if (entity != null) {
            messageBean = entity.getMessage();
            relevanceBean = entity.getRelevance();
            futureBea = entity.getFuture();
            messagePathBeanList = entity.getMessage_path();
            attend = relevanceBean.getAttend();

            //展示开启新漂流
            mTvOpen.setVisibility(attend == 1 ? View.VISIBLE : View.GONE);


            if (messageBean.getId() == 0) {  //发起话题
//                 InitiateTopic();
                setIsVisible(false);
                if (!TextUtils.isEmpty(Preferences.getCity())) {
                    selectCity(Preferences.getCity());
                }
                getFromUser(Integer.parseInt(Preferences.getUserId()));
            } else {
                message_id2 = messageBean.getId();
                MoreDetailsForMapEntity.MessagePathBean messagePathBean = new MoreDetailsForMapEntity.MessagePathBean();
                messagePathBean.setUser_id(messageBean.getUser_id());
                messagePathBean.setComment_id(-1);
                messagePathBean.setLng(messageBean.getLng());
                messagePathBean.setLat(messageBean.getLat());
                messagePathBean.setName_city(messageBean.getName_city());
                messagePathBean.setHas_shop(messageBean.getHas_shop());
                messagePathBean.setShop_no(messageBean.getShop_no());
                messagePathBeanList.add(0, messagePathBean);
                MoreDetailsForMapEntity.MessagePathBean messagePathBean2 = new MoreDetailsForMapEntity.MessagePathBean();
                messagePathBean2.setLat(futureBea.getLat());
                messagePathBean2.setLng(futureBea.getLng());
                messagePathBeanList.add(messagePathBean2);

                if (messagePathBeanList.size() == 2) {
                    getFromUser(messageBean.getUser_id());
                    setIsVisible(false);
                } else {
                    getFromUser(messageBean.getUser_id());

                    getToUser(messagePathBeanList.get(messagePathBeanList.size() - 2).getUser_id());
                }

                selectCity(messageBean.getName_city());
                setMapOption();
            }

            if (type == 2) { // type=2 表示参与成功刷新dialog
                showDetails(postion);
            }
        }
    }

    @Override
    public void onMessageAttendingSuccess() {
        showParticipateDialog();
    }

    @Override
    public void onSkuListSuccess(SkuListEntity skuListEntity) {
        if (skuListEntity != null) {
            raftingOrderDialog = new RaftingOrderDialog(this, skuListEntity);
            raftingOrderDialog.show();
            raftingOrderDialog.setOnClickCallback(type -> {
                if (type == RaftingOrderDialog.SELECT_FINISH) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < skuListEntity.getGoods_sku().size(); i++) {
                        if (sb.length() > 0) {
                            sb.append(",");
                        }
                        sb.append(skuListEntity.getGoods_sku().get(i).getSku_code());
                    }
                    if (mPresenter != null) {
                        mPresenter.createOrder(message_id2, sb.toString());
                    }
                }
            });
        }

    }

    @Override
    public void onCreatewithwordSuccess(CreatewithfileEntity entity) {
        if (entity != null) {
            message_id2 = entity.getMessage_id();
            if (status == 1) {
                if (entity.getNeed_pay() == 1) {
                    if (mPresenter != null) {
                        mPresenter.skulist(1, 0);
                    }
                } else {
                    sendSuccess();
                }
            } else {
                EventBus.getDefault().post(new BackSpaceEvent());
                sendSuccess();
            }
        }
    }


    /**
     * 消息发送成功
     */
    public void sendSuccess() {
        refreshUi(1);
        mapSendDriftDialog.dismiss();
        publicDialog = new PublicDialog(this);
        publicDialog.show();
        publicDialog.setCancelable(false);
        publicDialog.setTitleText("已成功发送");
        publicDialog.setContentText("可在“关于我-漂流轨迹”中 查看漂流记录");
        publicDialog.setButtonText("确定");
    }


    public void refreshUi(int type) {
        removeTrace();
        mBaiduMap.clear();
        getDetail(type);  // 刷新界面
    }


    @Override
    public void onCreateOrderSuccess(CreateOrderEntity entity) {
        if (entity != null) {
            PaymentInfoActivity.start(this, 1, entity.getSn(), entity.getTotal_amount(), 0, false);
        }
    }


    public void showParticipateDialog() {
        publicDialog = new PublicDialog(this);
        publicDialog.show();
        publicDialog.setCancelable(false);
        publicDialog.setTitleText("参与成功");
        publicDialog.setContentText("快写下你的传递漂信息吧！");
        publicDialog.setOnClickCallback(type -> {
            if (type == PublicDialog.SELECT_FINISH) {
                if (mapSendDriftDialog != null) {
                    mapSendDriftDialog.dismiss();
                    refreshUi(2);
                }
            }
        });
    }

    /**
     * @description设置右侧接收人是否展示
     */
    public void setIsVisible(boolean type) {
        mFlToName.setVisibility(type ? View.VISIBLE : View.INVISIBLE);
        mRlRight.setVisibility(type ? View.VISIBLE : View.INVISIBLE);
        mLlRight.setVisibility(type ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * @description 发起人信息
     */
    public void getFromUser(int user_id) {
        this.user_id = user_id;
        RequestUtil.create().userplayer(user_id + "", entity -> {
            if (entity != null && entity.getCode() == 200) {
                mTvFromName.setText(entity.getData().getUser().getName());
                GlideUtil.create().loadLongImage(this, entity.getData().getUser().getMascot(), mIvMastorLeft);
                mTvLeftName.setText("来自" + entity.getData().getPlanet().getName());
                if (user_id == Integer.parseInt(Preferences.getUserId())) {
                    mRlLeftAddFriends.setVisibility(View.GONE);
                } else {
                    mRlLeftAddFriends.setVisibility(View.VISIBLE);
                    isFriend(user_id, 1);
                }
            }
        });
    }


    /**
     * @description 接收人信息
     */
    public void getToUser(int user_id) {
        this.user_id2 = user_id;
        RequestUtil.create().userplayer(user_id + "", entity -> {
            if (entity != null && entity.getCode() == 200) {
                mTvToName.setText(entity.getData().getUser().getName());
                GlideUtil.create().loadLongImage(this, entity.getData().getUser().getMascot(), mIvMastorRight);
                mTvRightName.setText("来自" + entity.getData().getPlanet().getName());
                if (user_id == Integer.parseInt(Preferences.getUserId())) {
                    mRlRIghtAddFriends.setVisibility(View.GONE);
                } else {
                    mRlRIghtAddFriends.setVisibility(View.VISIBLE);
                    isFriend(user_id, 2);
                }
            }
        });
    }


    public void isFriend(int user_id, int type) {
        RequestUtil.create().isFriend(user_id + "", entity1 -> {
            if (entity1 != null && entity1.getCode() == 200) {
                int status = entity1.getData().getStatus();
                if (type == 1) {
                    this.leftStatus = status;
                } else {
                    this.rightStatus = status;
                }
                setIsLock(status, type);
            }
        });

    }

    public void setIsLock(int status, int type) {
        if (attend == 1) {   //已经参与话题了
            if (type == 1) {
                mIvLeftMask.setVisibility(View.GONE);
            } else {
                mIvRightMask.setVisibility(View.GONE);
            }
            if (status == 0) {//未申请
                if (type == 1) {
                    mRlLeftAddFriends.setClickable(true);
                } else {
                    mRlRIghtAddFriends.setClickable(true);
                }
                setText("添加好友", type);
            } else if (status == 1) {//已申请
                if (type == 1) {
                    mRlLeftAddFriends.setClickable(false);
                } else {
                    mRlRIghtAddFriends.setClickable(false);
                }
                setText("已申请", type);
            } else if (status == 2) {//已经是好友
                if (type == 1) {
                    mRlLeftAddFriends.setClickable(true);
                } else {
                    mRlRIghtAddFriends.setClickable(true);
                }
                setText("与Ta聊天", type);
            }
        } else {
            if (status == 0) {//未申请
                if (type == 1) {
                    mRlLeftAddFriends.setClickable(false);
                    mIvLeftMask.setVisibility(View.VISIBLE);
                } else {
                    mRlRIghtAddFriends.setClickable(false);
                    mIvRightMask.setVisibility(View.VISIBLE);
                }
                setText("添加好友", type);
            } else if (status == 1) {//已申请
                if (type == 1) {
                    mRlLeftAddFriends.setClickable(false);
                    mIvLeftMask.setVisibility(View.GONE);
                } else {
                    mRlRIghtAddFriends.setClickable(false);
                    mIvRightMask.setVisibility(View.GONE);
                }
                setText("已申请", type);
            } else if (status == 2) {//已经是好友
                if (type == 1) {
                    mRlLeftAddFriends.setClickable(true);
                    mIvLeftMask.setVisibility(View.GONE);
                } else {
                    mRlRIghtAddFriends.setClickable(true);
                    mIvRightMask.setVisibility(View.GONE);
                }
                setText("与Ta聊天", type);
            }
        }
    }

    public void setText(String content, int type) {
        if (type == 1) {
            mTvAddLeftFriend.setText(content);
        } else {
            mTvAddRightFriend.setText(content);
        }
    }

    public void selectCity(String name) {
        mDistrictSearch.searchDistrict(new DistrictSearchOption()
                .cityName(name)
                .districtName(""));
    }

    /**
     * @description 设置地图上的icon
     */
    public void setMapOption() {
        //创建OverlayOptions的集合
        options = new ArrayList<OverlayOptions>();
        for (int i = 0; i < messagePathBeanList.size(); i++) {
            OverlayOptions option = new MarkerOptions().position(new LatLng(Double.parseDouble(messagePathBeanList.get(i).getLat()), Double.parseDouble(messagePathBeanList.get(i).getLng())))
                    .icon(getIcon(i))
                    .perspective(false) // 设置是否开启 marker 覆盖物近大远小效果，默认开启
                    .anchor(0.5f, 0.5f) // 设置 marker 覆盖物的锚点比例，默认（0.5f, 1.0f）水平居中，垂直下对齐
                    .zIndex(i);
            options.add(option);
        }
        //在地图上批量添加
        mBaiduMap.addOverlays(options);

        //添加轨迹
        if (messagePathBeanList.size() == 2) {
            setLastLine();
        } else if (messagePathBeanList.size() == 3) {
            LatLng p1 = new LatLng(Double.parseDouble(messagePathBeanList.get(0).getLat()), Double.parseDouble(messagePathBeanList.get(0).getLng()));//起点
            LatLng p2 = new LatLng((Double.parseDouble(messagePathBeanList.get(0).getLat()) + Double.parseDouble(messagePathBeanList.get(1).getLat())) / 2, (Double.parseDouble(messagePathBeanList.get(0).getLng()) + Double.parseDouble(messagePathBeanList.get(1).getLng())) / 2);//中间点
            LatLng p3 = new LatLng(Double.parseDouble(messagePathBeanList.get(1).getLat()), Double.parseDouble(messagePathBeanList.get(1).getLng()));//终点
            OverlayOptions mArcOptions = new ArcOptions()
                    .color(0xAA6CECC3)
                    .width(6)
                    .points(p1, p2, p3);
            //在地图上显示弧线
            mBaiduMap.addOverlay(mArcOptions);
            upDataMapStatus();

            setLastLine();

        } else {
            traceOption();
        }
    }


    public void traceOption() {

        //通过设置enable为true或false 选择是否启用地图缩放手势
        mUiSettings.setZoomGesturesEnabled(false);

        TraceOptions traceOptions = initTraceOptions();
        traceOptions.setTrackMove(true);
        // 添加轨迹动画
        mTraceOverlay = mBaiduMap.addTraceOverlay(traceOptions, this);
        upDataMapStatus();
    }


    /**
     * 配置轨迹参数
     */
    private TraceOptions initTraceOptions() {
        TraceOptions traceOptions = new TraceOptions();
        traceOptions.animationTime(getTime());
        traceOptions.animate(true);
        traceOptions.animationType(TraceOptions.TraceAnimateType.TraceOverlayAnimationEasingCurveLinear);
        traceOptions.color(0xAA6CECC3);
        traceOptions.width(6);
        traceOptions.points(getTraceLocation());
        return traceOptions;
    }


    /**
     * @description 设置动画时间
     */

    public int getTime() {
        if (messagePathBeanList.size() - 1 == 2) {
            return 1000;
        } else if (messagePathBeanList.size() - 1 == 3) {
            return 2000;
        } else {
            return 4000;
        }
    }

    /**
     * 获取轨迹点
     *
     * @return 经纬度list
     */
    private List<LatLng> getTraceLocation() {
        List<LatLng> points = new ArrayList<LatLng>();
        for (int i = 0; i < messagePathBeanList.size() - 1; i++) {
            points.add(new LatLng(Double.parseDouble(messagePathBeanList.get(i).getLat()), Double.parseDouble(messagePathBeanList.get(i).getLng())));
        }
        return points;
    }


    public BitmapDescriptor getIcon(int i) {
        if (i == 0) {
            return mbpStart;
        } else {
            if (i == messagePathBeanList.size() - 1) {
                return mbpEnd;
            } else {
                return mbpCenter;
            }
        }
    }

    @Override
    public void showLoading() {
        ViewUtil.create().show(this);
    }

    @Override
    public void hideLoading() {
        ViewUtil.create().dismiss();
    }


    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }


    /**
     * @description 获取assets下的文件
     */
    private String getCustomStyleFilePath(Context context, String customStyleFileName) {
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String parentPath = null;

        try {
            inputStream = context.getAssets().open("customConfigdir/" + customStyleFileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);

            parentPath = context.getFilesDir().getAbsolutePath();
            File customStyleFile = new File(parentPath + "/" + customStyleFileName);
            if (customStyleFile.exists()) {
                customStyleFile.delete();
            }
            customStyleFile.createNewFile();

            outputStream = new FileOutputStream(customStyleFile);
            outputStream.write(buffer);
        } catch (IOException e) {
            Log.e("CustomMapDemo", "Copy custom style file failed", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                Log.e("CustomMapDemo", "Close stream failed", e);
                return null;
            }
        }

        return parentPath + "/" + customStyleFileName;
    }


    /**
     * @description 添加边界线
     */

    OnGetDistricSearchResultListener listener = new OnGetDistricSearchResultListener() {
        @Override
        public void onGetDistrictResult(DistrictResult districtResult) {
            if (districtResult == null) {
                return;
            }
            if (districtResult.error == SearchResult.ERRORNO.NO_ERROR) {
                List<List<LatLng>> polyLines = districtResult.getPolylines();
                if (polyLines == null) {
                    return;
                }
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (List<LatLng> polyline : polyLines) {
                    OverlayOptions ooPolyline = new PolylineOptions().width(5).points(polyline).color(getColor(R.color.color_6a));
                    mBaiduMap.addOverlay(ooPolyline);
                    for (LatLng latLng : polyline) {
                        builder.include(latLng);
                    }
                }
                if (isNew) {
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build()));
                    showNewInfoWindow(RBureauApplication.latLng);
                }
            }
        }
    };


    @Override
    public void onTraceAnimationUpdate(int percent) {
        Log.e("TAG==", "onTraceAnimationUpdate: " + percent);
    }

    @Override
    public void onTraceUpdatePosition(LatLng latLng) {
        Log.e("TAG==", "onTraceUpdatePosition: " + latLng.toString());
    }

    @Override
    public void onTraceAnimationFinish() {
        Log.e("TAG==", "onTraceAnimationFinish: ");
        mUiSettings.setZoomGesturesEnabled(true);
        setLastLine();
    }


    /**
     * @description 覆盖物参数配置
     */

    public void setLastLine() {
        OverlayOptions ooPolylineAA = new PolylineOptions().width(6)
                .points(getTrace())
                .dottedLine(true) // 设置折线是否虚线
                .customTexture(mBlueTexture);// 设置折线多纹理分段绘制的纹理队列
        mBaiduMap.addOverlay(ooPolylineAA);

        if (messagePathBeanList.size() == 2) {
            showInfoWindow(new LatLng(Double.parseDouble(messagePathBeanList.get(0).getLat()), Double.parseDouble(messagePathBeanList.get(0).getLng())), 0);
        } else {
            showInfoWindow(new LatLng(Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 2).getLat()), Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 2).getLng())), messagePathBeanList.size() - 2);
        }

        perfomRotate(0);
    }


    /**
     * 最后一个轨迹点
     *
     * @return 经纬度list
     */
    private List<LatLng> getTrace() {
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(new LatLng(Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 1).getLat()), Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 1).getLng())));
        points.add(new LatLng(Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 2).getLat()), Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 2).getLng())));
        return points;
    }


    /**
     * 处理旋转 旋转角范围： 0 ~ 360 , 单位：度
     */
    public void perfomRotate(int rote) {
        MapStatus mapStatus = mBaiduMap.getMapStatus();
        if (null != mapStatus) {
            MapStatus build = new MapStatus.Builder(mapStatus).rotate(rote).build();
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(build);
            mBaiduMap.animateMapStatus(mapStatusUpdate);
        }
    }

    /**
     * @description 拍照/录制视频完成后绑定界面
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void VideoEvent(VideoEvent event) {
        if (event != null) {
            path = event.getPath();
            mapSendDriftDialog.setData(event.getType(), path);
        }
    }


    /**
     * @description 添加标签
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void TagEvent(TagEvent event) {
        if (event != null) {
            mapSendDriftDialog.setTag(event.getTag(), event.getTagname());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void PaymentEvent(PaymentEvent event) {  //购买成功回调
        if (event != null) {
            sendSuccess();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时必须调用mMapView. onPause ()
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放检索实例
        mDistrictSearch.destroy();
        //移除图层
        removeTrace();
        // 清除所有图层
        mBaiduMap.clear();
        // 在activity执行onDestroy时必须调用mMapView.onDestroy()
        mMapView.onDestroy();
    }

    /**
     * 移除图层
     */
    private void removeTrace() {
        if (null != mTraceOverlay) {
            mTraceOverlay.clear(); // 清除轨迹数据，但不会移除轨迹覆盖物
            mTraceOverlay.remove(); // 移除轨迹覆盖物
        }
    }

    @OnClick({R.id.toolbar_back, R.id.tv_open, R.id.rl_left_add_friend, R.id.rl_right_add_friend})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_open:  //开启漂流
                    // 清除所有图层
                    if (RBureauApplication.latLng != null) {
                        removeTrace();
                        mBaiduMap.clear();
                        isNew = true;
                        if (!TextUtils.isEmpty(Preferences.getCity())) {
                            selectCity(Preferences.getCity());
                        }
                    }
                    break;
                case R.id.rl_left_add_friend:  //左边添加好友
                    if (leftStatus == 2) {
                        startIM(user_id, mTvFromName.getText().toString());
                    } else {
                        friendapply(user_id, 1);
                    }
                    break;
                case R.id.rl_right_add_friend: //右边添加好友
                    if (rightStatus == 2) {
                        startIM(user_id2, mTvToName.getText().toString());
                    } else {
                        friendapply(user_id2, 2);
                    }

                    break;
            }
        }
    }


    public void startIM(int user_id, String name) {
        String targetId = user_id + "";//这个是对方的id
        Bundle bundle = new Bundle();
        bundle.putString(RouteUtils.TITLE, name); //会话页面标题
        RouteUtils.routeToConversationActivity(this, Conversation.ConversationType.PRIVATE, targetId, bundle);
    }

    /**
     * @description 开启新漂流
     */
    public void showNewInfoWindow(LatLng latLng) {

        OverlayOptions option = new MarkerOptions().position(latLng)
                .icon(mbpStart)
                .perspective(false) // 设置是否开启 marker 覆盖物近大远小效果，默认开启
                .anchor(0.5f, 0.5f); // 设置 marker 覆盖物的锚点比例，默认（0.5f, 1.0f）水平居中，垂直下对齐

        mBaiduMap.addOverlay(option);

        inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.layout_start_drifting, null, false);
        FrameLayout mFlNewMessage = view.findViewById(R.id.fl_new_message);
        mInfoWindow = new InfoWindow(view, latLng, -80);
        mFlNewMessage.setOnClickListener(v -> {
            OpenNewMsg();
        });
        mBaiduMap.showInfoWindow(mInfoWindow);

        builder.zoom(10.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

    }

    public void OpenNewMsg() {
        RequestUtil.create().platformtimes(explore_id, entity -> {
            if (entity != null && entity.getCode() == 200) {
                total = entity.getData().getAttend_times() + entity.getData().getCommon_times();
                mapSendDriftDialog = new MapSendDriftDialog(getActivity(), 1, total);
                mapSendDriftDialog.show();
                mapSendDriftDialog.setOnStarrySkyClickCallback((type, word, path, list, cover, tag) -> {
                    status = 1;
                    if (mPresenter != null) {
                        showLoading();
                        if (type == 1) { //视频
                            mPresenter.compressVideo(DriftTrackMapActivity.this, type, word, 0, path, list != null ? new File(list.get(0).toString()) : null, cover != null ? BitmapUtil.saveBitmapFile(getActivity(), cover) : null, tag);
                        } else {
                            mPresenter.createwithword(type, word, 0, path != null ? new File(path) : null, list != null ? new File(list.get(0).toString()) : null, cover != null ? BitmapUtil.saveBitmapFile(getActivity(), cover) : null, tag);
                        }
                    }
                });

            }
        });

    }


    public void friendapply(int user_id, int type) {
        RequestUtil.create().friendapply(user_id + "", entity -> {
            if (entity != null) {
                if (entity.getCode() == 200) {
                    if (type == 1) {
                        mRlLeftAddFriends.setClickable(false);
                        mTvAddLeftFriend.setText("已申请");
                    } else {
                        mRlRIghtAddFriends.setClickable(false);
                        mTvAddRightFriend.setText("已申请");
                    }
                    ToastUtil.showAddFriendDialog(this);
                } else {
                    showMessage(entity.getMsg());
                }
            }
        });

    }
}