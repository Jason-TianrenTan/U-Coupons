package com.example.administrator.ccoupons.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ccoupons.Connections.UniversalPresenter;
import com.example.administrator.ccoupons.CouponListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public class UserOnsaleFragment extends CouponCommonFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * @param clistEvent recommend list
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCall(CouponListEvent clistEvent) {
        System.out.println("on event call at fragment");
        if (clistEvent.getListname().equals("UserOnsale"))
            setData(clistEvent.getList());
    }

    @Override
    public void initData() {
        adapter = new UserOnsaleAdapter(adapterList);
        new UniversalPresenter().getUserOnsaleByRxRetrofit();
    }
}
