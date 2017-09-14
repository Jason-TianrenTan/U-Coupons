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
    @POST("getUsedList")//我已使用过的
    Observable<CouponBean> getUsedList(@Field("userID") String userid);

    @FormUrlEncoded
    @POST("getOnSaleList")//已上架
    Observable<CouponBean> getOnSaleList(@Field("userID") String userid);

    @FormUrlEncoded
    @POST("getStoreList")//未上架
    Observable<CouponBean> getUnusedList(@Field("userID") String userid);

    @FormUrlEncoded
    @POST("getSoldList")//未上架
    Observable<CouponBean> getSoldList(@Field("userID") String userid);

    @FormUrlEncoded
    @POST("getBoughtList")//已经购买的
    Observable<CouponBean> getBoughtList(@Field("userID") String userid);

    @FormUrlEncoded
    @POST("getLikeList")//已经购买的
    Observable<CouponBean> getFollowList(@Field("userID") String userid);

    @POST("homepageCoupon")//主页推荐
    Observable<CouponBean> getRecList();

    @FormUrlEncoded
    @POST("/searchForAndroid")//搜索
    Observable<CouponBean> getSearchResultList(@Field("keyWord") String keyWord, @Field("order") String order);

    @FormUrlEncoded
    @POST("/searchInCategory")//分类搜索
    Observable<CouponBean> getCatSearchResultList(@Field("keyWord") String keyWord,
                                                  @Field("order") String order, @Field("catId") String catId);

    @FormUrlEncoded
    @POST("/sellerOnSaleList")//卖家正在出售
    Observable<CouponBean> getSellerOnsaleList(@Field("sellerID") String sellerId);


    @FormUrlEncoded
    @POST("/sellerSoldList")//卖家已卖出
    Observable<CouponBean> getSellerSoldList(@Field("sellerID") String sellerId);

}
