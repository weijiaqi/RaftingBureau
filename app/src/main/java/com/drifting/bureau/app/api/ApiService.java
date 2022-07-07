package com.drifting.bureau.app.api;


import com.bumptech.glide.annotation.GlideType;
import com.drifting.bureau.mvp.model.entity.AnswerEntity;
import com.drifting.bureau.mvp.model.entity.BarrageEntity;
import com.drifting.bureau.mvp.model.entity.BoxOpenEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.CustomerEntity;
import com.drifting.bureau.mvp.model.entity.DeliveryDetailsEntity;
import com.drifting.bureau.mvp.model.entity.DriftingTrackEntity;
import com.drifting.bureau.mvp.model.entity.ExploreTimesEntity;
import com.drifting.bureau.mvp.model.entity.FriendApplicationEntity;
import com.drifting.bureau.mvp.model.entity.FriendInfoEntity;
import com.drifting.bureau.mvp.model.entity.IncomeRecordEntity;
import com.drifting.bureau.mvp.model.entity.InfoForShareEntity;
import com.drifting.bureau.mvp.model.entity.LoginEntity;
import com.drifting.bureau.mvp.model.entity.MakingRecordEntity;
import com.drifting.bureau.mvp.model.entity.MessageContentEntity;
import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
import com.drifting.bureau.mvp.model.entity.MyBlindBoxEntity;
import com.drifting.bureau.mvp.model.entity.MySpaceStationEntity;
import com.drifting.bureau.mvp.model.entity.MyTreasuryEntity;
import com.drifting.bureau.mvp.model.entity.MysteryboxEntity;
import com.drifting.bureau.mvp.model.entity.OrderDetailEntity;
import com.drifting.bureau.mvp.model.entity.OrderOneEntity;
import com.drifting.bureau.mvp.model.entity.OrderRecordEntity;
import com.drifting.bureau.mvp.model.entity.PayOrderEntity;
import com.drifting.bureau.mvp.model.entity.PlanetEntity;
import com.drifting.bureau.mvp.model.entity.PlanetLocationEntity;
import com.drifting.bureau.mvp.model.entity.PlanetaryDetailEntity;
import com.drifting.bureau.mvp.model.entity.PlatformTimesEntity;
import com.drifting.bureau.mvp.model.entity.PrizeEntity;
import com.drifting.bureau.mvp.model.entity.QuestionAssessEntity;
import com.drifting.bureau.mvp.model.entity.QuestionEntity;
import com.drifting.bureau.mvp.model.entity.RaftingBureaufriendEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.model.entity.SpaceAboutEntity;
import com.drifting.bureau.mvp.model.entity.SpaceCheckEntity;
import com.drifting.bureau.mvp.model.entity.SpaceExchangeEntity;
import com.drifting.bureau.mvp.model.entity.SpaceInfoEntity;
import com.drifting.bureau.mvp.model.entity.SpaceStationEntity;
import com.drifting.bureau.mvp.model.entity.SysmessageEntity;
import com.drifting.bureau.mvp.model.entity.SysmessageMineEntity;
import com.drifting.bureau.mvp.model.entity.TeaShopEntity;
import com.drifting.bureau.mvp.model.entity.TeamStatisticEntity;
import com.drifting.bureau.mvp.model.entity.UserEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.model.entity.WriteOffInfoEntity;
import com.jess.arms.base.BaseEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * @author 卫佳琪1
 * @description API接口
 * @time 14:54 14:54
 */

public interface ApiService {


    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("n/send/verificationcode")
    Observable<BaseEntity> verificationcode(@Field("mobile") String mobile, @Field("status") int status);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("n/user/login")
    Observable<BaseEntity<LoginEntity>> login(@Field("mobile") String mobile, @Field("code") String code);

    /**
     * 注册（含昵称设置）
     */
    @FormUrlEncoded
    @POST("n/user/register")
    Observable<BaseEntity<LoginEntity>> register(@Field("mobile") String mobile, @Field("name") String code);

