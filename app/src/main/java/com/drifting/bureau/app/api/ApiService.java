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
 * @author ?????????1
 * @description API??????
 * @time 14:54 14:54
 */

public interface ApiService {

    /**
     * app??????
     *
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    /**
     * ????????????
     *
     * @return
     */
    @GET("n/version/update")
    Observable<BaseEntity<VersionUpdateEntity>> checkVersion();


    /**
     * ???????????????
     */
    @FormUrlEncoded
    @POST("n/send/verificationcode")
    Observable<BaseEntity> verificationcode(@Field("mobile") String mobile, @Field("status") int status);

    /**
     * ??????
     */
    @FormUrlEncoded
    @POST("n/user/login")
    Observable<BaseEntity<LoginEntity>> login(@Field("mobile") String mobile, @Field("code") String code);

    /**
     * ???????????????????????????
     */
    @FormUrlEncoded
    @POST("n/user/register")
    Observable<BaseEntity<LoginEntity>> register(@Field("mobile") String mobile, @Field("name") String code);

    /**
     * ????????????
     */
    @FormUrlEncoded
    @POST("n/user/passwordlogin")
    Observable<BaseEntity<LoginEntity>> passwordlogin(@Field("mobile") String mobile, @Field("password") String password);


    /**
     * ???????????????????????????
     */

    @GET("v/message/receive")
    Observable<BaseEntity<MessageReceiveEntity>> messagereceive();


    /**
     * ???????????????V2-???????????????
     */
    @POST("v/message/creatingwithfile/v2")
    Observable<BaseEntity<CreatewithfileEntity>> creatingwithfileword(@Body MultipartBody shortVoice);


    /**
     * ?????????????????????????????????????????????
     */
    @FormUrlEncoded
    @POST("v/sku/list")
    Observable<BaseEntity<SkuListEntity>> skulist(@Field("explore_id") int explore_id, @Field("message_id") int message_id);


    /**
     * ????????????(????????????)
     */
    @FormUrlEncoded
    @POST("v/order/createOrder")
    Observable<BaseEntity<CreateOrderEntity>> createOrder(@Field("message_id") int message_id, @Field("sku_codes") String sku_codes);


    /**
     * ??????????????????????????????
     */
    @FormUrlEncoded
    @POST("v/message/content")
    Observable<BaseEntity<MessageContentEntity>> messagecontent(@Field("message_id") int message_id);


    /**
     * ??????????????????????????????????????????
     */
    @FormUrlEncoded
    @POST("v/message/attending")
    Observable<BaseEntity> messageattending(@Field("message_id") int message_id);


    /**
     * ?????????????????????????????????????????????????????????
     */
    @FormUrlEncoded
    @POST("v/message/throw")
    Observable<BaseEntity> messagethrow(@Field("message_id") int message_id);


    /**
     * ?????????????????????????????????
     *
     * @return
     */
    @GET("v/sku/space")
    Observable<BaseEntity<List<SpaceStationEntity>>> space();

    /**
     * ???????????????????????????
     */
    @FormUrlEncoded
    @POST("v/order/createOrderSpace")
    Observable<BaseEntity<CreateOrderEntity>> createOrderSpace(@Field("sku_code") String sku_code, @Field("sku_num") String sku_num);

    /**
     * ????????????
     */
    @FormUrlEncoded
    @POST("v/order/payOrderAll")
    Observable<BaseEntity<PayOrderEntity>> payOrder(@Field("sn") String sn, @Field("terminal") String terminal,@Field("coupon_code") String coupon_code,@Field("use_scene") String use_scene);


    /**
     * ?????????????????????????????????
     *
     * @return
     */
    @GET("v/space/check")
    Observable<BaseEntity<SpaceCheckEntity>> spacecheck();

    /**
     * ?????????????????????????????????
     *
     * @return
     */
    @GET("v/space/award/preview")
    Observable<BaseEntity<List<PrizeEntity>>> awardpreview();


    /**
     * ?????????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/mysterybox/logs")
    Observable<BaseEntity<MysteryboxEntity>> mysterybox(@Field("limit") int limit);


    /**
     * ???????????????
     *
     * @return
     */
    @GET("v/space/about")
    Observable<BaseEntity<List<SpaceAboutEntity>>> spaceabout();


