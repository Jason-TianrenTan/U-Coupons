package com.example.administrator.ccoupons.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ccoupons.Connections.ImageFetchr;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.Purchase.CouponDetailActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Search.SearchResultActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/23 0023.
 */

public class MainPageCouponAdapter extends RecyclerView.Adapter<MainPageCouponAdapter.CouponViewHolder>{

    private Context mContext;
    private ArrayList<Coupon> mCouponList;
    public class CouponViewHolder extends RecyclerView.ViewHolder {

        //Card Item
        CardView rootView;
        ImageView imageView;
        TextView nameText, priceText, detailText;

        public CouponViewHolder(View view) {
            super(view);
            rootView = (CardView) view;
            imageView = view.findViewById(R.id.coupon_item_image);
            nameText = view.findViewById(R.id.coupon_name_text);
            priceText = view.findViewById(R.id.coupon_price_text);
            detailText = view.findViewById(R.id.coupon_detail_text);
        }

    }

    public MainPageCouponAdapter(ArrayList<Coupon> list) {
        this.mCouponList = list;
    }

    @Override
    public void onBindViewHolder(CouponViewHolder holder, int position) {
        final Coupon coupon = mCouponList.get(position);
        setImage(holder, coupon);
        holder.nameText.setText(coupon.getName());
        holder.detailText.setText(coupon.getExpireDate());
        holder.priceText.setText(coupon.getListPrice() + "");
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //TODO:处理点击事件
            }
        });
    }

    private void setImage(CouponViewHolder holder, Coupon coupon) {

        String url = coupon.getImgURL();
        ImageFetchr fetchr = new ImageFetchr(url, holder.imageView, false);
        fetchr.execute();
    }

    @Override
    public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.coupon_item, parent, false);
        return new CouponViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mCouponList.size();
    }

}
