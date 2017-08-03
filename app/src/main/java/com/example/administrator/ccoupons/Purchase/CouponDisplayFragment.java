package com.example.administrator.ccoupons.Purchase;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.User.UserCouponInfoAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class CouponDisplayFragment extends Fragment {

    private UserCouponInfoAdapter adapter;
    private ArrayList<Coupon> mCouponList;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_coupon_display, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.display_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mCouponList = (ArrayList<Coupon>) getArguments().getSerializable("coupons");
        System.out.println("My coupon list length = " + mCouponList.size());
        int index = (int)getArguments().getSerializable("index");
        adapter = new UserCouponInfoAdapter(mCouponList);
        adapter.setIndex(index);
        adapter.stub();
        recyclerView.setAdapter(adapter);
        return view;
    }


    public void clear() {
        int size = mCouponList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mCouponList.remove(0);
            }

            adapter.notifyItemRangeRemoved(0, size);
            adapter.notifyDataSetChanged();
        }

    }

    public void update(ArrayList<Coupon> newList) {
        mCouponList = newList;
        adapter = new UserCouponInfoAdapter(mCouponList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}