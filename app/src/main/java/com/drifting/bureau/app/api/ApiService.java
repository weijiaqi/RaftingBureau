package com.drifting.bureau.app.api;


import com.drifting.bureau.mvp.model.entity.BoxOpenEntity;
import com.drifting.bureau.mvp.model.entity.CreateOrderEntity;
import com.drifting.bureau.mvp.model.entity.CustomerEntity;
import com.drifting.bureau.mvp.model.entity.LoginEntity;
import com.drifting.bureau.mvp.model.entity.MyBlindBoxEntity;
import com.drifting.bureau.mvp.model.entity.OrderDetailEntity;
import com.drifting.bureau.mvp.model.entity.OrderOneEntity;
import com.drifting.bureau.mvp.model.entity.PayOrderEntity;
import com.drifting.bureau.mvp.model.entity.SpaceCheckEntity;
import com.drifting.bureau.mvp.model.entity.SpaceInfoEntity;
import com.drifting.bureau.mvp.model.entity.SpaceStationEntity;
import com.drifting.bureau.mvp.model.entity.UserEntity;
import com.drifting.bureau.mvp.model.entity.UserInfoEntity;
import com.jess.arms.base.BaseEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
     * 获取空间站（盲盒列表）
     * @return
     */
    @GET("v/sku/space")
    Observable<BaseEntity<List<SpaceStationEntity>>> space();

    /**
     * 创建订单（空间站）
     */
    @FormUrlEncoded
    @POST("v/order/createOrderSpace")
    Observable<BaseEntity<CreateOrderEntity>> createOrderSpace(@Field("sku_code") String sku_code,@Field("sku_num") String sku_num);

    /**
     * 支付订单
     */
    @FormUrlEncoded
    @POST("v/order/payOrder")
    Observable<BaseEntity<PayOrderEntity>> payOrder(@Field("sn") String sn);



    /**
     * 获取空间站（盲盒列表）
     * @return
     */
    @GET("v/space/check")
    Observable<BaseEntity<SpaceCheckEntity>> spacecheck();


    /**
     *我的盲盒（空间站）
     * @return
     */
    @FormUrlEncoded
    @POST("v/mysterybox/mine")
    Observable<BaseEntity<MyBlindBoxEntity>> mySteryboxList(@Field("page") int page,@Field("limit") int limit);


    /**
     *开启盲盒（我的盲盒中用）
     * @return
     */
    @FormUrlEncoded
    @POST("v/mysterybox/open")
    Observable<BaseEntity<BoxOpenEntity>> mysteryboxopen(@Field("box_id") String box_id);

    /**
     *空间站信息（我的空间站）
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/info")
    Observable<BaseEntity<SpaceInfoEntity>> spaceinfo(@Field("user_id") String user_id);


    /**
     * 漂流新订单（我的空间站）
     * @return
     */
    @GET("v/space/order/one")
    Observable<BaseEntity<OrderOneEntity>> orderone();


    /**
     *新订单的详情（空间站查看漂来的订单）
     * @return
     */
    @FormUrlEncoded
    @POST("v/space/order/detail")
    Observable<BaseEntity<OrderDetailEntity>> orderdetail(@Field("space_order_id") int space_order_id);

    /**
     *查看漂流-玩家信息
     * @return
     */
    @FormUrlEncoded
    @POST("v/user/player")
    Observable<BaseEntity<UserInfoEntity>> userplayer(@Field("user_id") String user_id);


    /**
     * 关于我
     * @return
     */
    @GET("v/user/home")
    Observable<BaseEntity<UserEntity>> userhome();

    /**
     * 联系客服
     *
     * @return
     */
    @GET("n/customer/list")
    Observable<BaseEntity<List<CustomerEntity>>> customerlist();


}
