package com.example.administrator.ccoupons.User.UserCoupons.Seller;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.User.CouponDetail.CouponDetailActivity;
import com.example.administrator.ccoupons.User.UserCouponInfoAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public class SellerOnsaleAdapter extends UserCouponInfoAdapter {

    private Context mContext;


    /**
     * Default constructor
     * @param clist
     */
    public SellerOnsaleAdapter(ArrayList<Coupon> clist) {
        super(clist);
        setCouponClickListener(new UserCouponInfoAdapter.CouponClickedListener() {
            @Override
            public void onCouponClicked(Coupon coupon) {
                Intent intent = new Intent(mContext, CouponDetailActivity.class);
                intent.putExtra("Coupon", coupon);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public UserCouponInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();
        return super.onCreateViewHolder(parent, viewType);
    }

}
