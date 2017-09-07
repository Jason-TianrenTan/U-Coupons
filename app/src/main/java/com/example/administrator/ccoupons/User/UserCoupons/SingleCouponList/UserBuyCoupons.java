package com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList;

import android.os.Bundle;

import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.R;

public class UserBuyCoupons extends CouponCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_coupon_layout);
        setToolbarTitle("我购买的");
        initData(GlobalConfig.base_URL + GlobalConfig.requestBoughtList_URL);
    }
}
