package com.example.administrator.ccoupons.User.UserCouponFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.User.UserCouponInfoAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/21 0021.
 */
//未上架的优惠券
public class NotOnSaleCouponFragment extends Fragment{

    RecyclerView recyclerView;
    private UserCouponInfoAdapter adapter;
    private ArrayList<Coupon> mCouponList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_user_notonsalecoupon, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.notonsale_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mCouponList = (ArrayList<Coupon>) getArguments().getSerializable("coupons");
        adapter = new UserCouponInfoAdapter(mCouponList);
        recyclerView.setAdapter(adapter);
        return view;
    }


}