    /**
     * 密码登录
     */
    @FormUrlEncoded
    @POST("n/user/passwordlogin")
    Observable<BaseEntity<LoginEntity>> passwordlogin(@Field("mobile") String mobile, @Field("password") String password);


    /**
     * 飘来新消息（话题）
     */

    @GET("v/message/receive")
    Observable<BaseEntity<MessageReceiveEntity>> messagereceive();


    /**
     * 创建话题 （普通文字）
     */
    @FormUrlEncoded
    @POST("v/message/createwithfile")
    Observable<BaseEntity<CreatewithfileEntity>> createwithword(@Field("type_id") int status, @Field("explore_id") int explore_id, @Field("content") String content, @Field("message_id") int message_id);


    /**
     * 创建话题（支持文件上传，发起和参与话题共用）
     */
    @POST("v/message/createwithfile")
    Observable<BaseEntity<CreatewithfileEntity>> createwithvoice(@Body MultipartBody shortVoice);


    /**
     * 商品列表（发起话题和参与话题）
     */
    @FormUrlEncoded
    @POST("v/sku/list")
    Observable<BaseEntity<SkuListEntity>> skulist(@Field("type_id") int type_id, @Field("explore_id") int explore_id, @Field("message_id") int message_id);


    /**
     * 创建订单(话题漂流)
     */
    @FormUrlEncoded
    @POST("v/order/createOrder")
    Observable<BaseEntity<CreateOrderEntity>> createOrder(@Field("message_id") int message_id, @Field("sku_codes") String sku_codes);


    /**
     * 查看漂流（话题详请）
     */
    @FormUrlEncoded
    @POST("v/message/content")
    Observable<BaseEntity<MessageContentEntity>> messagecontent(@Field("message_id") int message_id);


    /**
     * 参与话题（有免费次数时使用）
     */
    @FormUrlEncoded
    @POST("v/message/attending")
    Observable<BaseEntity> messageattending(@Field("message_id") int message_id);


    /**
     * 丢入星空（首页弹窗、查看漂流页面可用）
     */
    @FormUrlEncoded
    @POST("v/message/throw")
    Observable<BaseEntity> messagethrow(@Field("message_id") int message_id);


    /**
     * 获取空间站（盲盒列表）
     *
     * @return
     */
    @GET("v/sku/space")
    Observable<BaseEntity<List<SpaceStationEntity>>> space();

    /**
     * 创建订单（空间站）
     */
    @FormUrlEncoded
    @POST("v/order/createOrderSpace")
    Observable<BaseEntity<CreateOrderEntity>> createOrderSpace(@Field("sku_code") String sku_code, @Field("sku_num") String sku_num);

    /**
     * 支付订单
     */
    @FormUrlEncoded
    @POST("v/order/payOrder")
    Observable<BaseEntity<PayOrderEntity>> payOrder(@Field("sn") String sn);


    /**
     * 获取空间站（盲盒列表）
     *
     * @return
     */
    @GET("v/space/check")
    Observable<BaseEntity<SpaceCheckEntity>> spacecheck();

    /**
     * 奖品预览（获取空间站）
     *
     * @return
     */
    @GET("v/space/award/preview")
    Observable<BaseEntity<List<PrizeEntity>>> awardpreview();


    /**
     * 弹幕日志（获取空间站）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/mysterybox/logs")
    Observable<BaseEntity<MysteryboxEntity>> mysterybox(@Field("limit") int limit);


    /**
     * 空间站玩法
     *
     * @return
     */
    @GET("v/space/about")
    Observable<BaseEntity<List<SpaceAboutEntity>>> spaceabout();


