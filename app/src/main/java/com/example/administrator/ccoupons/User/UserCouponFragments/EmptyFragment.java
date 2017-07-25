package com.example.administrator.ccoupons.User.UserCouponFragments;

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
 * Created by Administrator on 2017/7/22 0022.
 */

public class EmptyFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.user_fragment_empty, container, false);
        return view;
    }


}
