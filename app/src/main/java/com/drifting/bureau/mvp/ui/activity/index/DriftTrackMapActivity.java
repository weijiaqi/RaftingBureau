package com.drifting.bureau.mvp.ui.activity.index;

import android.animation.ValueAnimator;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.animation.Animation;
import com.baidu.mapapi.animation.ScaleAnimation;
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

import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;

import com.baidu.mapapi.map.Polyline;
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
import com.drifting.bureau.data.entity.LatLngEntity;
import com.drifting.bureau.data.event.BackSpaceEvent;
import com.drifting.bureau.data.event.PaymentEvent;
import com.drifting.bureau.data.event.TagEvent;
import com.drifting.bureau.data.event.VideoEvent;
import com.drifting.bureau.di.component.DaggerDriftTrackMapComponent;
import com.drifting.bureau.mvp.model.entity.BoxEntity;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderOpenBoxEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsForMapEntity;

import com.drifting.bureau.mvp.model.entity.SkuListEntity;

import com.drifting.bureau.mvp.ui.activity.pay.PaymentInfoActivity;

import com.drifting.bureau.mvp.ui.dialog.BoxPasswordDialog;
import com.drifting.bureau.mvp.ui.dialog.CityReleaseDialog;
import com.drifting.bureau.mvp.ui.dialog.EnablePrivilegesDialog;

import com.drifting.bureau.mvp.ui.dialog.ListPrizesDialog;
import com.drifting.bureau.mvp.ui.dialog.MapSendDriftDialog;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;
import com.drifting.bureau.mvp.ui.dialog.RaftingOrderDialog;
import com.drifting.bureau.mvp.ui.dialog.ReleaseDriftingDialog;
import com.drifting.bureau.mvp.ui.dialog.ShareBoxDialog;
import com.drifting.bureau.mvp.ui.dialog.WelfareVoucherDialog;
import com.drifting.bureau.mvp.ui.dialog.WinningAddressDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.BitmapUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.ViewUtil;

import com.drifting.bureau.util.request.RequestUtil;
import com.drifting.bureau.view.guide.MapOpenMsgGuideView;

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
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.model.Conversation;


/**
 * Created on 2022/08/26 20:04
 *
 * @author ????????????
 * module name is DriftTrackMapActivity
 */