    /**
     * 我的盲盒（空间站）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/mysterybox/mine")
    Observable<BaseEntity<MyBlindBoxEntity>> mySteryboxList(@Field("page") int page, @Field("limit") int limit);


    /**
     * 开启盲盒（我的盲盒中用）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/mysterybox/open")
    Observable<BaseEntity<BoxOpenEntity>> mysteryboxopen(@Field("box_id") String box_id);

    /**
     * 转赠盲盒
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/mysterybox/transfer")
    Observable<BaseEntity> transfer(@Field("box_id") String box_id, @Field("mobile") String mobile);


    /**
     * 申请提现
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/withdraw/apply")
    Observable<BaseEntity> withdrawapply(@Field("name") String name, @Field("account") String account, @Field("money") String money, @Field("op_type") int op_type);


    /**
     * 转赠物品（含空间站）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/storage/transfer")
    Observable<BaseEntity> storagetransfer(@Field("object_id") int object_id, @Field("mobile") String mobile);


    /**
     * 空间站信息（我的空间站）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/info")
    Observable<BaseEntity<SpaceInfoEntity>> spaceinfo(@Field("user_id") String user_id);


    /**
     * 漂流新订单（我的空间站）
     *
     * @return
     */
    @GET("v/space/order/one")
    Observable<BaseEntity<OrderOneEntity>> orderone();


    /**
     * 新订单的详情（空间站查看漂来的订单）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/order/detail")
    Observable<BaseEntity<OrderDetailEntity>> orderdetail(@Field("space_order_id") int space_order_id);

    /**
     * 查看漂流-玩家信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/user/player")
    Observable<BaseEntity<UserInfoEntity>> userplayer(@Field("user_id") String user_id);


    /**
     * 添加好友（查看漂流）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/friend/apply")
    Observable<BaseEntity> friendapply(@Field("user_id") String user_id);


    /**
     * 空间站丢回太空（把订单丢回太空）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/order/throw")
    Observable<BaseEntity> orderthrow(@Field("space_order_id") int space_order_id);


    /**
     * 制作订单（空间站为他制作）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/order/making")
    Observable<BaseEntity> ordermaking(@Field("space_order_id") int space_order_id);


    /**
     * 当前空间站级别（升级空间站）
     *
     * @return
     */
    @GET("v/space/level/current")
    Observable<BaseEntity<MySpaceStationEntity>> levelcurrent();


    /**
     * 当前所在星球及答题状态
     *
     * @return
     */
    @GET("v/planet/location")
    Observable<BaseEntity<PlanetLocationEntity>> planetlocation();


    /**
     * 我的库藏(我的空间站)
     *
     * @return
     */
    @GET("v/space/storage/mine")
    Observable<BaseEntity<List<MyTreasuryEntity>>> storagemine();


    /**
     * 漂流轨迹
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/mine")
    Observable<BaseEntity<DriftingTrackEntity>> messagemine(@Field("page") int page, @Field("limit") int limit);


    /**
     * 漂流详情（探寻轨迹）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/details")
    Observable<BaseEntity<BarrageEntity>> messagedetails(@Field("message_id") int message_id);


    /**
     * 制作记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/order/madelog")
    Observable<BaseEntity<MakingRecordEntity>> ordermadelog(@Field("page") int page, @Field("limit") int limit);

    /**
     * 收支记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/bill/logs")
    Observable<BaseEntity<IncomeRecordEntity>> spacebillogs(@Field("page") int page, @Field("limit") int limit);


    /**
     * 提现记录（星际团队）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/team/withdrawnLogs")
    Observable<BaseEntity<IncomeRecordEntity>> withdrawnLogs(@Field("page") int page, @Field("limit") int limit);


    /**
     * 订单记录（关于我）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/order/mine")
    Observable<BaseEntity<OrderRecordEntity>> ordermine(@Field("page") int page, @Field("limit") int limit);


    /**
     * 星球详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/planet/level/details")
    Observable<BaseEntity<PlanetaryDetailEntity>> plannetdetails(@Field("pl_id") int pl_id);


    /**
     * 传递详情（增加分页）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/path/details")
    Observable<BaseEntity<DeliveryDetailsEntity>> pathdetails(@Field("message_id") int message_id, @Field("page") int page, @Field("limit") int limit);


    /**
     * 探索方式列表
     *
     * @return
     */
    @GET("v/exploretype/list")
    Observable<BaseEntity<List<PlanetEntity>>> exploretypelist();


