package com.example.administrator.ccoupons.Search;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Category;
import com.example.administrator.ccoupons.Connections.ImageFetchr;
import com.example.administrator.ccoupons.Connections.SearchThread;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.CategoryAdapter;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.Main.LoginActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.Purchase.CouponDetailActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.MessageType;
import com.example.administrator.ccoupons.UI.CustomDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    private String resultString;
    private static final String url = DataHolder.base_URL + DataHolder.requestSearch_URL;
    private static final int SEARCH_MAX_RESULT = 5;//最大获取结果数
    private ArrayList<Coupon> couponResults;
    private ResultAdapter adapter;
    private SearchThread thread;
    private CustomDialog customDialog;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessageType.CONNECTION_ERROR:
                    Toast.makeText(getApplicationContext(), "连接服务器遇到问题，请检查网络连接!", Toast.LENGTH_LONG).show();
                    customDialog.dismiss();
                    break;
                case MessageType.CONNECTION_TIMEOUT:
                    Toast.makeText(getApplicationContext(), "连接服务器超时，请检查网络连接!", Toast.LENGTH_LONG).show();
                    customDialog.dismiss();
                    break;
                case MessageType.CONNECTION_SUCCESS:
                    parseMessage(thread.getResponse());
                    customDialog.dismiss();
                    break;
            }
        }
    };

    /*
    [

    {"model": "UHuiWebApp.coupon", "pk": "002", "fields": {"brandid": 2, "catid": 1, "listprice": "1", "value": "1", "product": "\u542e\u6307\u539f\u5473\u9e21", "discount": "30", "stat": "onSale", "pic": null, "expiredtime": null}},
    {"model": "UHuiWebApp.coupon", "pk": "03", "fields": {"brandid": 3, "catid": 1, "listprice": "1", "value": "1", "product": "\u9ea6\u8fa3\u9e21\u7fc5", "discount": "10", "stat": "onSale", "pic": null, "expiredtime": null}}]
     */

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

        thread = new SearchThread(url, requestString, handler, getApplicationContext());
        thread.start();
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
            ImageFetchr fetchr = new ImageFetchr(url, holder.imageView);
            fetchr.execute();
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
