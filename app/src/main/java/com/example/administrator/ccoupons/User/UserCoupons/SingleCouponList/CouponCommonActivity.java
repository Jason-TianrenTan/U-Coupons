package com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.example.administrator.ccoupons.Events.CouponListEvent;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.User.CouponCommonFragment;
import com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList.Fragments.UserBuyFragment;
import com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList.Fragments.UserFollowFragment;
import com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList.Fragments.UserSellFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class CouponCommonActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private FrameLayout frameView;

    private CouponCommonFragment commonFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int resId) {
        super.setContentView(resId);
        initViews();
    }

    private void initViews() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        toolbar = (Toolbar) findViewById(R.id.common_coupons_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        frameView = (FrameLayout) findViewById(R.id.common_frame);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     *
     * @param title
     * title for the list
     */
    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }


    /**
     *
     * @param index
     * 0 for buy
     * 1 for follow
     * 2 for sell
     */
    public void initData(int index) {

        switch (index) {
            case 0:
                commonFragment = new UserBuyFragment();
                break;
            case 1:
                commonFragment = new UserFollowFragment();
                break;
            case 2:
                commonFragment = new UserSellFragment();
                break;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.common_frame, commonFragment);
        fragmentTransaction.commit();
        fragmentTransaction.show(commonFragment);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCall(CouponListEvent clistEvent) {

    }


}
