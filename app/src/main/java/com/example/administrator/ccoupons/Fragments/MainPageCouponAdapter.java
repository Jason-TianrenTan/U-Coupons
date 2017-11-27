package com.example.administrator.ccoupons.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.FontUtils.FontUtils;
import com.example.administrator.ccoupons.User.CouponDetail.CouponDetailActivity;
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
        @BindView(R.id.coupon_discount_text)
        TextView discountText;
        @BindView(R.id.coupon_expire_text)
        TextView expireText;
        @BindView(R.id.coupon_eval_text)
        TextView evalText;
        @BindView(R.id.coupon_price_text)
        TextView priceText;
        @BindView(R.id.coupon_category_text)
        TextView categoryText;

        public CouponViewHolder(View view) {
            super(view);
            if (view == footerView)
                return;
            ButterKnife.bind(this, view);
            rootView = (CardView) view;
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
        String fontname = "PingFang Light.ttf";
        Typeface typeface = FontUtils.getTypeface(mContext, fontname);
        holder.nameText.setText(coupon.getProduct());
        holder.expireText.setText("截止日期： " +coupon.getExpiredtime());
        holder.expireText.setTypeface(typeface);
        holder.priceText.setText(coupon.getListprice() + "U");
        holder.priceText.setTypeface(typeface);
        holder.evalText.setText("官方估值 " + coupon.getValue() + "U");
        holder.evalText.setTypeface(typeface);
        holder.discountText.setText(coupon.getDiscount());
        holder.discountText.setTypeface(typeface);
        holder.categoryText.setText("所属分类：" + coupon.getCategory());
        holder.categoryText.setTypeface(typeface);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:处理点击事件
            }
        });
    }


    /**
     * set the coupon image
     *
     * @param holder
     * @param coupon
     */
    private void setImage(CouponViewHolder holder, Coupon coupon) {
<<<<<<< HEAD

        String url = coupon.getImgURL();
        ImageManager.GlideImage(url, holder.imageView, mContext);
=======
        String url = GlobalConfig.base_URL + "/static/" + coupon.getPic();
        Glide.with(mContext)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imageView);
>>>>>>> ttr
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


    /**
     * set the footerview
     *
     * @param footer
     */
    public void setFooterView(View footer) {
        footerView = footer;
        System.out.println("cat item count = " + getItemCount());
        notifyItemInserted(getItemCount() - 1);
    }
}
