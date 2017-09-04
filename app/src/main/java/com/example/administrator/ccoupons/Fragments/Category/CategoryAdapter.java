package com.example.administrator.ccoupons.Fragments.Category;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {



    private Context mContext;
    private ArrayList<Category> mCategoryList;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category_imageview) ImageView imageView;
        @BindView(R.id.category_textview) TextView textView;

        LinearLayout rootView;
        public CategoryViewHolder(View view) {
            super(view);
            rootView = (LinearLayout)view;
            ButterKnife.bind(this, view);
        }
    }


    public CategoryAdapter(ArrayList<Category> cList) {
        this.mCategoryList = cList;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_item, parent, false);
        final CategoryViewHolder holder = new CategoryViewHolder(view);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Category category = mCategoryList.get(position);
                Intent intent = new Intent(mContext, CategorySearchActivity.class);
                intent.putExtra("categoryId", (position + 1) + "");
                mContext.startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = mCategoryList.get(position);
        holder.textView.setText(category.getProduct());
        Glide.with(mContext)
                .load(category.getResId())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }
}
