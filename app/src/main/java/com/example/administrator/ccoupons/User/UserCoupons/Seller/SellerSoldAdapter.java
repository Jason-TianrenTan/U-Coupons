package com.example.administrator.ccoupons.User.UserCoupons.Seller;

import android.content.Context;
import android.view.ViewGroup;

import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.User.UserCouponInfoAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public class SellerSoldAdapter extends UserCouponInfoAdapter {

    private Context mContext;

    public SellerSoldAdapter(ArrayList<Coupon> clist) {
        super(clist);
    }


    @Override
    public UserCouponInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();
        return super.onCreateViewHolder(parent, viewType);
    }

}
