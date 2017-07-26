package com.example.administrator.ccoupons.Search;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.Purchase.CouponDetailActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.ImageManager;
import com.example.administrator.ccoupons.UI.CustomDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultActivity extends AppCompatActivity {

    private String resultString;
    private static final String url = DataHolder.base_URL + DataHolder.requestSearch_URL;
    private static final int SEARCH_MAX_RESULT = 5;//最大获取结果数
    private ArrayList<Coupon> couponResults;
    private ResultAdapter adapter;
    private CustomDialog customDialog;

    //处理返回回来的json
    private void parseMessage(String response) {
        resultString = response;
        requestResults(0);
        customDialog.dismiss();
        //TODO:

    }

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

        couponResults = new ArrayList<>();
        setUpAdapter();

        HashMap<String,String> map = new HashMap<>();
        map.put("keyWord", requestString);
        ConnectionManager connectionManager = new ConnectionManager(url, map);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
                parseMessage(response);
                customDialog.dismiss();
            }

            @Override
            public void onConnectionTimeOut() {
                Toast.makeText(getApplicationContext(), "连接服务器超时，请检查网络连接!", Toast.LENGTH_LONG).show();
                customDialog.dismiss();
            }

            @Override
            public void onConnectionFailed() {
                Toast.makeText(getApplicationContext(), "连接服务器遇到问题，请检查网络连接!", Toast.LENGTH_LONG).show();
                customDialog.dismiss();
            }
        });
        connectionManager.connect();
        customDialog = new CustomDialog(this, R.style.CustomDialog);
        customDialog.show();

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


    private void requestResults(int start) {

        int count = 0;
        try {
            JSONObject jsObj = new JSONObject(resultString);
            JSONArray jsonArray = jsObj.getJSONArray("coupons");
            for (int i = start; i < jsonArray.length(); i++, count++) {
                if (count >= SEARCH_MAX_RESULT)
                    break;
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Coupon coupon = Coupon.decodeFromJSON(jsonObject);
                adapter.addCoupon(coupon);
                adapter.notifyDataSetChanged();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

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
            final Coupon coupon = mCouponList.get(position);
            setImage(holder, coupon);
            holder.nameText.setText(coupon.getName());
            holder.detailText.setText(coupon.getExpireDate());
            holder.priceText.setText(coupon.getListPrice() + "");
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SearchResultActivity.this, CouponDetailActivity.class);
                    intent.putExtra("Coupon", coupon);
                    startActivity(intent);
                }
            });
        }

        private void setImage(ResultViewHolder holder, Coupon coupon) {
            String url = DataHolder.base_URL + coupon.getImgURL();
            ImageManager.GlideImage(url, holder.imageView);
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
