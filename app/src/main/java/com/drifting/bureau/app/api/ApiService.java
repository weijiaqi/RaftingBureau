package com.drifting.bureau.app.api;


import com.drifting.bureau.mvp.model.entity.AnnouncementEntity;
import com.drifting.bureau.mvp.model.entity.AvailableEntity;
import com.drifting.bureau.mvp.model.entity.BarrageEntity;
import com.drifting.bureau.mvp.model.entity.BlindBoxRecordEntity;
import com.drifting.bureau.mvp.model.entity.BoxEntity;
import com.drifting.bureau.mvp.model.entity.BoxOpenEntity;
import com.drifting.bureau.mvp.model.entity.CommentDetailsEntity;
import com.drifting.bureau.mvp.model.entity.CouponsMineEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderOpenBoxEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.DeliveryDetailsEntity;
import com.drifting.bureau.mvp.model.entity.DidAttendEntity;
import com.drifting.bureau.mvp.model.entity.DriftingTrackEntity;
import com.drifting.bureau.mvp.model.entity.ExploreTimesEntity;
import com.drifting.bureau.mvp.model.entity.FriendApplicationEntity;
import com.drifting.bureau.mvp.model.entity.FriendEntity;
import com.drifting.bureau.mvp.model.entity.FriendInfoEntity;
import com.drifting.bureau.mvp.model.entity.IncomeRecordEntity;
import com.drifting.bureau.mvp.model.entity.LoginEntity;
import com.drifting.bureau.mvp.model.entity.MakingRecordEntity;
import com.drifting.bureau.mvp.model.entity.MessageContentEntity;
import com.drifting.bureau.mvp.model.entity.MessageReceiveEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsEntity;
import com.drifting.bureau.mvp.model.entity.MoreDetailsForMapEntity;
import com.drifting.bureau.mvp.model.entity.MyBlindBoxEntity;
import com.drifting.bureau.mvp.model.entity.MySpaceStationEntity;
import com.drifting.bureau.mvp.model.entity.MyTreasuryEntity;
import com.drifting.bureau.mvp.model.entity.MysteryboxEntity;
import com.drifting.bureau.mvp.model.entity.NebulaEntity;
import com.drifting.bureau.mvp.model.entity.NebulaListEntity;
import com.drifting.bureau.mvp.model.entity.OpenBoxEntity;
import com.drifting.bureau.mvp.model.entity.OpenBoxListEntity;
import com.drifting.bureau.mvp.model.entity.OrderDetailEntity;
import com.drifting.bureau.mvp.model.entity.OrderOneEntity;
import com.drifting.bureau.mvp.model.entity.OrderRecordEntity;
import com.drifting.bureau.mvp.model.entity.PayOrderEntity;
import com.drifting.bureau.mvp.model.entity.PlanetArEntity;
import com.drifting.bureau.mvp.model.entity.PlanetEntity;
import com.drifting.bureau.mvp.model.entity.PlanetLocationEntity;
import com.drifting.bureau.mvp.model.entity.PlanetPasswordEntity;
import com.drifting.bureau.mvp.model.entity.PlanetaryDetailEntity;
import com.drifting.bureau.mvp.model.entity.PlatformTimesEntity;
import com.drifting.bureau.mvp.model.entity.PreviewBoxEntity;
import com.drifting.bureau.mvp.model.entity.PrizeEntity;
import com.drifting.bureau.mvp.model.entity.QuestionAssessEntity;
import com.drifting.bureau.mvp.model.entity.QuestionEntity;
import com.drifting.bureau.mvp.model.entity.QuestionStagesEntity;
import com.drifting.bureau.mvp.model.entity.RaftingBureaufriendEntity;
import com.drifting.bureau.mvp.model.entity.SandPayQueryEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.model.entity.SpaceAboutEntity;
import com.drifting.bureau.mvp.model.entity.SpaceCheckEntity;
import com.drifting.bureau.mvp.model.entity.SpaceExchangeEntity;
import com.drifting.bureau.mvp.model.entity.SpaceInfoEntity;
import com.drifting.bureau.mvp.model.entity.SpaceStationEntity;
import com.drifting.bureau.mvp.model.entity.StarUpIndexEntity;
import com.drifting.bureau.mvp.model.entity.SysmessageEntity;
import com.drifting.bureau.mvp.model.entity.SysmessageMineEntity;
import com.drifting.bureau.mvp.model.entity.TeaShopEntity;
import com.drifting.bureau.mvp.model.entity.TeamStatisticEntity;
import com.drifting.bureau.mvp.model.entity.TopicTagsEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.drifting.bureau.mvp.model.entity.VersionUpdateEntity;
import com.drifting.bureau.mvp.model.entity.WriteOffInfoEntity;
import com.drifting.bureau.view.chart.EnergyChartView;
import com.jess.arms.base.BaseEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author 卫佳琪1
 * @description API接口
 * @time 14:54 14:54
 */

