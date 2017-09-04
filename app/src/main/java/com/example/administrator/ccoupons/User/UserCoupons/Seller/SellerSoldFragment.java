package com.example.administrator.ccoupons.User.UserCoupons.Seller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ccoupons.Connections.UniversalPresenter;
import com.example.administrator.ccoupons.Events.CouponListEvent;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.User.CouponCommonFragment;

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

    @Override
    public void initData() {
        adapter = new SellerSoldAdapter(adapterList);
        new UniversalPresenter().getUserUsedByRxRetrofit();//TODO: TO BE CHANGED
    }

    @Override
    public void setData(ArrayList<Coupon> cList) {
        super.setData(cList);
        recyclerView.setAdapter(adapter);
        System.out.println("at set data, list size = " + cList.size());
        requestResults(0, 5);
    }
}
