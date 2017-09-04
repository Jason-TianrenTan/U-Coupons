package com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList;

import android.os.Bundle;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList.CouponCommonActivity;

public class UserBuyCoupons extends CouponCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_coupon_layout);
        setToolbarTitle("我购买的");
        initData(DataHolder.base_URL + DataHolder.requestBoughtList_URL);
    }
}
