package com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ccoupons.Connections.UniversalPresenter;
import com.example.administrator.ccoupons.Events.CouponListEvent;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.User.CouponCommonFragment;
import com.example.administrator.ccoupons.User.UserCoupons.CouponModifiedEvent;
import com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList.Adapters.UserBoughtAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class UserBuyFragment extends CouponCommonFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * @param clistEvent recommend list
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCall(CouponListEvent clistEvent) {
        if (clistEvent.getListname().equals("UserBought")) {
            System.out.println("on setData at user bought fragment");
            setData(clistEvent.getList());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCall(CouponModifiedEvent event) {
        initData();
    }

    @Override
    public void initData() {
        adapterList = new ArrayList<>();
        adapter = new UserBoughtAdapter(adapterList);
        new UniversalPresenter().getUserBoughtByRxRetrofit(((MyApp)getActivity().getApplicationContext()).getUserId());
    }

}