    /**
     * ???????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/mysterybox/mine")
    Observable<BaseEntity<MyBlindBoxEntity>> mySteryboxList(@Field("page") int page, @Field("limit") int limit);


    /**
     * ????????????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/mysterybox/open")
    Observable<BaseEntity<BoxOpenEntity>> mysteryboxopen(@Field("box_id") String box_id);

    /**
     * ????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/mysterybox/transfer")
    Observable<BaseEntity> transfer(@Field("box_id") String box_id, @Field("mobile") String mobile);


    /**
     * ????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/withdraw/apply")
    Observable<BaseEntity> withdrawapply(@Field("name") String name, @Field("account") String account, @Field("money") String money, @Field("bank_name") String bank_name, @Field("op_type") int op_type);


    /**
     * ??????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/storage/transfer")
    Observable<BaseEntity> storagetransfer(@Field("object_id") int object_id, @Field("mobile") String mobile);


    /**
     * ????????????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/info")
    Observable<BaseEntity<SpaceInfoEntity>> spaceinfo(@Field("user_id") String user_id);


    /**
     * ????????????????????????????????????
     *
     * @return
     */
    @GET("v/space/order/one")
    Observable<BaseEntity<OrderOneEntity>> orderone();


    /**
     * ??????????????????????????????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/order/detail")
    Observable<BaseEntity<OrderDetailEntity>> orderdetail(@Field("space_order_id") int space_order_id);

    /**
     * ????????????-????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/user/player")
    Observable<BaseEntity<UserInfoEntity>> userplayer(@Field("user_id") String user_id);


    /**
     * ??????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/friend/apply")
    Observable<BaseEntity> friendapply(@Field("user_id") String user_id);


    /**
     * ????????????????????????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/order/throw")
    Observable<BaseEntity> orderthrow(@Field("space_order_id") int space_order_id);


    /**
     * ???????????????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/order/making")
    Observable<BaseEntity> ordermaking(@Field("space_order_id") int space_order_id);


    /**
     * ??????????????????????????????????????????
     *
     * @return
     */
    @GET("v/space/level/current")
    Observable<BaseEntity<MySpaceStationEntity>> levelcurrent();


    /**
     * ??????????????????
     *
     * @return
     */
    @GET("v/planet/whenMove")
    Observable<BaseEntity<PlanetLocationEntity>> planetlocation();


    /**
     * ????????????(???????????????)
     *
     * @return
     */
    @GET("v/space/storage/mine")
    Observable<BaseEntity<List<MyTreasuryEntity>>> storagemine();


    /**
     * ??????
     *
     * @return
     */
    @GET("v/announcement/latest")
    Observable<BaseEntity<List<AnnouncementEntity>>> latest();


    /**
     * ????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/mine")
    Observable<BaseEntity<DriftingTrackEntity>> messagemine(@Field("page") int page, @Field("limit") int limit);


    /**
     * ??????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/details")
    Observable<BaseEntity<BarrageEntity>> messagedetails(@Field("message_id") int message_id);


    /**
     * ????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/order/madelog")
    Observable<BaseEntity<MakingRecordEntity>> ordermadelog(@Field("page") int page, @Field("limit") int limit);

    /**
     * ????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/bill/logs")
    Observable<BaseEntity<IncomeRecordEntity>> spacebillogs(@Field("page") int page, @Field("limit") int limit);


    /**
     * ??????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/team/withdrawnLogs")
    Observable<BaseEntity<IncomeRecordEntity>> withdrawnLogs(@Field("page") int page, @Field("limit") int limit);


    /**
     * ???????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/order/mine")
    Observable<BaseEntity<OrderRecordEntity>> ordermine(@Field("page") int page, @Field("limit") int limit);


    /**
     * ????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/planet/level/details")
    Observable<BaseEntity<PlanetaryDetailEntity>> plannetdetails(@Field("pl_id") int pl_id);


    /**
     * ??????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/path/attendInCity")
    Observable<BaseEntity<DeliveryDetailsEntity>> pathdetails(@Field("message_id") int message_id, @Field("code_city") String code_city, @Field("page") int page, @Field("limit") int limit);


    /**
     * ??????????????????
     *
     * @return
     */
    @GET("v/exploretype/list")
    Observable<BaseEntity<List<PlanetEntity>>> exploretypelist();


