package com.drifting.bureau.app.api;


import com.bumptech.glide.annotation.GlideType;
import com.drifting.bureau.mvp.model.entity.BarrageEntity;
import com.drifting.bureau.mvp.model.entity.BoxOpenEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CreatewithfileEntity;
import com.drifting.bureau.mvp.model.entity.CustomerEntity;
import com.drifting.bureau.mvp.model.entity.DeliveryDetailsEntity;
import com.drifting.bureau.mvp.model.entity.DriftingTrackEntity;
import com.drifting.bureau.mvp.model.entity.IncomeRecordEntity;
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
import com.drifting.bureau.mvp.model.entity.PayOrderEntity;
import com.drifting.bureau.mvp.model.entity.PlanetEntity;
import com.drifting.bureau.mvp.model.entity.PlanetaryDetailEntity;
import com.drifting.bureau.mvp.model.entity.PrizeEntity;
import com.drifting.bureau.mvp.model.entity.SkuListEntity;
import com.drifting.bureau.mvp.model.entity.SpaceCheckEntity;
import com.drifting.bureau.mvp.model.entity.SpaceInfoEntity;
import com.drifting.bureau.mvp.model.entity.SpaceStationEntity;
import com.drifting.bureau.mvp.model.entity.UserEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
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
    Observable<BaseEntity<CreatewithfileEntity>> createwithword(@Field("type_id") int status, @Field("explore_id") int explore_id, @Field("content") String content,@Field("message_id") int message_id);


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
    Observable<BaseEntity<MysteryboxEntity>> mysterybox( @Field("limit") int limit);


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
    Observable<BaseEntity> transfer(@Field("box_id") String box_id,@Field("mobile") String mobile);



    /**
     *申请提现
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/withdraw/apply")
    Observable<BaseEntity> withdrawapply(@Field("name") String name,@Field("account") String account,@Field("money") String money);



    /**
     * 转赠物品（含空间站）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/storage/transfer")
    Observable<BaseEntity> storagetransfer(@Field("object_id") int object_id,@Field("mobile") String mobile);




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
    Observable<BaseEntity<DeliveryDetailsEntity>> pathdetails(@Field("message_id") int message_id,@Field("page") int page, @Field("limit") int limit);


    /**
     * 关于我
     *
     * @return
     */
    @GET("v/user/home")
    Observable<BaseEntity<UserEntity>> userhome();

    /**
     * 探索方式列表
     *
     * @return
     */
    @GET("v/exploretype/list")
    Observable<BaseEntity<List<PlanetEntity>>>exploretypelist();


    /**
     *设置昵称
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/user/setname")
    Observable<BaseEntity> setname(@Field("name") String name);

    /**
     *意见反馈
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/feedback/add")
    Observable<BaseEntity> feedbackadd(@Field("content") String content,@Field("phone") String phone);

}
