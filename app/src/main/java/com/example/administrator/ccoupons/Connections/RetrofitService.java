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
    @POST("post_getOwnList")
    Observable<CouponBean> getOwnList(@Field("userID") String userid);
    @POST("post_homepageCoupon")
    Observable<CouponBean> getRecList();
}
