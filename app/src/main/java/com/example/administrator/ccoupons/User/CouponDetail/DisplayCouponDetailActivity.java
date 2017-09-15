package com.example.administrator.ccoupons.User.CouponDetail;

import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class DisplayCouponDetailActivity extends BaseDetailActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onDisplay = true;
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onKeyBack() {

    }


    @Override
    public void initBottomViews(boolean isLiked) {

    }
}
