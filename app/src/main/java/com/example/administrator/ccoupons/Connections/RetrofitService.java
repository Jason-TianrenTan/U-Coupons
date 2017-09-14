package com.example.administrator.ccoupons.Connections;

import com.example.administrator.ccoupons.Main.Coupon;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/8/17 0017.
 */

//Service API interface
public interface RetrofitService {

    @FormUrlEncoded
    @POST("getOwnList")//我拥有的优惠券
    Observable<CouponBean> getOwnList(@Field("userID") String userid);

    @FormUrlEncoded
    @POST("getUsedList")//我已使用过的
    Observable<CouponBean> getUsedList(@Field("userID") String userid);

    @FormUrlEncoded
    @POST("getOnSaleList")//已上架
    Observable<CouponBean> getOnSaleList(@Field("userID") String userid);

    @FormUrlEncoded
    @POST("getStoreList")//未上架
    Observable<CouponBean> getUnusedList(@Field("userID") String userid);

    @POST("homepageCoupon")//主页推荐
    Observable<CouponBean> getRecList();
}
