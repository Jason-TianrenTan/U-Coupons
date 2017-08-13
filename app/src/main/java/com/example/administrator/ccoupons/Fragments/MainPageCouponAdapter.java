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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.Purchase.CouponDetailActivity;
import com.example.administrator.ccoupons.R;
import com.todddavies.components.progressbar.ProgressWheel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/23 0023.
 */

public class MainPageCouponAdapter extends RecyclerView.Adapter<MainPageCouponAdapter.CouponViewHolder> {


    public static final int TYPE_FOOTER = 0; //footer view
    public static final int TYPE_ITEM = 1; // normal list item

    private Context mContext;
    private ArrayList<Coupon> mCouponList;
    private View footerView;


    public class CouponViewHolder extends RecyclerView.ViewHolder {

        CardView rootView;

        @BindView(R.id.coupon_item_image)
        ImageView imageView;
        @BindView(R.id.coupon_name_text)
        TextView nameText;
        @BindView(R.id.coupon_detail_text)
        TextView detailText;
        @BindView(R.id.coupon_special_word)
        TextView specialText;
        @BindView(R.id.coupon_price_text)
        TextView priceText;
        public CouponViewHolder(View view) {
            super(view);
            if (view == footerView)
                return;
            rootView = (CardView) view;
            ButterKnife.bind(this, view);
        }

    }


    public MainPageCouponAdapter(ArrayList<Coupon> list) {
        this.mCouponList = list;
    }


    @Override
    public void onBindViewHolder(CouponViewHolder holder, int position) {
        if (footerView != null && position == (getItemCount() - 1))
            return;
        final Coupon coupon = mCouponList.get(position);
        setImage(holder, coupon);
        holder.nameText.setText(coupon.getName());
        holder.detailText.setText(coupon.getExpireDate());
        holder.priceText.setText(coupon.getListPrice() + "");
        holder.specialText.setText(coupon.getWord());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:处理点击事件
                Intent intent = new Intent(mContext, CouponDetailActivity.class);
                intent.putExtra("Coupon", coupon);
                intent.putExtra("type", "purchase");
                mContext.startActivity(intent);
            }
        });
    }


    private void setImage(CouponViewHolder holder, Coupon coupon) {
        String url = DataHolder.base_URL + "/static/" + coupon.getImgURL();
        Glide.with(mContext)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imageView);
    }


    @Override
    public int getItemViewType(int position) {
        if (footerView == null)
            return TYPE_ITEM;
        if (footerView != null && position == (getItemCount() - 1))
            return TYPE_FOOTER;
        return TYPE_ITEM;
    }


    @Override
    public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();
        if (footerView != null && viewType == TYPE_FOOTER) {
            ProgressWheel progressWheel = (ProgressWheel) footerView.findViewById(R.id.pw_spinner);
            progressWheel.startSpinning();
            return new CouponViewHolder(footerView);
        }
        return new CouponViewHolder(LayoutInflater.from(mContext).inflate(R.layout.coupon_item, parent, false));
    }


    @Override
    public int getItemCount() {
        return mCouponList.size();
    }


    public void setFooterView(View footer) {
        footerView = footer;
        notifyItemInserted(getItemCount() - 1);
    }
}
