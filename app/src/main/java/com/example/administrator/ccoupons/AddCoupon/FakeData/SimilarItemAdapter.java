package com.example.administrator.ccoupons.AddCoupon.FakeData;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class SimilarItemAdapter extends RecyclerView.Adapter<SimilarItemAdapter.SimilarViewHolder> {


    private Context mContext;
    private ArrayList<FakeCoupon> cList;

    public static class SimilarViewHolder extends RecyclerView.ViewHolder {


        CardView rootView;
        @BindView(R.id.similar_item_image)
        ImageView img;
        @BindView(R.id.similar_name_text)
        TextView couponNameText;
        @BindView(R.id.similar_discount_text)
        TextView discountText;
        @BindView(R.id.similar_brand_text)
        TextView brandText;
        @BindView(R.id.similar_category_text)
        TextView categoryText;
        @BindView(R.id.similar_price_text)
        TextView priceText;
        public SimilarViewHolder(View view) {
            super(view);
            rootView = (CardView) view;
            ButterKnife.bind(this, view);
        }
    }


    public SimilarItemAdapter(ArrayList<FakeCoupon> fakeCoupons) {
        this.cList = fakeCoupons;
    }

    @Override
    public SimilarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.similar_item, parent, false);
        SimilarViewHolder holder = new SimilarViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SimilarViewHolder holder, int position) {
        FakeCoupon coupon = cList.get(position);
        holder.couponNameText.setText(coupon.getName());
        holder.brandText.setText("品牌：" + coupon.getBrand());
        holder.discountText.setText(coupon.getDicount());
        holder.categoryText.setText("分类：" + coupon.getCategory());
        holder.priceText.setText(coupon.getPrice() + "U");
        Glide.with(mContext)
                .load( GlobalConfig.base_URL + "/static/" + coupon.getImg_url())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return cList.size();
    }
}