    /**
     * ????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/user/setname")
    Observable<BaseEntity> setname(@Field("name") String name);

    /**
     * ????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/feedback/add")
    Observable<BaseEntity> feedbackadd(@Field("content") String content, @Field("phone") String phone);


    /**
     * ??????????????????????????????-?????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/order/writeOffInfo")
    Observable<BaseEntity<WriteOffInfoEntity>> writeOffInfo(@Field("order_id") String order_id);


    /**
     * ????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/business/nearby")
    Observable<BaseEntity<TeaShopEntity>> nearby(@Field("name") String name, @Field("page") int page, @Field("limit") int limit, @Field("lng") String lng, @Field("lat") String lat);


    /**
     * ??????????????????
     *
     * @return
     */
    @GET("v/question/stages")
    Observable<BaseEntity<List<QuestionStagesEntity>>> stages();


    /**
     * ??????????????????????????????
     *
     * @return
     */
    @GET("v/question/listWithScene")
    Observable<BaseEntity<List<QuestionEntity>>> listWithScene();


    /**
     * ???????????????????????????
     *
     * @return
     */
    @POST("v/question/assessWithScene")
    Observable<BaseEntity<QuestionAssessEntity>> questionassess(@Body RequestBody body);


    /**
     * ????????????
     *
     * @return
     */
    @GET("v/question/assessResult")
    Observable<BaseEntity<QuestionAssessEntity>> assessResult();

    /**
     * ??????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/position/information")
    Observable<BaseEntity> information(@Field("lng") String lng, @Field("lat") String lat);


    /**
     * ???????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/storage/using")
    Observable<BaseEntity> storageusing(@Field("object_id") int object_id, @Field("object_num") int object_num);


    /**
     * ??????????????????
     *
     * @return
     */
    @GET("v/team/statistic")
    Observable<BaseEntity<TeamStatisticEntity>> team();


    /**
     * ?????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/exploreTimes")
    Observable<BaseEntity<ExploreTimesEntity>> exploreTimes(@Field("explore_id") int explore_id);


    /**
     * ??????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/report/commit")
    Observable<BaseEntity> reportcommit(@Field("message_id") int message_id, @Field("comment_id") int comment_id, @Field("report_type") int report_type, @Field("reason") String reason);


    /**
     * ??????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/platform/times")
    Observable<BaseEntity<PlatformTimesEntity>> platformtimes(@Field("explore_id") int explore_id);


    /**
     * ?????????????????????????????????
     *
     * @return
     */
    @GET("v/sysmessage/unread")
    Observable<BaseEntity<SysmessageEntity>> unread();


    /**
     * ?????????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/sysmessage/mine")
    Observable<BaseEntity<SysmessageMineEntity>> sysmessage(@Field("page") int page, @Field("limit") int limit);


    /**
     * ?????????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/sysmessage/markread")
    Observable<BaseEntity> markread(@Field("sys_msg_id") int sys_msg_id);


    /**
     * ?????????????????????????????????
     *
     * @return
     */
    @GET("v/friend/mine")
    Observable<BaseEntity<RaftingBureaufriendEntity>> friendmine();


