package com.example.administrator.ccoupons.Search;

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
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.User.CouponDetail.CouponDetailActivity;
import com.example.administrator.ccoupons.User.CouponDetail.MyCouponDetailActivity;
import com.example.administrator.ccoupons.User.UserCouponInfoAdapter;
import com.todddavies.components.progressbar.ProgressWheel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {


    protected ArrayList<Coupon> couponList;
    private UserCouponInfoAdapter.CouponClickedListener listener;
    private View footerView;
    private Context mContext;
    private ArrayList<Coupon> mCouponList;
    public static final int TYPE_FOOTER = 0; //footer view
    public static final int TYPE_ITEM = 1; // normal list item


    public class ResultViewHolder extends RecyclerView.ViewHolder {


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

        public ResultViewHolder(View view) {
            super(view);
            if (view == footerView)
                return;
            rootView = (CardView) view;
            ButterKnife.bind(this, view);
        }

    }

    /**
     * Default constructor
     * @param cList
     */
    public ResultAdapter(ArrayList<Coupon> cList) {
        mCouponList = cList;
        this.setCouponClickListener(new UserCouponInfoAdapter.CouponClickedListener() {
            @Override
            public void onCouponClicked(Coupon coupon) {
                Intent intent = new Intent(mContext, CouponDetailActivity.class);
                intent.putExtra("Coupon", coupon);
                mContext.startActivity(intent);
            }
        });

    }


    /**
     * return type for ViewHolder at position
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (footerView == null)
            return TYPE_ITEM;
        if (footerView != null && position == (getItemCount() - 1))
            return TYPE_FOOTER;
        return TYPE_ITEM;
    }


    @Override
    public void onBindViewHolder(ResultViewHolder holder, final int position) {
        if (footerView != null && position == (getItemCount() - 1))
            return;
        final Coupon coupon = mCouponList.get(position);
        setImage(holder, coupon);
        holder.nameText.setText(coupon.getProduct());
        holder.detailText.setText(coupon.getExpiredtime());
        holder.priceText.setText(coupon.getListprice() + "");
        holder.specialText.setText("估值: " + coupon.getValue());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onCouponClicked(coupon);
            }
        });
    }


    /**
     * set coupon image
     * @param holder container of image
     * @param coupon coupon corresponding
     */
    private void setImage(ResultViewHolder holder, Coupon coupon) {
        String url = GlobalConfig.base_URL + "/static/" + coupon.getPic();
        Glide.with(mContext).load(url).into(holder.imageView);
    }


    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        if (footerView != null && viewType == TYPE_FOOTER) {
            ProgressWheel progressWheel = (ProgressWheel) footerView.findViewById(R.id.pw_spinner);
            progressWheel.startSpinning();
            return new ResultViewHolder(footerView);
        }
        return new ResultViewHolder(LayoutInflater.from(mContext).inflate(R.layout.coupon_item, parent, false));
    }


    @Override
    public int getItemCount() {
        return mCouponList.size();
    }


    /**
     * set footer view
     * @param footer
     */
    public void setFooterView(View footer) {
        footerView = footer;
        notifyItemInserted(getItemCount() - 1);
    }


    /**
     *
     * @param listener
     */
    public void setCouponClickListener(UserCouponInfoAdapter.CouponClickedListener listener) {
        this.listener = listener;
    }


}

