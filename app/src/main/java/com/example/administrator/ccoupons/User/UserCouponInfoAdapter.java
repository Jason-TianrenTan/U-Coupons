package com.example.administrator.ccoupons.User;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.todddavies.components.progressbar.ProgressWheel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public abstract class UserCouponInfoAdapter extends RecyclerView.Adapter<UserCouponInfoAdapter.UserCouponInfoViewHolder> {


    public static final int INFO_CODE = 1943;
    public static final int TYPE_FOOTER = 0; //footer view
    public static final int TYPE_ITEM = 1; // normal list item
    private View footerView;
    protected ArrayList<Coupon> couponList;
    private CouponClickedListener listener;
    private Context mContext;


    public class UserCouponInfoViewHolder extends RecyclerView.ViewHolder {
        FrameLayout rootView;
        @BindView(R.id.coupon_listprice_text)
        TextView couponListText;
        @BindView(R.id.coupon_evalprice_text)
        TextView couponEvalText;
        @BindView(R.id.usercoupon_name_text)
        TextView couponNameText;
        @BindView(R.id.usercoupon_discount_text)
        TextView couponDiscountText;
        @BindView(R.id.usercoupon_expire_text)
        TextView couponExpireText;

        public UserCouponInfoViewHolder(View view) {
            super(view);
            if (view == footerView)
                return;
            ButterKnife.bind(this, view);
            rootView = (FrameLayout) view;
        }
    }


    public UserCouponInfoAdapter(ArrayList<Coupon> cList) {
        this.couponList = cList;
    }

    /**
     * purchase -> 关注页面
     * seller -> 含出售者信息
     * show -> 自己的优惠券
     */
    @Override
    public UserCouponInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        if (footerView != null && viewType == TYPE_FOOTER) {
            System.out.println("FOOTERVIEW");
            ProgressWheel progressWheel = (ProgressWheel)footerView.findViewById(R.id.pw_spinner);
            progressWheel.startSpinning();
            return new UserCouponInfoViewHolder(footerView);
        }
        return new UserCouponInfoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.usercouponinfo_item, parent, false));
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
    public void onBindViewHolder(UserCouponInfoViewHolder holder, final int position) {
        if (footerView != null && position == (getItemCount() - 1))
            return;
        Coupon coupon = couponList.get(position);
        holder.couponNameText.setText(coupon.getProduct());
        holder.couponDiscountText.setText(coupon.getDiscount());
        holder.couponExpireText.setText(coupon.getExpiredtime());
        holder.couponEvalText.setText("¥" + coupon.getValue());
        //holder.couponListText.setText("¥" + coupon.getListprice());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onCouponClicked(couponList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return couponList.size();
    }


    public void setCouponClickListener(CouponClickedListener listener) {
        this.listener = listener;
    }

    public interface CouponClickedListener {
        public abstract void onCouponClicked(Coupon coupon);
    }


    public void setFooterView(View footer) {
        footerView = footer;
        notifyItemInserted(getItemCount() - 1);
    }
}
