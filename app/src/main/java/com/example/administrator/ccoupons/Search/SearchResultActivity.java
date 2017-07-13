package com.example.administrator.ccoupons.Search;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Category;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.CategoryAdapter;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    private static final int SEARCH_MAX_RESULT = 5;//最大获取结果数
    private ArrayList<Coupon> couponResults;
    private ResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_result_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //edittext
        EditText editText = (EditText) findViewById(R.id.input_search_result);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String requestString = getIntent().getStringExtra("search_string");
        editText.setText(requestString);

        initCoupons();
        setUpAdapter();



    }


    private void setUpAdapter() {
        adapter = new ResultAdapter(couponResults);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_result_recyclerview);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //endless listener
        recyclerView.addOnScrollListener(new EndlessOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                System.out.println("CurrentPage = " + currentPage);
                requestResults(couponResults.size());
            }
        });
    }

    private void initCoupons() {
        //初始化coupon数据
        couponResults = new ArrayList<>();
        for (int i=0;i<SEARCH_MAX_RESULT;i++) {
            if (i==DataHolder.Coupons.nameList.length) {
                break;
            }
            Coupon coupon = new Coupon(DataHolder.Coupons.nameList[i],
                    DataHolder.Coupons.priceList[i],
                    DataHolder.Coupons.detailList[i],
                    DataHolder.Coupons.resIds[i]);
            couponResults.add(coupon);
            //后期加入不足MAX_RESULT个数的判断
        }
    }


    private void requestResults(int start) {

        int count = 0;
        for (int i = start; i < DataHolder.Coupons.nameList.length; i++, count++) {
            if (count >= SEARCH_MAX_RESULT)
                break;
            Coupon coupon = new Coupon(DataHolder.Coupons.nameList[i],
                    DataHolder.Coupons.priceList[i],
                    DataHolder.Coupons.detailList[i],
                    DataHolder.Coupons.resIds[i]);
            adapter.addCoupon(coupon);
            adapter.notifyDataSetChanged();
        }
    }


    private class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

        private RecyclerView mRecyclerView;
        private Context mContext;

        private ArrayList<Coupon> mCouponList;

        public void addCoupon(Coupon coupon) {
            this.mCouponList.add(coupon);
        }
        public class ResultViewHolder extends RecyclerView.ViewHolder {

            //Card Item
            CardView cardView;
            ImageView imageView;
            TextView nameText, priceText, detailText;

            public ResultViewHolder(View view) {
                super(view);
                cardView = (CardView) view;
                imageView = view.findViewById(R.id.coupon_item_image);
                nameText = view.findViewById(R.id.coupon_name_text);
                priceText = view.findViewById(R.id.coupon_price_text);
                detailText = view.findViewById(R.id.coupon_detail_text);
            }

        }

        public ResultAdapter(ArrayList<Coupon> cList) {
            mCouponList = cList;
        }

        @Override
        public void onBindViewHolder(ResultViewHolder holder, int position) {
            Coupon coupon = mCouponList.get(position);
            setImage(holder, coupon);
            holder.nameText.setText(coupon.getName());
            holder.detailText.setText(coupon.getDetail());
            holder.priceText.setText(coupon.getPrice() + "");
        }

        private void setImage(ResultViewHolder holder, Coupon coupon) {
            //url初始化...
            //getImageURL()...
            Glide.with(mContext).load(coupon.getResId()).into(holder.imageView);
        }

        @Override
        public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.coupon_item, parent, false);
            return new ResultViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return mCouponList.size();
        }
    }
}
