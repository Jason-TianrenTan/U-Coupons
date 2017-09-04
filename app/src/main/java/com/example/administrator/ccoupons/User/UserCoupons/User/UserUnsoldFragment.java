package com.example.administrator.ccoupons.User.UserCoupons.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ccoupons.Connections.UniversalPresenter;
import com.example.administrator.ccoupons.Events.CouponListEvent;
import com.example.administrator.ccoupons.User.CouponCommonFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public class UserUnsoldFragment extends CouponCommonFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * @param clistEvent recommend list
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCall(CouponListEvent clistEvent) {
        if (clistEvent.getListname().equals("UserUnsold")) {
            System.out.println("on setData at unsold fragment");
            setData(clistEvent.getList());
        }
    }


    @Override
    public void initData() {
        adapterList = new ArrayList<>();
        adapter = new UserUnsoldAdapter(adapterList);
        new UniversalPresenter().getUserUnsoldByRxRetrofit();
    }

}