public interface ApiService {

    /**
     * app下载
     *
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    /**
     * 版本更新
     *
     * @return
     */
    @GET("n/version/update")
    Observable<BaseEntity<VersionUpdateEntity>> checkVersion();


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
     * 创建话题（V2-话题标签）
     */
    @POST("v/message/creatingwithfile/v2")
    Observable<BaseEntity<CreatewithfileEntity>> creatingwithfileword(@Body MultipartBody shortVoice);


    /**
     * 商品列表（发起话题和参与话题）
     */
    @FormUrlEncoded
    @POST("v/sku/list")
    Observable<BaseEntity<SkuListEntity>> skulist(@Field("explore_id") int explore_id, @Field("message_id") int message_id);


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
    @POST("v/order/payOrderAll")
    Observable<BaseEntity<PayOrderEntity>> payOrder(@Field("sn") String sn, @Field("terminal") String terminal,@Field("coupon_code") String coupon_code,@Field("use_scene") String use_scene);


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
    Observable<BaseEntity> withdrawapply(@Field("name") String name, @Field("account") String account, @Field("money") String money, @Field("bank_name") String bank_name, @Field("op_type") int op_type);


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
     * 答题间隔天数
     *
     * @return
     */
    @GET("v/planet/whenMove")
    Observable<BaseEntity<PlanetLocationEntity>> planetlocation();


    /**
     * 我的库藏(我的空间站)
     *
     * @return
     */
    @GET("v/space/storage/mine")
    Observable<BaseEntity<List<MyTreasuryEntity>>> storagemine();


    /**
     * 公告
     *
     * @return
     */
    @GET("v/announcement/latest")
    Observable<BaseEntity<List<AnnouncementEntity>>> latest();


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
    @POST("v/message/path/attendInCity")
    Observable<BaseEntity<DeliveryDetailsEntity>> pathdetails(@Field("message_id") int message_id, @Field("code_city") String code_city, @Field("page") int page, @Field("limit") int limit);


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
    Observable<BaseEntity<TeaShopEntity>> nearby(@Field("name") String name, @Field("page") int page, @Field("limit") int limit, @Field("lng") String lng, @Field("lat") String lat);


    /**
     * 答题过程阶段
     *
     * @return
     */
    @GET("v/question/stages")
    Observable<BaseEntity<List<QuestionStagesEntity>>> stages();


    /**
     * 问题列表（增加场景）
     *
     * @return
     */
    @GET("v/question/listWithScene")
    Observable<BaseEntity<List<QuestionEntity>>> listWithScene();


    /**
     * 答题测评（场景版）
     *
     * @return
     */
    @POST("v/question/assessWithScene")
    Observable<BaseEntity<QuestionAssessEntity>> questionassess(@Body RequestBody body);


    /**
     * 答题结果
     *
     * @return
     */
    @GET("v/question/assessResult")
    Observable<BaseEntity<QuestionAssessEntity>> assessResult();

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
    Observable<BaseEntity> reportcommit(@Field("message_id") int message_id, @Field("comment_id") int comment_id, @Field("report_type") int report_type, @Field("reason") String reason);


