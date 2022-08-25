package com.drifting.bureau.mvp.ui.activity.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseActivity;
import com.drifting.bureau.data.event.BackSpaceEvent;
import com.drifting.bureau.data.event.PaymentEvent;
import com.drifting.bureau.data.event.VideoEvent;
import com.drifting.bureau.di.component.DaggerTopicDetailComponent;
import com.drifting.bureau.mvp.contract.TopicDetailContract;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.presenter.TopicDetailPresenter;
import com.drifting.bureau.mvp.ui.activity.pay.PaymentInfoActivity;
import com.drifting.bureau.mvp.ui.dialog.PublicDialog;
import com.drifting.bureau.mvp.ui.dialog.RaftingOrderDialog;
import com.drifting.bureau.mvp.ui.dialog.ReleaseDriftingDialog;
import com.drifting.bureau.storageinfo.Preferences;
import com.drifting.bureau.util.BitmapUtil;
import com.drifting.bureau.util.ClickUtil;
import com.drifting.bureau.util.GlideUtil;
import com.drifting.bureau.util.ToastUtil;
import com.drifting.bureau.util.VideoUtil;
import com.drifting.bureau.util.ViewUtil;
import com.drifting.bureau.util.request.RequestUtil;
import com.drifting.bureau.view.chart.LineChartView;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/07/12 17:59
 *
 * @author 发布话题
 * module name is TopicDetailActivity
 */
