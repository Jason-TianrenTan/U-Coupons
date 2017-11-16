package com.example.administrator.ccoupons.Things;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ccoupons.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by CZJ on 2017/11/16.
 */

public class ThingAdapter extends RecyclerView.Adapter<ThingAdapter.ThingViewHolder> {

    private Context mContext;
    private ArrayList<Thing> mThingList;

    public static class ThingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thing_img)
        ImageView thingImg;
        @BindView(R.id.thing_name)
        TextView thingName;
        @BindView(R.id.thing_brand)
        TextView thingBrand;
        @BindView(R.id.thing_cat)
        TextView thingCat;
        @BindView(R.id.thing_prize)
        TextView thingPrize;
        @BindView(R.id.thing_number)
        TextView thingNumber;

        LinearLayout rootView;

        public ThingViewHolder(View view) {
            super(view);
            rootView = (LinearLayout) view;
            ButterKnife.bind(this, view);
        }
    }


    public ThingAdapter(ArrayList<Thing> tList) {
        this.mThingList = tList;
    }

    @Override
    public ThingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.thing_item, parent, false);
        final ThingViewHolder holder = new ThingViewHolder(view);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo:跳转至实物页面
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ThingViewHolder holder, int position) {
        Thing thing = mThingList.get(position);
        holder.thingImg.setImageResource(thing.getImgId());
        holder.thingName.setText(thing.getName());
        holder.thingBrand.setText("品牌：" + thing.getBrand());
        holder.thingCat.setText("种类：" + thing.getCategory());
        holder.thingPrize.setText("￥" + thing.getPrize());
        holder.thingNumber.setText("剩余数量：" + thing.getNumber());
    }

    @Override
    public int getItemCount() {
        return mThingList.size();
    }
}
