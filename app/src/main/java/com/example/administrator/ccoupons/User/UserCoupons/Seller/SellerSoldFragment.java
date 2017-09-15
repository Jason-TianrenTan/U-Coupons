package com.example.administrator.ccoupons.User.UserCoupons.Seller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ccoupons.Connections.UniversalPresenter;
import com.example.administrator.ccoupons.Events.CouponListEvent;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.User.CouponCommonFragment;
import com.example.administrator.ccoupons.User.UserCoupons.CouponModifiedEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public class SellerSoldFragment extends CouponCommonFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * @param clistEvent recommend list
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCall(CouponListEvent clistEvent) {
        if (clistEvent.getListname().equals("SellerSold")) {
            System.out.println("on setData at seller sold fragment");
            setData(clistEvent.getList());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCall(CouponModifiedEvent event) {
        initData();
    }


    @Override
    public void initData() {
        String sellerId = getArguments().getString("sellerId");
        adapterList = new ArrayList<>();
        adapter = new SellerSoldAdapter(adapterList);
        new UniversalPresenter().getSellerSoldByRxRetrofit(sellerId);//TODO: TO BE CHANGED
    }

}