public class TopicDetailActivity extends BaseActivity<TopicDetailPresenter> implements TopicDetailContract.View {
    @BindView(R.id.line_chart_view)
    LineChartView lineChartView;
    @BindView(R.id.scroll_view)
    HorizontalScrollView scrollView;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.tv_rafting_type)
    TextView mTvRaftingType;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_planet)
    TextView mTvPlanet;
    @BindView(R.id.tv_identity)
    TextView mTvIdentity;
    @BindView(R.id.iv_mastor)
    ImageView mIvMastor;
    @BindView(R.id.tv_from_name)
    TextView mTvFromName;
    @BindView(R.id.tv_to_name)
    TextView mTvToName;
    @BindView(R.id.ll_top)
    LinearLayout mLlTop;
    @BindView(R.id.tv_open)
    TextView mTvOpen;
    @BindView(R.id.rl_add_friends)
    RelativeLayout mRlAddFriends;
    @BindView(R.id.iv_mask)
    ImageView mIvMask;
    @BindView(R.id.tv_add_friends)
    TextView mTvAddFriend;
    @BindView(R.id.iv_add_friend_bg)
    ImageView mIvAddFriendBg;
    private int explore_id, message_id, message_id2, total, postion, status, user_id, attend, Msgtype;
    private String path;
    private ReleaseDriftingDialog releaseDriftingDialog;
    private static String EXTRA_EXPLORE_ID = "extra_explore_id";

    private static String EXTRA_MESSAGE_ID = "extra_message_id";

    private static String EXTRA_TYPE = "extra_type";

    private List<LineChartView.Data> datas;
    private List<MoreDetailsEntity.MessagePathBean> messagePathBeanList;

    private MoreDetailsEntity.MessageBean messageBean;
    private MoreDetailsEntity.RelevanceBean relevanceBean;


    private RaftingOrderDialog raftingOrderDialog;
    private PublicDialog publicDialog;


    private int[] dataArr = new int[]{152, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146, 52, 59, 185, 134, 45, 169, 54, 155, 40, 146};

    private int[] newData;


    public static void start(Context context, int type, int explore_id, int message_id, boolean closePage) {
        Intent intent = new Intent(context, TopicDetailActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_EXPLORE_ID, explore_id);
        intent.putExtra(EXTRA_MESSAGE_ID, message_id);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    public static void start(Context context, int explore_id, int message_id, boolean closePage) {
        Intent intent = new Intent(context, TopicDetailActivity.class);
        intent.putExtra(EXTRA_EXPLORE_ID, explore_id);
        intent.putExtra(EXTRA_MESSAGE_ID, message_id);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTopicDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_topic_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        if (getIntent() != null) {
            Msgtype = getIntent().getExtras().getInt(EXTRA_TYPE);
            explore_id = getIntent().getExtras().getInt(EXTRA_EXPLORE_ID);
            message_id = getIntent().getExtras().getInt(EXTRA_MESSAGE_ID);
        }
        if (message_id != 0) {
            mIvRight.setVisibility(View.VISIBLE);
            mIvRight.setImageResource(R.drawable.tran_detail);
        }
        initListener();
    }

    public void initListener() {
        datas = new ArrayList<>();
        if (Msgtype == 1) {  //开启新漂流
            InitiateTopic();
            OpenNewMsg();
            getUserinfo(user_id);
        } else {
            getDetail(1);
        }

    }

    public void getDetail(int type) {
        if (mPresenter != null) {
            mPresenter.moreDetails(message_id, type);
        }
    }


    @Override
    public void onMoreDetailsSuccess(MoreDetailsEntity entity, int type) {
        if (entity != null) {
            messageBean = entity.getMessage();
            relevanceBean = entity.getRelevance();
            messagePathBeanList = entity.getMessage_path();


            attend = relevanceBean.getAttend();

            //展示开启新漂流
            mTvOpen.setVisibility(attend == 1 ? View.VISIBLE : View.GONE);

            if (!TextUtils.isEmpty(entity.getRelevance().getLast_attend())) {
                mLlTop.setVisibility(View.VISIBLE);
                mTvFromName.setText(messageBean.getUser_name());
                mTvToName.setText(entity.getRelevance().getLast_attend());
            } else {
                mLlTop.setVisibility(View.GONE);
            }

            if (messageBean.getId() == 0) {  //发起话题
                InitiateTopic();
            } else {
                message_id2 = messageBean.getId();
                MoreDetailsEntity.MessagePathBean messagePathBean = new MoreDetailsEntity.MessagePathBean();
                messagePathBean.setUser_id(messageBean.getUser_id());
                messagePathBean.setComment_id(-1);
                messagePathBeanList.add(0, messagePathBean);
                user_id = messagePathBeanList.get(messagePathBeanList.size() - 1).getUser_id();
                newData = Arrays.copyOfRange(dataArr, 0, messagePathBeanList.size() + 1);
                for (int i = 0; i < newData.length; i++) {
                    LineChartView.Data data = new LineChartView.Data(newData[i]);
                    datas.add(data);
                }
                lineChartView.setData(datas, 2);
            }


            lineChartView.setClockListener(new LineChartView.OpenMessageListener() {
                @Override
                public void onClick(int index) {
                    if (index == -1) {
                        OpenNewMsg();
                    } else {
                        user_id = messagePathBeanList.get(index).getUser_id();
                        getUserinfo(user_id);
                    }
                }

                @Override
                public void onOPenClick(int index) {   //点击飘来宇宙电波
                    postion = index;
                    showDetails(postion);
                }
            });
            if (type == 2) {
                showDetails(postion);
            }

            scrollView.post(() -> {
                //滚动到右边
                scrollView.fullScroll(ScrollView.FOCUS_RIGHT);
            });

            getUserinfo(user_id);
        }
    }

    public void OpenNewMsg() {
        RequestUtil.create().platformtimes(explore_id, entity -> {
            if (entity != null && entity.getCode() == 200) {
                total = entity.getData().getAttend_times() + entity.getData().getCommon_times();
                releaseDriftingDialog = new ReleaseDriftingDialog(getActivity(), 1, total);
                releaseDriftingDialog.show();
                releaseDriftingDialog.setOnStarrySkyClickCallback((type, word, path, list, cover) -> {
                    status = 1;
                    if (mPresenter != null) {
                        showLoading();
                        if (type == 1) { //视频
                            mPresenter.compressVideo(TopicDetailActivity.this, type, explore_id, word, 0, path, list != null ? new File(list.get(0).toString()) : null, cover != null ? BitmapUtil.saveBitmapFile(getActivity(), cover) : null);
                        } else {
                            mPresenter.createwithword(type, explore_id, word, 0, path != null ? new File(path) : null, list != null ? new File(list.get(0).toString()) : null, cover != null ? BitmapUtil.saveBitmapFile(getActivity(), cover) : null);
                        }
                    }
                });

            }
        });

    }


    public void showDetails(int type) {
        if (type == 0) {
            mPresenter.details(messageBean.getId(), 0, messagePathBeanList.get(type).getUser_id());
        } else {
            mPresenter.details(messagePathBeanList.get(type).getComment_id(), 1, messagePathBeanList.get(type).getUser_id());
        }
    }

    /**
     * @description 发起话题
     */

    public void InitiateTopic() {
        user_id = Integer.parseInt(Preferences.getUserId());
        newData = Arrays.copyOfRange(dataArr, 0, 2);
        for (int i = 0; i < newData.length; i++) {
            LineChartView.Data data = new LineChartView.Data(newData[i]);
            datas.add(data);
        }
        lineChartView.setData(datas, 1);
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
    public void onCommentDetailsSuccess(CommentDetailsEntity entity) {
        if (entity != null) {
            RequestUtil.create().platformtimes(explore_id, entity1 -> {
                if (entity1 != null && entity1.getCode() == 200) {
                    total = entity1.getData().getAttend_times() + entity1.getData().getCommon_times();
                    releaseDriftingDialog = new ReleaseDriftingDialog(getActivity(), 2, entity, relevanceBean, total);
                    releaseDriftingDialog.show();
                    releaseDriftingDialog.setOnContentClickCallback(content -> {
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

                    releaseDriftingDialog.setOnStarrySkyClickCallback((type, word, path, list, cover) -> {
                        status = 2;
                        if (mPresenter != null) {
                            showLoading();
                            mPresenter.createwithword(type, explore_id, word, entity.getMessage_id(), path != null ? new File(path) : null, list != null ? new File(list.get(0).toString()) : null, cover != null ? BitmapUtil.saveBitmapFile(this, cover) : null);
                        }
                    });
                    releaseDriftingDialog.setOnClickCallback(type -> {
                        if (type == ReleaseDriftingDialog.SELECT_FINISH) {

                            RequestUtil.create().messagethrow(message_id, entity2 -> {
                                if (entity2.getCode() == 200) {
                                    releaseDriftingDialog.dismiss();
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
    public void onCreateOrderSuccess(CreateOrderEntity entity) {
        if (entity != null) {
            PaymentInfoActivity.start(this, 1, entity.getSn(), entity.getTotal_amount(), 0, false);
        }
    }

    @Override
    public void onMessageAttendingSuccess() {
        showParticipateDialog();
    }

    public void showParticipateDialog() {
        publicDialog = new PublicDialog(this);
        publicDialog.show();
        publicDialog.setCancelable(false);
        publicDialog.setTitleText("参与成功");
        publicDialog.setContentText("快写下你的传递漂信息吧！");
        publicDialog.setOnClickCallback(type -> {
            if (type == PublicDialog.SELECT_FINISH) {
                if (releaseDriftingDialog != null) {
                    releaseDriftingDialog.dismiss();
                    if (datas != null) {
                        datas.clear();
                    }
                    getDetail(2);  // 刷新界面
                }
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void PaymentEvent(PaymentEvent event) {  //购买成功回调
        if (event != null) {
            sendSuccess();
        }
    }


    /**
     * 消息发送成功
     */
    public void sendSuccess() {
        if (datas != null) {
            datas.clear();
        }
        getDetail(1);  // 刷新界面
        releaseDriftingDialog.dismiss();
        publicDialog = new PublicDialog(this);
        publicDialog.show();
        publicDialog.setCancelable(false);
        publicDialog.setTitleText("已成功发送");
        publicDialog.setContentText("可在“关于我-漂流轨迹”中 查看漂流记录");
        publicDialog.setButtonText("确定");
    }


    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
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
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }


    @OnClick({R.id.toolbar_back, R.id.tv_open, R.id.iv_right, R.id.rl_add_friends})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_open:   //开启新漂流
                    if (datas != null) {
                        datas.clear();
                    }
                    mLlTop.setVisibility(View.GONE);
                    mTvOpen.setVisibility(View.GONE);
                    InitiateTopic();
                    getUserinfo(user_id);
                    break;
                case R.id.iv_right:  //星云
                    if (messageBean != null) {
                        NebulaActivity.start(this, message_id, false);
                    }
                    break;
                case R.id.rl_add_friends: //添加好友
                    RequestUtil.create().friendapply(user_id + "", entity -> {
                        if (entity != null) {
                            if (entity.getCode() == 200) {
                                mRlAddFriends.setClickable(false);
                                mTvAddFriend.setText("已申请");
                                mIvAddFriendBg.setColorFilter(getColor(R.color.color_66));
                                ToastUtil.showAddFriendDialog(this);
                            } else {
                                showMessage(entity.getMsg());
                            }
                        }
                    });

                    break;
            }
        }
    }


    /**
     * @description 用户信息
     */
    public void getUserinfo(int user_id) {
        RequestUtil.create().userplayer(user_id + "", entity -> {
            if (entity != null && entity.getCode() == 200) {
                if (explore_id == 1) {
                    mTvRaftingType.setText("传递漂");
                }
                mTvName.setText("昵称：" + entity.getData().getUser().getName());
                mTvPlanet.setText(entity.getData().getPlanet().getName());
                mTvIdentity.setText(entity.getData().getUser().getLevel_name());
                GlideUtil.create().loadLongImage(this, entity.getData().getUser().getMascot(), mIvMastor);

                if (user_id == Integer.parseInt(Preferences.getUserId())) {
                    mRlAddFriends.setVisibility(View.GONE);
                } else {
                    RequestUtil.create().isFriend(user_id + "", entity1 -> {
                        if (entity1 != null && entity1.getCode() == 200) {
                            int status = entity1.getData().getStatus();
                            if (status == 0) {  //未申请
                                setIsLock(status);
                            } else if (status == 1) { //已申请
                                setIsLock(status);
                            } else if (status == 2) { //  已经是好友
                                setIsLock(status);
                            }
                            mRlAddFriends.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });

    }


    public void setIsLock(int status) {

        if (attend == 1) {   //已经参与话题了
            mIvMask.setVisibility(View.GONE);
            if (status == 0) {//未申请
                mRlAddFriends.setClickable(true);
                setText("添加好友", R.color.transparent);
            } else if (status == 1) {//已申请
                mRlAddFriends.setClickable(false);
                setText("已申请", R.color.color_66);
            } else if (status == 2) {//已经是好友
                mRlAddFriends.setClickable(false);
                setText("已添加", R.color.color_66);
            }
        } else {
            mRlAddFriends.setClickable(false);
            if (status == 0) {//未申请
                mIvMask.setVisibility(View.VISIBLE);
                setText("添加好友", R.color.transparent);
            } else if (status == 1) {//已申请
                mIvMask.setVisibility(View.GONE);
                setText("已申请", R.color.color_66);
            } else if (status == 2) {//已经是好友
                mIvMask.setVisibility(View.GONE);
                setText("已添加", R.color.color_66);
            }
        }
    }

    public void setText(String content, int color) {
        mTvAddFriend.setText(content);
        mIvAddFriendBg.setColorFilter(getColor(color));
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void VideoEvent(VideoEvent event) {
        if (event != null) {
            path = event.getPath();
            releaseDriftingDialog.setData(event.getType(), path);
        }
    }


    @Override
    protected void onDestroy() {
        RequestUtil.create().disDispose();
        if (lineChartView != null) {
            lineChartView.recycleBitmap();
        }
        super.onDestroy();
    }
}