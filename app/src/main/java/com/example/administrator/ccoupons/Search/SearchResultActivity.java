package com.example.administrator.ccoupons.Search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.Purchase.CouponDetailActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.ImageManager;
import com.example.administrator.ccoupons.UI.CustomDialog;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Exchanger;

public class SearchResultActivity extends AppCompatActivity {


    private String catId;
    private RecyclerView recyclerView;
    private String requestString;
    private String resultString;
    private static final int SEARCH_MAX_RESULT = 10;//最大获取结果数
    private ArrayList<Coupon> couponResults;
    private ResultAdapter adapter;
    private CustomDialog customDialog;
    private EditText editText;
    private TextView sortByDateButton, price_sortText, eval_sortText, specialText;
    private ImageView price_img, eval_img;
    private LinearLayout sortByPriceButton, sortByEvalButton;

    private boolean pricePressed = false, evalPressed = false;
    private int priceStat = 0, evalStat = 0; // 0 ascend 1 descend
    private int[] resId = {R.drawable.sort_ascend, R.drawable.sort_descend};

    //处理返回回来的json
    private void parseMessage(String response) {
        resultString = response;
        clear();
        requestResults(0);
        resetAdapter();
        customDialog.dismiss();
    }

    private void bindViews() {
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
        editText = (EditText) findViewById(R.id.input_search_result);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        price_img = (ImageView) findViewById(R.id.imageview_sort_price);
        eval_img = (ImageView) findViewById(R.id.imageview_sort_eval);
        price_sortText = (TextView) findViewById(R.id.textview_sort_price);
        eval_sortText = (TextView) findViewById(R.id.textview_sort_eval);

        sortByDateButton = (TextView) findViewById(R.id.sort_date_button);
        sortByEvalButton = (LinearLayout) findViewById(R.id.sort_eval_button);
        sortByPriceButton = (LinearLayout) findViewById(R.id.sort_listprice_button);
        sortByDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pricePressed = false;
                evalPressed = false;
                priceStat = 0;
                clearStats();
                requestSort("expiredtime");
                sortByDateButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            }
        });
        sortByEvalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = null;
                pricePressed = false;
                clearStats();
                eval_sortText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                if (evalPressed) {
                    evalStat = 1 - evalStat;
                    int id = resId[evalStat];
                    eval_img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), id));

                    if (evalStat == 0)
                        requestSort("value");
                    else
                        requestSort("-value");
                } else {
                    evalPressed = true;
                    eval_img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sort_ascend));
                    requestSort("value");
                }
            }
        });

        sortByPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evalPressed = false;
                clearStats();
                price_sortText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                if (pricePressed) {
                    priceStat = 1 - priceStat;
                    int id = resId[priceStat];
                    price_img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), id));

                    if (priceStat == 0)
                        requestSort("listprice");
                    else
                        requestSort("-listprice");
                } else {
                    pricePressed = true;
                    price_img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sort_ascend));
                    requestSort("listprice");
                }
            }
        });
    }

    private void requestSort(String type) {
        String url = DataHolder.base_URL + DataHolder.requestSearch_URL;
        HashMap<String, String> map = new HashMap<>();
        map.put("keyWord", requestString);
        map.put("order", type);
        if (catId != null && catId.length() > 0) {
            url = DataHolder.base_URL + DataHolder.requestCatSearch_URL;
            map.put("category", catId);
        }


        ConnectionManager connectionManager = new ConnectionManager(url, map);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
                System.out.println("Response = " + response);
                parseMessage(response);
            }

            @Override
            public void onConnectionTimeOut() {

            }

            @Override
            public void onConnectionFailed() {

            }
        });
        connectionManager.connect();
    }

    private void clearStats() {
        sortByDateButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        eval_sortText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        price_sortText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        price_img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sort));
        eval_img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sort));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        catId = getIntent().getStringExtra("categoryId");
        bindViews();

        couponResults = new ArrayList<>();
        setUpAdapter();

        requestString = getIntent().getStringExtra("search_string");
        editText.setText(requestString);

        requestSort("");
        customDialog = new CustomDialog(this, R.style.CustomDialog);
        customDialog.show();

    }

    private void clear() {
        int size = couponResults.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                couponResults.remove(0);
            }

            adapter.notifyItemRangeRemoved(0, size);
            adapter.notifyDataSetChanged();
        }

    }

    private void resetAdapter() {

        adapter = new ResultAdapter(couponResults);
        recyclerView.setAdapter(adapter);

        //endless listener
        recyclerView.addOnScrollListener(new EndlessOnScrollListener((LinearLayoutManager)recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int currentPage) {
                System.out.println("CurrentPage = " + currentPage);
                requestResults(couponResults.size());
            }
        });
    }

    private void setUpAdapter() {
        recyclerView = (RecyclerView) findViewById(R.id.search_result_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        resetAdapter();
    }


    private void requestResults(int start) {
        System.out.println("request results at index = " + start);
        int count = 0;
        try {
            JSONObject jsObj = new JSONObject(resultString);
            JSONArray jsonArray = jsObj.getJSONArray("coupons");
            for (int i = start; i < jsonArray.length(); i++, count++) {
                if (count >= SEARCH_MAX_RESULT)
                    break;
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Coupon coupon = Coupon.decodeFromJSON(jsonObject);
                System.out.println("Decoding coupon id = " + coupon.getCouponId());
                couponResults.add(coupon);
            }
            adapter.notifyDataSetChanged();

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
            TextView nameText, priceText, detailText, specialText;

            public ResultViewHolder(View view) {
                super(view);
                cardView = (CardView) view;
                imageView = view.findViewById(R.id.coupon_item_image);
                nameText = view.findViewById(R.id.coupon_name_text);
                priceText = view.findViewById(R.id.coupon_price_text);
                detailText = view.findViewById(R.id.coupon_detail_text);
                specialText = view.findViewById(R.id.coupon_special_word);
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
            holder.specialText.setText(coupon.getWord());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SearchResultActivity.this, CouponDetailActivity.class);
                    intent.putExtra("Coupon", coupon);
                    intent.putExtra("type", "purchase");
                    startActivity(intent);
                }
            });
        }

        private void setImage(ResultViewHolder holder, Coupon coupon) {
            String url = DataHolder.base_URL + "/static/" + coupon.getImgURL();
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