    /**
     * 设置昵称
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/user/setname")
    Observable<BaseEntity> setname(@Field("name") String name);

    /**
     * 意见反馈
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/feedback/add")
    Observable<BaseEntity> feedbackadd(@Field("content") String content, @Field("phone") String phone);


    /**
     * 核销码信息（订单记录-核销）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/order/writeOffInfo")
    Observable<BaseEntity<WriteOffInfoEntity>> writeOffInfo(@Field("order_id") String order_id);


    /**
     * 实体门店
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/business/nearby")
    Observable<BaseEntity<TeaShopEntity>> nearby(@Field("name") String name, @Field("page") int page, @Field("limit") int limit,@Field("lng") String lng,@Field("lat") String lat);


    /**
     * 问题列表（搬离星球）
     *
     * @return
     */
    @GET("v/question/list")
    Observable<BaseEntity<List<QuestionEntity>>> questionlist();


    /**
     * 问题列表（搬离星球）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/question/assess")
    Observable<BaseEntity<QuestionAssessEntity>> questionassess(@Field("questions") String questions, @Field("anwsers") String anwsers);


    /**
     * 添加位置信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/position/information")
    Observable<BaseEntity> information(@Field("lng") String lng, @Field("lat") String lat);


    /**
     * 使用物品（空间站）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/storage/using")
    Observable<BaseEntity> storageusing(@Field("object_id") int object_id, @Field("object_num") int object_num);


    /**
     * 星际团队信息
     *
     * @return
     */
    @GET("v/team/statistic")
    Observable<BaseEntity<TeamStatisticEntity>> team();


    /**
     * 开启漂流的次数
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/exploreTimes")
    Observable<BaseEntity<ExploreTimesEntity>> exploreTimes(@Field("explore_id") int explore_id);


    /**
     * 举报
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/report/commit")
    Observable<BaseEntity> reportcommit(@Field("message_id") int message_id, @Field("report_type") int report_type, @Field("reason") String reason);


    /**
     * 星际团队信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/platform/times")
    Observable<BaseEntity<PlatformTimesEntity>> platformtimes(@Field("explore_id") int explore_id);


    /**
     * 未读消息数（消息中心）
     *
     * @return
     */
    @GET("v/sysmessage/unread")
    Observable<BaseEntity<SysmessageEntity>> unread();


    /**
     * 元宇宙消息（消息中心）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/sysmessage/mine")
    Observable<BaseEntity<SysmessageMineEntity>> sysmessage(@Field("page") int page, @Field("limit") int limit);


    /**
     * 标记为已读（消息中心）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/sysmessage/markread")
    Observable<BaseEntity> markread(@Field("sys_msg_id") int sys_msg_id);


    /**
     * 未读消息数（消息中心）
     *
     * @return
     */
    @GET("v/friend/mine")
    Observable<BaseEntity<RaftingBureaufriendEntity>> friendmine();


    /**
     * 好友申请列表（消息中心）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/friend/applyToMe")
    Observable<BaseEntity<FriendApplicationEntity>> applyToMe(@Field("page") int page, @Field("limit") int limit);


    /**
     * 同意或拒绝好友申请（消息中心）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/friend/agreeRefuse")
    Observable<BaseEntity> agreeRefuse(@Field("apply_id") int apply_id,@Field("status") int status);


    /**
     * 删除好友（消息中心）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/friend/delete")
    Observable<BaseEntity> frienddelete(@Field("user_id") int user_id);


    /**
     * 好友相关信息（头像、昵称）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/friend/info")
    Observable<BaseEntity<FriendInfoEntity>> friendinfo(@Field("user_id") String user_id);

    /**
     * 兑换码兑换空间站
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/exchange")
    Observable<BaseEntity<SpaceExchangeEntity>> spaceExchange(@Field("code") String code);



}
