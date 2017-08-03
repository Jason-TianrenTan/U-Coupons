package com.example.administrator.ccoupons.User;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.Purchase.CouponDetailActivity;
import com.example.administrator.ccoupons.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class UserCouponInfoAdapter extends RecyclerView.Adapter<UserCouponInfoAdapter.UserCouponInfoViewHolder> {


    int index = 0;
    private Context mContext;
    private ArrayList<Coupon> mUserCouponInfoList;
    public static class UserCouponInfoViewHolder extends RecyclerView.ViewHolder {
        FrameLayout rootView;
        TextView couponListText,
                couponEvalText,
                couponNameText,
                couponDiscountText,
                couponExpireText;

        public UserCouponInfoViewHolder(View view) {
            super(view);
            rootView = (FrameLayout) view;
            couponListText = (TextView) view.findViewById(R.id.coupon_listprice_text);
            couponEvalText = (TextView) view.findViewById(R.id.coupon_evalprice_text);
            couponNameText = (TextView) view.findViewById(R.id.usercoupon_name_text);
            couponExpireText = (TextView) view.findViewById(R.id.usercoupon_expire_text);
            couponDiscountText = (TextView) view.findViewById(R.id.usercoupon_discount_text);
        }
    }


    public void setIndex(int i) {
        this.index = i;
        System.out.println("At set index = " + i);
    }
    public UserCouponInfoAdapter(ArrayList<Coupon> cList) {
        this.mUserCouponInfoList = cList;
    }

    @Override
    public UserCouponInfoAdapter.UserCouponInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.usercouponinfo_item, parent, false);
        final UserCouponInfoAdapter.UserCouponInfoViewHolder holder = new UserCouponInfoAdapter.UserCouponInfoViewHolder(view);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Coupon coupon = mUserCouponInfoList.get(position);
                Intent intent = new Intent(mContext.getApplicationContext(), CouponDetailActivity.class);
                intent.putExtra("Coupon", coupon);
                intent.putExtra("type", "show");
                intent.putExtra("index", index + "");
                System.out.println("Clickeddlksfsdf, index = " + index);
                mContext.startActivity(intent);
                Intent broadcastIntent = new Intent("com.example.administrator.ccoupons.UPDATEVIEWS");
                mContext.sendBroadcast(broadcastIntent);
                //Todo:获得当前Coupon编号，跳转到Coupon页面
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(UserCouponInfoAdapter.UserCouponInfoViewHolder holder, int position) {
        Coupon coupon = mUserCouponInfoList.get(position);
        holder.couponNameText.setText(coupon.getName());
        holder.couponDiscountText.setText(coupon.getDiscount());
        holder.couponExpireText.setText(coupon.getExpireDate());
        holder.couponEvalText.setText("¥" + coupon.getEvaluatePrice());
        holder.couponListText.setText("¥" + coupon.getListPrice());
        System.out.println("bind view holder");
        //TODO:有待完善
    }

    @Override
    public int getItemCount() {
        return mUserCouponInfoList.size();
    }




}
