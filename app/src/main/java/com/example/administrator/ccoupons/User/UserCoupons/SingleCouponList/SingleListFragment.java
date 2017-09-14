package com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.Search.EndlessOnScrollListener;
import com.example.administrator.ccoupons.User.CouponCommonFragment;
import com.example.administrator.ccoupons.User.UserCoupons.Seller.SellerSoldAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

/**
 * Temporary fragment
 * Waiting for new API
 */
public class SingleListFragment extends CouponCommonFragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        fullList = (ArrayList<Coupon>) bundle.getSerializable("coupons");
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void initData() {
        adapter = new SellerSoldAdapter(adapterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnScrollListener(new EndlessOnScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore() {
                requestResults(adapterList.size(), COUPON_MAX_RESULT);
            }
        });
        recyclerView.setAdapter(adapter);
        requestResults(0, 5);
    }

}
