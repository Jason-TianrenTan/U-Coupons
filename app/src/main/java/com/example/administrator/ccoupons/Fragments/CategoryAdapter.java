package com.example.administrator.ccoupons.Fragments;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Category;
import com.example.administrator.ccoupons.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Category> mCategoryList;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView)view;
            imageView = (ImageView)view.findViewById(R.id.category_imageview);
            textView = (TextView)view.findViewById(R.id.category_textview);
        }
    }

    public CategoryAdapter(ArrayList<Category> cList) {
        this.mCategoryList = cList;
    }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.category_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder,int position) {
            Category category = mCategoryList.get(position);
            holder.textView.setText(category.getName());
            Glide.with(mContext).load(category.getResId()).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return mCategoryList.size();
        }
}