    /**
     * ????????????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/friend/applyToMe")
    Observable<BaseEntity<FriendApplicationEntity>> applyToMe(@Field("page") int page, @Field("limit") int limit);


    /**
     * ?????????????????????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/friend/agreeRefuse")
    Observable<BaseEntity> agreeRefuse(@Field("apply_id") int apply_id, @Field("status") int status);


    /**
     * ??????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/friend/delete")
    Observable<BaseEntity> frienddelete(@Field("user_id") int user_id);


    /**
     * ???????????????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/friend/info")
    Observable<BaseEntity<FriendInfoEntity>> friendinfo(@Field("user_id") String user_id);

    /**
     * ????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/exchange")
    Observable<BaseEntity<SpaceExchangeEntity>> spaceExchange(@Field("code") String code);


    /**
     * ????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/moreDetails")
    Observable<BaseEntity<MoreDetailsEntity>> moreDetails(@Field("message_id") int message_id);


    /**
     * ??????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/friend/isFriend")
    Observable<BaseEntity<FriendEntity>> isFriend(@Field("user_id") String user_id);


    /**
     * ???????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/comment/details")
    Observable<BaseEntity<CommentDetailsEntity>> details(@Field("log_id") int log_id, @Field("level") int level, @Field("user_id") int user_id);


    /**
     * ???????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/nebula")
    Observable<BaseEntity<NebulaEntity>> messagenebula(@Field("message_id") int message_id, @Field("page") int page, @Field("limit") int limit);


    /**
     * ??????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/order/sandPayQuery")
    Observable<BaseEntity<SandPayQueryEntity>> sandPayQuery(@Field("sn") String sn, @Field("terminal") String terminal);


    /**
     * ??????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/mysterybox/open/logs")
    Observable<BaseEntity<BlindBoxRecordEntity>> openlogs(@Field("page") int page, @Field("limit") int limit);


    /**
     * ??????
     *
     * @return
     */
    @GET("v/user/unregister")
    Observable<BaseEntity> unregister();


    /**
     * ??????AR Url (?????????)
     *
     * @return
     */
    @GET("n/planet/ar")
    Observable<BaseEntity<PlanetArEntity>> planetar();

    /**
     * ??????AR Url (?????????)
     *
     * @return
     */
    @GET("v/nebula/list")
    Observable<BaseEntity<NebulaListEntity>> nebulalist();

    /**
     * ???????????????
     *
     * @return
     */
    @GET("v/startup/index")
    Observable<BaseEntity<StarUpIndexEntity>> startup();


    /**
     * ?????????????????????
     *
     * @return
     */
    @GET("v/topic/didAttend")
    Observable<BaseEntity<DidAttendEntity>> didAttend();


    /**
     * ?????????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/moreDetailsForMap")
    Observable<BaseEntity<MoreDetailsForMapEntity>> moreDetailsForMap(@Field("message_id") int message_id);


    /**
     * ????????????
     *
     * @return
     */
    @GET("v/topic/tags/list")
    Observable<BaseEntity<List<TopicTagsEntity>>> tagslist();

    /**
     * ??????????????????
     *
     * @return
     */
    @GET("v/sdb/get_box")
    Observable<BaseEntity<List<BoxEntity>>> getbox();


    /**
     * ????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/sdb/open_box")
    Observable<BaseEntity<OpenBoxEntity>> openbox(@Field("key") int key, @Field("type") int type, @Field("code") String code,@Field("is_kongtou") int is_kongtou);


    /**
     * ??????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/sdb/add_express")
    Observable<BaseEntity> addexpress(@Field("safe_box_open_record_id") int id, @Field("people_name") String people_name, @Field("mobile") String mobile, @Field("address") String address);

    /**
     * ????????????
     *
     * @return
     */
    @GET("v/planet/password")
    Observable<BaseEntity<PlanetPasswordEntity>> planetpassword();

    /**
     * ??????????????????????????????????????????
     *
     * @return
     */
    @GET("v/award/previewBox")
    Observable<BaseEntity<PreviewBoxEntity>> previewBox();


    /**
     * ?????????????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/order/createOrderOpenBoxDaily")
    Observable<BaseEntity<CreateOrderOpenBoxEntity>> createOrderOpenBoxDaily(@Field("box_no") String box_no, @Field("box_type") int box_type);


    /**
     * ??????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/sdb/open_box_list")
    Observable<BaseEntity<OpenBoxListEntity>> openboxlist(@Field("page") int page, @Field("limit") int limit);


    /**
     * ???????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/coupons/mine")
    Observable<BaseEntity<CouponsMineEntity>> couponsmine(@Field("page") int page, @Field("limit") int limit,@Field("status") int status,@Field("use_scene") String use_scene);



    /**
     *???????????????????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/coupons/listForScene")
    Observable<BaseEntity<CouponsMineEntity>> listForScene(@Field("page") int page, @Field("limit") int limit,@Field("status") int status,@Field("use_scene") String use_scene);


    /**
     * ???????????????
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/coupons/available")
    Observable<BaseEntity<AvailableEntity>> available(@Field("use_scene") String scene);

}
