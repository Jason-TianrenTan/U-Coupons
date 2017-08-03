package com.example.administrator.ccoupons.User.UserCouponFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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


public class UsedCouponFragment extends Fragment {


    private UserCouponInfoAdapter adapter;
    private ArrayList<Coupon> mCouponList;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_user_unusedcoupon, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.unused_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mCouponList = (ArrayList<Coupon>) getArguments().getSerializable("coupons");
        adapter = new UserCouponInfoAdapter(mCouponList);
        recyclerView.setAdapter(adapter);
        return view;
    }



}