package com.example.administrator.ccoupons.User;

import android.content.Context;
<<<<<<< HEAD
import android.support.v7.widget.CardView;
=======
>>>>>>> ttr
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

    String[] names = "远古传奇 远古洪荒 太古传奇 一张SS 一打SS 一酒馆SS 污津史诗招募券".split(" ");

<<<<<<< HEAD
=======
    public static final int INFO_CODE = 1943;
    public static final int TYPE_FOOTER = 0; //footer view
    public static final int TYPE_ITEM = 1; // normal list item
    private View footerView;
    protected ArrayList<Coupon> couponList;
    private CouponClickedListener listener;
>>>>>>> ttr
    private Context mContext;


    public class UserCouponInfoViewHolder extends RecyclerView.ViewHolder {
        FrameLayout rootView;
<<<<<<< HEAD
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
=======
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
>>>>>>> ttr
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
<<<<<<< HEAD
        View view = LayoutInflater.from(mContext).inflate(R.layout.usercouponinfo_item, parent, false);
        final UserCouponInfoAdapter.UserCouponInfoViewHolder holder = new UserCouponInfoAdapter.UserCouponInfoViewHolder(view);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {/*
                int position = holder.getAdapterPosition();
                Coupon coupon = mUserCouponInfoList.get(position);
                Toast.makeText(mContext, "UserCouponInfo = " + coupon.getName(), Toast.LENGTH_SHORT).show();
                Coupon coupon = mUserCouponInfoList.get(position);*/
                //Todo:获得当前Coupon编号，跳转到Coupon页面
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
=======
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
        holder.couponListText.setText("¥" + coupon.getListprice());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onCouponClicked(couponList.get(position));
            }
        });
>>>>>>> ttr
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
<<<<<<< HEAD
=======


    public void setFooterView(View footer) {
        footerView = footer;
        notifyItemInserted(getItemCount() - 1);
    }
>>>>>>> ttr
}