    /**
     * 免费漂流次数
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
    Observable<BaseEntity> agreeRefuse(@Field("apply_id") int apply_id, @Field("status") int status);


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


    /**
     * 传递详情（新版）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/moreDetails")
    Observable<BaseEntity<MoreDetailsEntity>> moreDetails(@Field("message_id") int message_id);


    /**
     * 是否添加好友
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/friend/isFriend")
    Observable<BaseEntity<FriendEntity>> isFriend(@Field("user_id") String user_id);


    /**
     * 查看评论或话题详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/comment/details")
    Observable<BaseEntity<CommentDetailsEntity>> details(@Field("log_id") int log_id, @Field("level") int level, @Field("user_id") int user_id);


    /**
     * 我的盲盒（空间站）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/nebula")
    Observable<BaseEntity<NebulaEntity>> messagenebula(@Field("message_id") int message_id, @Field("page") int page, @Field("limit") int limit);


    /**
     * 查询支付状态
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/order/sandPayQuery")
    Observable<BaseEntity<SandPayQueryEntity>> sandPayQuery(@Field("sn") String sn, @Field("terminal") String terminal);


    /**
     * 盲盒记录列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/mysterybox/open/logs")
    Observable<BaseEntity<BlindBoxRecordEntity>> openlogs(@Field("page") int page, @Field("limit") int limit);


    /**
     * 注销
     *
     * @return
     */
    @GET("v/user/unregister")
    Observable<BaseEntity> unregister();


    /**
     * 获取AR Url (登录前)
     *
     * @return
     */
    @GET("n/planet/ar")
    Observable<BaseEntity<PlanetArEntity>> planetar();

    /**
     * 获取AR Url (登录前)
     *
     * @return
     */
    @GET("v/nebula/list")
    Observable<BaseEntity<NebulaListEntity>> nebulalist();

    /**
     * 青年创业营
     *
     * @return
     */
    @GET("v/startup/index")
    Observable<BaseEntity<StarUpIndexEntity>> startup();


    /**
     * 是否参与过漂流
     *
     * @return
     */
    @GET("v/topic/didAttend")
    Observable<BaseEntity<DidAttendEntity>> didAttend();


    /**
     * 传递详情（适用地图版）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/moreDetailsForMap")
    Observable<BaseEntity<MoreDetailsForMapEntity>> moreDetailsForMap(@Field("message_id") int message_id);


    /**
     * 话题标签
     *
     * @return
     */
    @GET("v/topic/tags/list")
    Observable<BaseEntity<List<TopicTagsEntity>>> tagslist();

    /**
     * 地图上保险柜
     *
     * @return
     */
    @GET("v/sdb/get_box")
    Observable<BaseEntity<List<BoxEntity>>> getbox();


    /**
     * 开保险柜
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/sdb/open_box")
    Observable<BaseEntity<OpenBoxEntity>> openbox(@Field("key") int key, @Field("type") int type, @Field("code") String code,@Field("is_kongtou") int is_kongtou);


    /**
     * 填写快递信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/sdb/add_express")
    Observable<BaseEntity> addexpress(@Field("safe_box_open_record_id") int id, @Field("people_name") String people_name, @Field("mobile") String mobile, @Field("address") String address);

    /**
     * 星球口令
     *
     * @return
     */
    @GET("v/planet/password")
    Observable<BaseEntity<PlanetPasswordEntity>> planetpassword();

    /**
     * 盲盒奖品预览（城市分布盲盒）
     *
     * @return
     */
    @GET("v/award/previewBox")
    Observable<BaseEntity<PreviewBoxEntity>> previewBox();


    /**
     * 每日开盲盒，购买下订单
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/order/createOrderOpenBoxDaily")
    Observable<BaseEntity<CreateOrderOpenBoxEntity>> createOrderOpenBoxDaily(@Field("box_no") String box_no, @Field("box_type") int box_type);


    /**
     * 开保险柜记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/sdb/open_box_list")
    Observable<BaseEntity<OpenBoxListEntity>> openboxlist(@Field("page") int page, @Field("limit") int limit);


    /**
     * 我的优惠券
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/coupons/mine")
    Observable<BaseEntity<CouponsMineEntity>> couponsmine(@Field("page") int page, @Field("limit") int limit,@Field("status") int status,@Field("use_scene") String use_scene);



    /**
     *支付可用优惠券列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/coupons/listForScene")
    Observable<BaseEntity<CouponsMineEntity>> listForScene(@Field("page") int page, @Field("limit") int limit,@Field("status") int status,@Field("use_scene") String use_scene);


    /**
     * 可用券数量
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/coupons/available")
    Observable<BaseEntity<AvailableEntity>> available(@Field("use_scene") String scene);

}
