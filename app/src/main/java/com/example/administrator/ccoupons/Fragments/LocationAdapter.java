package com.example.administrator.ccoupons.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private Context mContext;
    private ArrayList<String> mLocationList;
    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        LinearLayout rootView;

        public LocationViewHolder(View view) {
            super(view);
            rootView = (LinearLayout) view;
            textView = (TextView) view.findViewById(R.id.city_name_textview);
        }
    }


    public LocationAdapter(ArrayList<String> cList) {
        this.mLocationList = cList;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.location_item, parent, false);
        final LocationViewHolder holder = new LocationViewHolder(view);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                String Location = mLocationList.get(position);
                Toast.makeText(mContext, "Location = " + Location, Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        String Location = mLocationList.get(position);
        holder.textView.setText(Location);
        if (Location.length() == 1) {
            holder.textView.setTextColor(ContextCompat.getColor(mContext.getApplicationContext(),R.color.colorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        return mLocationList.size();
    }
}
