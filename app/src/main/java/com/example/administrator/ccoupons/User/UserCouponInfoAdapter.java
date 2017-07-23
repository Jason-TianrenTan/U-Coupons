package com.example.administrator.ccoupons.User;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class UserCouponInfoAdapter extends RecyclerView.Adapter<UserCouponInfoAdapter.UserCouponInfoViewHolder> {

    String[] names = "远古传奇 远古洪荒 太古传奇 一张SS 一打SS 一酒馆SS 污津史诗招募券".split(" ");

    private Context mContext;
    private ArrayList<Coupon> mUserCouponInfoList;

    public static class UserCouponInfoViewHolder extends RecyclerView.ViewHolder {
        FrameLayout rootView;
        TextView couponListText,
                couponEvalText,
                couponNameText,
                couponDetailText,
                couponExpireText;
        public UserCouponInfoViewHolder(View view) {
            super(view);
            rootView = (FrameLayout)view;
            couponListText = (TextView)view.findViewById(R.id.coupon_listprice_text);
            couponEvalText = (TextView)view.findViewById(R.id.coupon_evalprice_text);
            couponNameText = (TextView)view.findViewById(R.id.usercoupon_name_text);
            couponDetailText = (TextView)view.findViewById(R.id.coupon_detail_text);
            couponExpireText = (TextView)view.findViewById(R.id.coupon_detail_expire_date);
        }
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
            public void onClick(View view) {/*
                int position = holder.getAdapterPosition();
                Coupon coupon = mUserCouponInfoList.get(position);*/
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(UserCouponInfoAdapter.UserCouponInfoViewHolder holder, int position) {
     //   Coupon coupon = mUserCouponInfoList.get(position);
        Random random = new Random();
        int index = random.nextInt(names.length);
        holder.couponNameText.setText(names[index]);
        //TODO:有待完善
    }

    @Override
    public int getItemCount() {
        return mUserCouponInfoList.size();
    }
}