public class DriftTrackMapActivity extends BaseManagerActivity<DriftTrackMapPresenter> implements DriftTrackMapContract.View, TraceAnimationListener {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.mFrameLayout)
    FrameLayout mFrameLayout;
    @BindView(R.id.tv_open)
    TextView mTvOpen;
    @BindView(R.id.tv_from_name)
    TextView mTvFromName;
    @BindView(R.id.tv_to_name)
    TextView mTvToName;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
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
    @BindView(R.id.guide_view)
    MapOpenMsgGuideView mOpenGuideView;
    // ??????View??????
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private MapStatus.Builder builder;
    private DistrictSearch mDistrictSearch;
    private List<OverlayOptions> options, optionsBox;
    private LayoutInflater inflater;
    private List<InfoWindow> infoWindowList, infoBoxWindowList;
    private int status, explore_id, message_id, attend, CreateTopicId, user_id, user_id2, total, postion, Msgtype, leftStatus, rightStatus;
    private int PermisType;
    private String path;
    private UiSettings mUiSettings;
    private static String EXTRA_EXPLORE_ID = "extra_explore_id";
    private static String EXTRA_MESSAGE_ID = "extra_message_id";
    private static String EXTRA_TYPE = "extra_type";
    private boolean isNew = false;
    // ??????????????????????????????????????????
    private static final String CUSTOM_FILE_NAME_GRAY = "dark.sty";
    private TraceOverlay mTraceOverlay;
    private InfoWindow mInfoWindow, mLastInfoWindow, mBoxInfoWindow;

    private List<Overlay> mMarkerOpenList, mMarkerBox;
    //????????????
    private MapSendDriftDialog mapSendDriftDialog;
    private PublicDialog publicDialog;
    private RaftingOrderDialog raftingOrderDialog;
    private CityReleaseDialog cityReleaseDialog;
    private EnablePrivilegesDialog enablePrivilegesDialog;
    private ShareBoxDialog shareBoxDialog;
    private WinningAddressDialog winningAddressDialog;
    private BoxPasswordDialog boxPasswordDialog;
    private WelfareVoucherDialog welfareVoucherDialog;
    private ListPrizesDialog listPrizesDialog;
    private BitmapDescriptor finger = BitmapDescriptorFactory.fromResource(R.drawable.map_finger);

    BitmapDescriptor[] mbppic = {BitmapDescriptorFactory.fromResource(R.drawable.track_start), BitmapDescriptorFactory.fromResource(R.drawable.track_center), BitmapDescriptorFactory.fromResource(R.drawable.track_end)};

    BitmapDescriptor[] boxpic = {BitmapDescriptorFactory.fromResource(R.drawable.free_box), BitmapDescriptorFactory.fromResource(R.drawable.toll_lock_box), BitmapDescriptorFactory.fromResource(R.drawable.toll_unlock_box)};

    private MoreDetailsForMapEntity.MessageBean messageBean;
    private MoreDetailsForMapEntity.RelevanceBean relevanceBean;
    private MoreDetailsForMapEntity.FutureBean futureBea;
    private List<MoreDetailsForMapEntity.MessagePathBean> messagePathBeanList;
    private CommentDetailsEntity commentDetailsEntity;
    private List<LatLngEntity> latLngEntityList;
    private List<BoxEntity> boxEntityList;
    private Marker mMarker;

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
        DaggerDriftTrackMapComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_drift_track_map; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        Msgtype = getInt(EXTRA_TYPE);
        explore_id = getInt(EXTRA_EXPLORE_ID);
        message_id = getInt(EXTRA_MESSAGE_ID);
        if (message_id != 0) {
            mIvRight.setVisibility(View.VISIBLE);
            mIvRight.setImageResource(R.drawable.tran_detail);
        }

        //??????????????????
        mOpenGuideView.setVisibility(!Preferences.isPostGuide() ? View.VISIBLE : View.GONE);
        mOpenGuideView.setOnClickCallback(() -> {
            mOpenGuideView.setVisibility(View.GONE);
            if (RBureauApplication.latLng != null) {
                SendNewMesg(false);
            }
        });


        initListener();
    }

    public void initListener() {
        mMapView = new MapView(this, new BaiduMapOptions());
        // ??????.sty????????????
        String customStyleFilePath = getCustomStyleFilePath(this, CUSTOM_FILE_NAME_GRAY);
        // ?????????????????????????????????????????????????????????
        mMapView.setMapCustomStylePath(customStyleFilePath);
        // ?????????????????????????????????????????????
        mMapView.setMapCustomStyleEnable(true);
        //????????????enable???true???false ???????????????????????????
        mMapView.showScaleControl(false);
        mMapView.showZoomControls(false);  //????????????????????????
        //??????logo
        mMapView.removeViewAt(1);
        mFrameLayout.addView(mMapView);
        mBaiduMap = mMapView.getMap();
        mUiSettings = mBaiduMap.getUiSettings();
        //????????????enable???true???false ????????????????????????????????????
        mUiSettings.setRotateGesturesEnabled(false);
        //????????????????????????
        builder = new MapStatus.Builder();
        builder.zoom(10.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //??????????????????
        mBaiduMap.showMapPoi(false);
        //????????????+-??????
        mBaiduMap.setMyLocationEnabled(true);
        //????????????????????????
        mBaiduMap.setMaxAndMinZoomLevel(13, 6);
        mDistrictSearch = DistrictSearch.newInstance();
        mDistrictSearch.setOnDistrictSearchListener(listener);

        inflater = LayoutInflater.from(getApplicationContext());

        if (Msgtype == 1) {  //???????????????
            openNewDrift();
            if (RBureauApplication.latLng != null) {
                OpenNewMsg();
            }
        } else if (Msgtype == 2) { //??????
            openNewDrift();
        } else {
            getDetail(1, message_id);
        }

        AddListener();
    }


    public void getDetail(int type, int id) {
        if (mPresenter != null) {
            mPresenter.moreDetails(id, type);
        }
    }

    public void getBox() {
        if (mPresenter != null) {
            mPresenter.getBox();
        }
    }

    public void AddListener() {
        mBaiduMap.setOnMarkerClickListener(marker -> {
            LatLng latLng = marker.getPosition();
            if (mMarkerBox.contains(marker)) {   //????????????
                if (boxEntityList.get(marker.getZIndex()).getType() == 1) {
                    boxPasswordDialog = new BoxPasswordDialog(this);
                    boxPasswordDialog.show();
                    boxPasswordDialog.setOnContentClickCallback(content -> {
                        openBox(marker, content);
                    });
                } else {  //1?????????2??????
                    if (boxEntityList.get(marker.getZIndex()).getEquity() == 1) {
                        mMarker = marker;
                        enablePrivilegesDialog = new EnablePrivilegesDialog(this);
                        enablePrivilegesDialog.show();
                        enablePrivilegesDialog.setOnClickCallback(type -> {
                            if (type == EnablePrivilegesDialog.OPEN_PRIVILEGE) {
                                if (mPresenter != null) {
                                    mPresenter.createOrderOpenBoxDaily(boxEntityList.get(marker.getZIndex()).getKey() + "", 1);
                                }
                            }
                        });
                    } else {
                        openBox(marker, "");
                    }
                }
            } else {
                if (mMarkerOpenList != null && mMarkerOpenList.contains(marker)) {
                    return false;
                }
                if (marker.getZIndex() != options.size() - 1) {
                    mBaiduMap.hideInfoWindow(mInfoWindow);
                    showInfoWindow(latLng, marker.getZIndex());
                    if (messagePathBeanList.get(marker.getZIndex()).getUser_id() == messageBean.getUser_id()) {
                        setIsVisible(false);
                    } else {
                        setIsVisible(true);
                        getToUser(messagePathBeanList.get(marker.getZIndex()).getUser_id());
                    }
                } else {
                    setIsVisible(true);
                    getToUser(messagePathBeanList.get(marker.getZIndex()).getUser_id());
                }
            }

            return true;
        });
    }


    public void openBox(Marker marker, String code) {
        RequestUtil.create().openbox(boxEntityList.get(marker.getZIndex()).getKey(), boxEntityList.get(marker.getZIndex()).getType(), code, boxEntityList.get(marker.getZIndex()).getIs_kongtou(),entity1 -> {
            if (entity1.getCode() == 200) {
                if (boxPasswordDialog != null) {
                    boxPasswordDialog.dismiss();
                }

                marker.remove();
                mBaiduMap.hideInfoWindow(infoBoxWindowList.get(marker.getZIndex()));

                if (entity1.getData().getIs_fictitious() == 1) {  //??????
                    welfareVoucherDialog = new WelfareVoucherDialog(this, entity1.getData());
                    welfareVoucherDialog.show();
                } else {  //??????
                    winningAddressDialog = new WinningAddressDialog(this, entity1.getData().getImage());
                    winningAddressDialog.show();
                    winningAddressDialog.setOnAddressClickCallback((name, phone, address) -> {
                        RequestUtil.create().addexpress(entity1.getData().getSafe_box_open_record_id(), name, phone, address, entity2 -> {
                            if (entity2 != null && entity2.getCode() == 200) {
                                winningAddressDialog.dismiss();
                                publicDialog = new PublicDialog(this);
                                publicDialog.show();
                                publicDialog.setCancelable(false);
                                publicDialog.setTitleText("??????");
                                publicDialog.setContentText("?????????????????????");
                                publicDialog.setButtonText("??????");
                            } else {
                                showMessage(entity2.getMsg());
                            }
                        });
                    });
                }

            } else {
                showMessage(entity1.getMsg());
            }
        });

    }

    /**
     * @description ???????????????
     */
    public void showInfoWindow(LatLng latLng, int index) {
        this.postion = index;
        infoWindowList = new ArrayList<>();

        View view = inflater.inflate(R.layout.layout_show_message, null, false);
        TextView mTvCityMore = view.findViewById(R.id.tv_city_more);
        TextView mTvTitle = view.findViewById(R.id.tv_title);
        TextView mTvReceiveTime = view.findViewById(R.id.tv_receive_time);
        TextView mTvShopNo = view.findViewById(R.id.tv_shop_no);
        mTvTitle.setText(getString(R.string.from_city, messagePathBeanList.get(index).getName_city()));
        mTvCityMore.setVisibility(messagePathBeanList.get(index).getCity_attend() > 1 ? View.VISIBLE : View.GONE);
        if (messagePathBeanList.get(index).getHas_shop() == 0) {
            mTvShopNo.setVisibility(View.INVISIBLE);
        } else {
            mTvShopNo.setVisibility(View.INVISIBLE);
            mTvShopNo.setText("???" + messagePathBeanList.get(index).getShop_no() + "???");
        }
        //???????????????????????????
        mTvCityMore.setOnClickListener(view1 -> {
            DeliveryDetailsActivity.start(this, messageBean.getId(), messagePathBeanList.get(index).getCode_city(), false);
        });

        mInfoWindow = new InfoWindow(view, latLng, -80);
        infoWindowList.add(mInfoWindow);
        mTvReceiveTime.setOnClickListener(v -> {
            showInfoWindowDetails(postion);
        });
        TextView textView = new TextView(getApplicationContext());
        textView.setText("?????????????????????");
        textView.setTextSize(12);
        textView.setTextColor(getColor(R.color.white));
        mLastInfoWindow = new InfoWindow(textView, new LatLng(Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 1).getLat()), Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 1).getLng())), 80);
        infoWindowList.add(mLastInfoWindow);
        mBaiduMap.showInfoWindows(infoWindowList);
    }


    public void showInfoWindowDetails(int click) {
        if (click == 0) {
            mPresenter.details(messageBean.getId(), 0, messagePathBeanList.get(0).getUser_id());
        } else {
            mPresenter.details(messagePathBeanList.get(click).getComment_id(), 1, messagePathBeanList.get(click).getUser_id());
        }
    }

    /**
     * ????????????
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
            commentDetailsEntity = entity;
            RequestUtil.create().platformtimes(explore_id, entity1 -> {
                if (entity1 != null && entity1.getCode() == 200) {
                    total = entity1.getData().getAttend_times() + entity1.getData().getCommon_times();
                    mapSendDriftDialog = new MapSendDriftDialog(getActivity(), 2, commentDetailsEntity, relevanceBean, total, messageBean.getFree());
                    mapSendDriftDialog.show();
                    mapSendDriftDialog.setOnContentClickCallback(content -> {
                        if (RBureauApplication.latLng != null) {
                            participate();
                        } else {
                            if (mPresenter != null) {  //????????????
                                PermisType = 2;
                                mPresenter.getLocation(this);
                            }
                        }
                    });

                    mapSendDriftDialog.setOnStarrySkyClickCallback((type, word, path, list, cover, tag) -> {  //??????
                        status = 2;
                        if (mPresenter != null) {
                            showLoading();
                            if (type == 1) {//??????
                                mPresenter.compressVideo(DriftTrackMapActivity.this, type, word, commentDetailsEntity.getMessage_id(), path, list != null ? new File(list.get(0).toString()) : null, cover != null ? BitmapUtil.saveBitmapFile(getActivity(), cover) : null, tag, messageBean.getFree());
                            } else {
                                mPresenter.createwithword(type, word, commentDetailsEntity.getMessage_id(), path != null ? new File(path) : null, list != null ? new File(list.get(0).toString()) : null, cover != null ? BitmapUtil.saveBitmapFile(this, cover) : null, tag, messageBean.getFree());
                            }
                        }
                    });
                    mapSendDriftDialog.setOnClickCallback(type -> {
                        if (type == ReleaseDriftingDialog.SELECT_FINISH) {
                            RequestUtil.create().messagethrow(message_id, entity2 -> {
                                if (entity2 != null && entity2.getCode() == 200) {
                                    mapSendDriftDialog.dismiss();
                                    publicDialog = new PublicDialog(this);
                                    publicDialog.show();
                                    publicDialog.setCancelable(false);
                                    publicDialog.setTitleText("???????????????");
                                    publicDialog.setContentText("???????????????????????????\n?????????????????????");
                                    publicDialog.setButtonText("????????????");
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


    /**
     * @description ????????????
     */
    public void participate() {
        if (total > 0 || messageBean.getFree() == 1) {   //???????????????
            if (mPresenter != null) {
                mPresenter.messageattending(commentDetailsEntity.getMessage_id());
            }
        } else {
            if (mPresenter != null) {
                mPresenter.skulist(explore_id, commentDetailsEntity.getMessage_id());
            }
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
            //?????????????????????
            mTvOpen.setVisibility(attend == 1 ? View.VISIBLE : View.GONE);
            if (messageBean.getId() == 0) {  //????????????
                openNewDrift();
            } else {
                MoreDetailsForMapEntity.MessagePathBean messagePathBean = new MoreDetailsForMapEntity.MessagePathBean();
                messagePathBean.setUser_id(messageBean.getUser_id());
                messagePathBean.setComment_id(-1);
                messagePathBean.setLng(messageBean.getLng());
                messagePathBean.setLat(messageBean.getLat());
                messagePathBean.setName_city(messageBean.getName_city());
                messagePathBean.setHas_shop(messageBean.getHas_shop());
                messagePathBean.setShop_no(messageBean.getShop_no());
                messagePathBean.setCity_attend(messageBean.getCity_attend());
                messagePathBean.setCode_city(messageBean.getCode_city());
                messagePathBeanList.add(0, messagePathBean);
                MoreDetailsForMapEntity.MessagePathBean messagePathBean2 = new MoreDetailsForMapEntity.MessagePathBean();
                messagePathBean2.setCode_city(messagePathBean2.getCode_city());
                messagePathBean2.setUser_id(futureBea.getUser_id());
                messagePathBean2.setLat(futureBea.getLat());
                messagePathBean2.setLng(futureBea.getLng());
                messagePathBeanList.add(messagePathBean2);

                getFromUser(messageBean.getUser_id());
                getToUser(messagePathBeanList.get(messagePathBeanList.size() - 1).getUser_id());
                selectCity(messageBean.getName_city_complete());
            }

            if (type == 2) { // type=2 ????????????????????????dialog
                showInfoWindowDetails(postion);
            }

        }
    }

    @Override
    public void onMessageAttendingSuccess() {
        showParticipateDialog();
    }

    @Override
    public void onSkuListSuccess(SkuListEntity skuListEntity,int id) {
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
                        mPresenter.createOrder(CreateTopicId, sb.toString(),id);
                    }
                }
            });
        }

    }

    @Override
    public void onCreatewithwordSuccess(CreatewithfileEntity entity) {
        if (entity != null) {
            CreateTopicId = entity.getMessage_id();
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
     * ??????????????????
     */
    public void sendSuccess() {
        refreshUi(1);
        mapSendDriftDialog.dismiss();
        publicDialog = new PublicDialog(this);
        publicDialog.show();
        publicDialog.setCancelable(false);
        publicDialog.setTitleText("???????????????");
        publicDialog.setContentText("??????????????????-?????????????????? ??????????????????");
        publicDialog.setButtonText("??????");
    }


    public void refreshUi(int type) {
        isNew = false;
        removeTrace();
        mBaiduMap.clear();
        getDetail(type, CreateTopicId);  // ????????????
    }


    @Override
    public void onCreateOrderSuccess(CreateOrderEntity entity,int id) {
        if (entity != null) {
            PaymentInfoActivity.start(this, id==0?1:2, entity.getSn(), entity.getTotal_amount(),  false);
        }
    }

    @Override
    public void OnBoxSuccess(List<BoxEntity> list) {
        if (list.size() > 0) {
            boxEntityList = list.subList(0,10);
            //??????OverlayOptions?????????
            optionsBox = new ArrayList<>();
            infoBoxWindowList = new ArrayList<>();
            for (int i = 0; i < boxEntityList.size(); i++) {
                LatLng latLng = new LatLng(Double.parseDouble(boxEntityList.get(i).getLat()), Double.parseDouble(boxEntityList.get(i).getLng()));
                OverlayOptions option = new MarkerOptions().position(latLng)
                        .icon(getBoxIcon(i))
                        .perspective(true) // ?????????????????? marker ??????????????????????????????????????????
                        .anchor(0.5f, 0.5f) // ?????? marker ????????????????????????????????????0.5f, 1.0f?????????????????????????????????
                        .zIndex(i);
                optionsBox.add(option);
                View view = inflater.inflate(R.layout.layout_show_box, null, false);
                TextView mTvTitle = view.findViewById(R.id.tv_title);
                mTvTitle.setText(getString(R.string.box, boxEntityList.get(i).getName()));
                if (boxEntityList.get(i).getType() == 1) { //??????
                    mTvTitle.setBackgroundResource(R.drawable.free_box_bg);
                } else {
                    mTvTitle.setBackgroundResource(R.drawable.toll_box_bg);
                }
                mBoxInfoWindow = new InfoWindow(view, latLng, -120);
                infoBoxWindowList.add(mBoxInfoWindow);
            }
            //????????????????????????
            mMarkerBox = mBaiduMap.addOverlays(optionsBox);
            mBaiduMap.showInfoWindows(infoBoxWindowList);
        }
    }

    @Override
    public void OnCreateOrderOpenBoxDailySuccess(CreateOrderOpenBoxEntity entity) {
        if (entity != null) {
            PaymentInfoActivity.start(this, 3, entity.getSn(), entity.getTotal_amount(), false);
        }
    }

    public BitmapDescriptor getBoxIcon(int i) {

        if (boxEntityList.get(i).getType() == 1) {//??????
            return boxpic[0];
        } else {
            if (boxEntityList.get(i).getEquity() == 1) { //??????
                return boxpic[1];
            } else {
                return boxpic[2];
            }
        }
    }


    @Override
    public void onLocationSuccess() {
        if (PermisType == 1) {
            clearOrOpen();
        } else if (PermisType == 2) {
            participate();
        }
    }


    public void showParticipateDialog() {
        publicDialog = new PublicDialog(this);
        publicDialog.show();
        publicDialog.setCancelable(false);
        publicDialog.setTitleText("????????????");
        publicDialog.setContentText("????????????????????????????????????");
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
     * @description?????????????????????????????????
     */
    public void setIsVisible(boolean type) {
        mRlRight.setVisibility(type ? View.VISIBLE : View.INVISIBLE);
        mLlRight.setVisibility(type ? View.VISIBLE : View.INVISIBLE);
        mRlRIghtAddFriends.setVisibility(View.GONE);
    }

    /**
     * @description ???????????????
     */
    public void getFromUser(int user_id) {
        this.user_id = user_id;
        RequestUtil.create().userplayer(user_id + "", entity -> {
            if (entity != null && entity.getCode() == 200) {
                mTvFromName.setText(entity.getData().getUser().getName());
                GlideUtil.create().loadLongImage(this, entity.getData().getUser().getMascot(), mIvMastorLeft);
                mTvLeftName.setText("??????" + entity.getData().getPlanet().getName());
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
     * @description ???????????????
     */
    public void getToUser(int user_id) {
        this.user_id2 = user_id;
        RequestUtil.create().userplayer(user_id + "", entity -> {
            if (entity != null && entity.getCode() == 200) {
                if (!TextUtils.isEmpty(entity.getData().getUser().getName())) {
                    mTvToName.setText(entity.getData().getUser().getName());
                }
                GlideUtil.create().loadLongImage(this, entity.getData().getUser().getMascot(), mIvMastorRight);
                if (!TextUtils.isEmpty(entity.getData().getPlanet().getName())) {
                    mTvRightName.setText("??????" + entity.getData().getPlanet().getName());
                }
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
        if (attend == 1) {   //?????????????????????
            if (type == 1) {
                mIvLeftMask.setVisibility(View.GONE);
            } else {
                mIvRightMask.setVisibility(View.GONE);
            }
            if (status == 0) {//?????????
                if (type == 1) {
                    mRlLeftAddFriends.setClickable(true);
                } else {
                    mRlRIghtAddFriends.setClickable(true);
                }
                setText("????????????", type);
            } else if (status == 1) {//?????????
                if (type == 1) {
                    mRlLeftAddFriends.setClickable(false);
                } else {
                    mRlRIghtAddFriends.setClickable(false);
                }
                setText("?????????", type);
            } else if (status == 2) {//???????????????
                if (type == 1) {
                    mRlLeftAddFriends.setClickable(true);
                } else {
                    mRlRIghtAddFriends.setClickable(true);
                }
                setText("???Ta??????", type);
            }
        } else {
            if (status == 0) {//?????????
                if (type == 1) {
                    mRlLeftAddFriends.setClickable(false);
                    mIvLeftMask.setVisibility(View.VISIBLE);
                } else {
                    mRlRIghtAddFriends.setClickable(false);
                    mIvRightMask.setVisibility(View.VISIBLE);
                }
                setText("????????????", type);
            } else if (status == 1) {//?????????
                if (type == 1) {
                    mRlLeftAddFriends.setClickable(false);
                    mIvLeftMask.setVisibility(View.GONE);
                } else {
                    mRlRIghtAddFriends.setClickable(false);
                    mIvRightMask.setVisibility(View.GONE);
                }
                setText("?????????", type);
            } else if (status == 2) {//???????????????
                if (type == 1) {
                    mRlLeftAddFriends.setClickable(true);
                    mIvLeftMask.setVisibility(View.GONE);
                } else {
                    mRlRIghtAddFriends.setClickable(true);
                    mIvRightMask.setVisibility(View.GONE);
                }
                setText("???Ta??????", type);
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
     * @description ??????????????????icon
     */
    public void setMapOption() {
        //????????????
        if (messagePathBeanList.size() == 2) {
            setLastLine();
        } else if (messagePathBeanList.size() == 3) {
            LatLng p1 = new LatLng(Double.parseDouble(messagePathBeanList.get(0).getLat()), Double.parseDouble(messagePathBeanList.get(0).getLng()));//??????
            LatLng p2 = new LatLng((Double.parseDouble(messagePathBeanList.get(0).getLat()) + Double.parseDouble(messagePathBeanList.get(1).getLat())) / 2, (Double.parseDouble(messagePathBeanList.get(0).getLng()) + Double.parseDouble(messagePathBeanList.get(1).getLng())) / 2);//?????????
            LatLng p3 = new LatLng(Double.parseDouble(messagePathBeanList.get(1).getLat()), Double.parseDouble(messagePathBeanList.get(1).getLng()));//??????
            OverlayOptions mArcOptions = new ArcOptions()
                    .color(0xAA6CECC3)
                    .width(6)
                    .points(p1, p2, p3);
            //????????????????????????
            mBaiduMap.addOverlay(mArcOptions);
            upDataMapStatus();

            setLastLine();

        } else {
            traceOption();
        }


        //??????OverlayOptions?????????
        options = new ArrayList<>();
        for (int i = 0; i < messagePathBeanList.size(); i++) {
            OverlayOptions option = new MarkerOptions().position(new LatLng(Double.parseDouble(messagePathBeanList.get(i).getLat()), Double.parseDouble(messagePathBeanList.get(i).getLng())))
                    .icon(getIcon(i))
                    .perspective(true) // ?????????????????? marker ??????????????????????????????????????????
                    .anchor(0.5f, 0.5f) // ?????? marker ????????????????????????????????????0.5f, 1.0f?????????????????????????????????
                    .zIndex(i);
            options.add(option);
        }
        //????????????????????????
        mBaiduMap.addOverlays(options);


    }


    public void traceOption() {

        //????????????enable???true???false ????????????????????????????????????
        mUiSettings.setZoomGesturesEnabled(false);

        TraceOptions traceOptions = initTraceOptions();
        traceOptions.setTrackMove(true);
        // ??????????????????
        mTraceOverlay = mBaiduMap.addTraceOverlay(traceOptions, this);
        upDataMapStatus();
    }


    /**
     * ??????????????????
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
     * @description ??????????????????
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
     * ???????????????
     *
     * @return ?????????list
     */
    private List<LatLng> getTraceLocation() {
        List<LatLng> points = new ArrayList<LatLng>();
        for (int i = 0; i < messagePathBeanList.size() - 1; i++) {
            points.add(new LatLng(Double.parseDouble(messagePathBeanList.get(i).getLat()), Double.parseDouble(messagePathBeanList.get(i).getLng())));
        }
        return points;
    }


    public BitmapDescriptor getIcon(int i) {

        if (i == messagePathBeanList.size() - 1) {
            return mbppic[2];
        } else {
            if (i == 0 || messagePathBeanList.get(i).getHas_shop() == 1) {
                return mbppic[0];
            } else {
                return mbppic[1];
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
     * @description ??????assets????????????
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
     * @description ???????????????
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
                try {
                    if (messagePathBeanList != null && messagePathBeanList.size() == 3) {
                        builder = new LatLngBounds.Builder();
                        builder.include(new LatLng(Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 2).getLat()), Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 2).getLng())));
                        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build()));
                    } else {
                        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build()));
                    }
                    if (isNew) {  //???????????????
                        getRandomLatLng();
                    } else {
                        setMapOption();
                    }
                    getBox();
                } catch (Exception e) {
                    Log.e("latlng---", e.toString());
                }
            }
        }
    };


    /**
     * @description ???????????????????????????
     */

    public void getRandomLatLng() {
        latLngEntityList = new ArrayList<>();
        if (!Preferences.getCity().contains("??????")) {
            latLngEntityList.add(new LatLngEntity("??????", 39.914466, 116.403613));
        }
        if (!Preferences.getCity().contains("??????")) {
            latLngEntityList.add(new LatLngEntity("??????", 31.234941, 121.477665));
        }
        if (!Preferences.getCity().contains("?????????")) {
            latLngEntityList.add(new LatLngEntity("?????????", 38.039663, 114.51904));
        }
        if (!Preferences.getCity().contains("??????")) {
            latLngEntityList.add(new LatLngEntity("??????", 37.868491, 112.55053));
        }
        showOpenMsg(RBureauApplication.latLng, latLngEntityList.get(new Random().nextInt(latLngEntityList.size())));
    }


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
     * @description ?????????????????????
     */

    public void setLastLine() {
        OverlayOptions ooGeoPolyline = new PolylineOptions()
                .isGeodesic(true)
                .width(6)
                .color(0x7fFFFFFF)
                // ???????????????180??????????????????
                .lineDirectionCross180(PolylineOptions.LineDirectionCross180.FROM_WEST_TO_EAST)
                .points(getTrace());// ????????????????????? ??????[2,10000]?????????????????? null
        Polyline mGeoPolyline = (Polyline) mBaiduMap.addOverlay(ooGeoPolyline);
        mGeoPolyline.setDottedLine(true);

        if (messagePathBeanList.size() == 2) {
            showInfoWindow(new LatLng(Double.parseDouble(messagePathBeanList.get(0).getLat()), Double.parseDouble(messagePathBeanList.get(0).getLng())), 0);
        } else {
            showInfoWindow(new LatLng(Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 2).getLat()), Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 2).getLng())), messagePathBeanList.size() - 2);
        }
        perfomRotate(0);
    }


    /**
     * ?????????????????????
     *
     * @return ?????????list
     */
    private List<LatLng> getTrace() {
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(new LatLng(Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 1).getLat()), Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 1).getLng())));
        points.add(new LatLng(Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 2).getLat()), Double.parseDouble(messagePathBeanList.get(messagePathBeanList.size() - 2).getLng())));
        return points;
    }


    /**
     * ???????????? ?????????????????? 0 ~ 360 , ????????????
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
     * @description ??????/?????????????????????????????????
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
     * @description ????????????
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void TagEvent(TagEvent event) {
        if (event != null) {
            mapSendDriftDialog.setTag(event.getTag(), event.getTagname());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void PaymentEvent(PaymentEvent event) {  //??????????????????
        if (event != null) {
            if (event.getType() == 1 || event.getType()==2) {
                sendSuccess();
            } else {
                openBox(mMarker, "");
            }
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
        // ???activity??????onPause???????????????mMapView. onPause ()
        mMapView.onPause();
    }


    @OnClick({R.id.toolbar_back, R.id.tv_open, R.id.rl_left_add_friend, R.id.rl_right_add_friend, R.id.iv_right, R.id.tv_share, R.id.tv_winning_record, R.id.tv_rules_description, R.id.tv_list_prizes})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_open:  //????????????
                    if (messagePathBeanList != null) {
                        messagePathBeanList.clear();
                    }
                    // ??????????????????
                    openNewDrift();
                    break;
                case R.id.rl_left_add_friend:  //??????????????????
                    if (leftStatus == 2) {
                        startIM(user_id, mTvFromName.getText().toString());
                    } else {
                        friendapply(user_id, 1);
                    }
                    break;
                case R.id.rl_right_add_friend: //??????????????????
                    if (rightStatus == 2) {
                        startIM(user_id2, mTvToName.getText().toString());
                    } else {
                        friendapply(user_id2, 2);
                    }
                    break;
                case R.id.iv_right:  //??????
                    if (messageBean != null) {
                        NebulaActivity.start(this, message_id, false);
                    }
                    break;
                case R.id.tv_share:  //??????
                    RequestUtil.create().userplayer(Preferences.getUserId(), entity -> {
                        shareBoxDialog = new ShareBoxDialog(this, entity.getData());
                        shareBoxDialog.show();
                    });
                    break;
                case R.id.tv_winning_record:  //????????????
                    WinningRecordActivity.start(this, false);
                    break;
                case R.id.tv_rules_description:  //????????????
                    RulesDescriptionActivity.start(this, false);
                    break;
                case R.id.tv_list_prizes:  //????????????
                    RequestUtil.create().previewBox(entity -> {
                        if (entity!=null &&entity.getCode()==200){
                            if (listPrizesDialog == null) {
                                listPrizesDialog = new ListPrizesDialog(DriftTrackMapActivity.this,entity.getData().getImage1());
                            }
                            listPrizesDialog.show();
                        }
                    });
                     break;
            }
        }
    }

    /**
     * @description ???????????????
     */
    public void openNewDrift() {
        if (RBureauApplication.latLng != null) {
            clearOrOpen();
        } else {
            if (mPresenter != null) {  //????????????
                PermisType = 1;
                mPresenter.getLocation(this);
            }
        }
    }


    /**
     * @description ????????????????????????
     */
    public void clearOrOpen() {
        removeTrace();
        mBaiduMap.clear();
        isNew = true;
        getFromUser(Integer.parseInt(Preferences.getUserId()));
        setIsVisible(true);
        mTvToName.setText("???");
        mTvRightName.setText("?????? ? ? ?");
        mIvMastorRight.setImageResource(R.drawable.dark_bear);
        if (!TextUtils.isEmpty(Preferences.getCity())) {
            selectCity(Preferences.getCity());
        }
    }


    public void startIM(int user_id, String name) {
        String targetId = user_id + "";//??????????????????id
        Bundle bundle = new Bundle();
        bundle.putString(RouteUtils.TITLE, name); //??????????????????
        RouteUtils.routeToConversationActivity(this, Conversation.ConversationType.PRIVATE, targetId, bundle);
    }

    /**
     * @description ???????????????
     */
    public void showOpenMsg(LatLng latLng, LatLngEntity latLngEntity) {
        //??????OverlayOptions?????????
        options = new ArrayList<OverlayOptions>();
        for (int i = 0; i < 2; i++) {
            OverlayOptions option = new MarkerOptions().position(i == 0 ? latLng : new LatLng(latLngEntity.getLatitude(), latLngEntity.getLongitude()))
                    .icon(i == 0 ? mbppic[0] : mbppic[2])
                    .perspective(false) // ?????????????????? marker ??????????????????????????????????????????
                    .anchor(0.5f, 0.5f); // ?????? marker ????????????????????????????????????0.5f, 1.0f?????????????????????????????????
            options.add(option);
        }

        mMarkerOpenList = mBaiduMap.addOverlays(options);
        //???????????????
        OverlayOptions ooGeoPolyline = new PolylineOptions()
                .isGeodesic(true)
                .width(6)
                .color(0x7fFFFFFF)
                // ???????????????180??????????????????
                .lineDirectionCross180(PolylineOptions.LineDirectionCross180.FROM_WEST_TO_EAST)
                .points(getNewTrace(latLng, latLngEntity));// ????????????????????? ??????[2,10000]?????????????????? null
        Polyline mGeoPolyline = (Polyline) mBaiduMap.addOverlay(ooGeoPolyline);
        mGeoPolyline.setDottedLine(true);

        showNewInfoWindow(latLng);

    }

    private List<LatLng> getNewTrace(LatLng latLng, LatLngEntity latLngEntity) {
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(latLng);
        points.add(new LatLng(latLngEntity.getLatitude(), latLngEntity.getLongitude()));
        return points;
    }


    /**
     * @description ????????????
     */

    public void showNewInfoWindow(LatLng latLng) {

        View view = inflater.inflate(R.layout.layout_start_drifting, null, false);
        FrameLayout mFlNewMessage = view.findViewById(R.id.fl_new_message);
        mInfoWindow = new InfoWindow(view, latLng, -80);
        mFlNewMessage.setOnClickListener(v -> {
            OpenNewMsg();
        });
        mBaiduMap.showInfoWindow(mInfoWindow);

    }


    public void OpenNewMsg() {
        cityReleaseDialog = new CityReleaseDialog(this);
        cityReleaseDialog.show();
        cityReleaseDialog.setOnClickCallback(type -> {
            if (type == CityReleaseDialog.SELECT_PAY) {  //??????
                SendNewMesg(true);
            } else if (type == CityReleaseDialog.SELECT_FREE) {//??????
                SendNewMesg(false);
            }
        });
    }


    public void SendNewMesg(boolean isPay) {
        RequestUtil.create().platformtimes(explore_id, entity -> {
            if (entity != null && entity.getCode() == 200) {
                total = entity.getData().getAttend_times() + entity.getData().getCommon_times();
                mapSendDriftDialog = new MapSendDriftDialog(getActivity(), 1, total);
                mapSendDriftDialog.show();
                mapSendDriftDialog.setOnStarrySkyClickCallback((type, word, path, list, cover, tag) -> {
                    status = 1;
                    if (mPresenter != null) {
                        showLoading();
                        if (type == 1) { //??????
                            mPresenter.compressVideo(DriftTrackMapActivity.this, type, word, 0, path, list != null ? new File(list.get(0).toString()) : null, cover != null ? BitmapUtil.saveBitmapFile(getActivity(), cover) : null, tag, isPay ? 0 : 1);
                        } else {
                            mPresenter.createwithword(type, word, 0, path != null ? new File(path) : null, list != null ? new File(list.get(0).toString()) : null, cover != null ? BitmapUtil.saveBitmapFile(getActivity(), cover) : null, tag, isPay ? 0 : 1);
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
                        mTvAddLeftFriend.setText("?????????");
                    } else {
                        mRlRIghtAddFriends.setClickable(false);
                        mTvAddRightFriend.setText("?????????");
                    }
                    ToastUtil.showAddFriendDialog(this);
                } else {
                    showMessage(entity.getMsg());
                }
            }
        });

    }


    /**
     * ??????????????????
     */
    private Animation getScaleAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1.5f, 1f);
        scaleAnimation.setDuration(1500);
        scaleAnimation.setRepeatMode(Animation.RepeatMode.RESTART);// ??????????????????
        scaleAnimation.setRepeatCount(ValueAnimator.INFINITE);// ??????????????????
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void onAnimationCancel() {
            }

            @Override
            public void onAnimationRepeat() {
            }
        });
        return scaleAnimation;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //??????????????????
        mDistrictSearch.destroy();
        //????????????
        removeTrace();
        // ??????????????????
        mBaiduMap.clear();
        // ???activity??????onDestroy???????????????mMapView.onDestroy()
        mMapView.onDestroy();
        RequestUtil.create().disDispose();
    }

    /**
     * ????????????
     */
    private void removeTrace() {
        if (null != mTraceOverlay) {
            mTraceOverlay.clear(); // ???????????????????????????????????????????????????
            mTraceOverlay.remove(); // ?????????????????????
        }
    }
}