package com.example.administrator.ccoupons.Search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.User.CouponDetail.CouponDetailActivity;
import com.todddavies.components.progressbar.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchResultActivity extends AppCompatActivity {


    public static final int SEARCH_MAX_RESULT = 4;//最大获取结果数
    private String catId;

    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.input_search_result)
    EditText editText;
    @BindView(R.id.search_result_toolbar)
    Toolbar toolbar;
    @BindView(R.id.sort_date_button)
    TextView sortByDateButton;
    @BindView(R.id.textview_sort_price)
    TextView price_sortText;
    @BindView(R.id.imageview_sort_price)
    ImageView price_img;
    @BindView(R.id.sort_listprice_button)
    LinearLayout sortByPriceButton;
    @BindView(R.id.textview_sort_eval)
    TextView eval_sortText;
    @BindView(R.id.imageview_sort_eval)
    ImageView eval_img;
    @BindView(R.id.sort_eval_button)
    LinearLayout sortByEvalButton;
    @BindView(R.id.search_result_recyclerview)
    RecyclerView recyclerView;

    @OnClick({R.id.input_search_result, R.id.sort_date_button, R.id.sort_eval_button, R.id.sort_listprice_button})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.input_search_result:
                finish();
                break;
            case R.id.sort_date_button:
                pricePressed = false;
                evalPressed = false;
                priceStat = 0;
                clearStats();
                requestSort("expiredtime");
                sortByDateButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                break;
            case R.id.sort_eval_button:
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
                break;
            case R.id.sort_listprice_button:
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
                break;
        }
    }

    private String resultString;
    private String requestString;
    private ArrayList<Coupon> couponResults;
    private ResultAdapter adapter;

    private boolean pricePressed = false, evalPressed = false;
    private int priceStat = 0, evalStat = 0; // 0 ascend 1 descend
    private int[] resId = {R.drawable.sort_ascend, R.drawable.sort_descend};

    /**
     * @param response 收到的回复
     */
    private void parseMessage(String response) {

        resultString = response;
        clear();
        requestResults(0);
        resetAdapter();
    }


    private void bindViews() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    /**
     * @param type 排序标准类型
     */
    private void requestSort(String type) {
        String url = GlobalConfig.base_URL + GlobalConfig.requestSearch_URL;
        HashMap<String, String> map = new HashMap<>();
        map.put("keyWord", requestString);
        map.put("order", type);
        if (catId != null && catId.length() > 0) {
            url = GlobalConfig.base_URL + GlobalConfig.requestCatSearch_URL;
            map.put("category", catId);
        }


        ConnectionManager connectionManager = new ConnectionManager(url, map);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
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
        ButterKnife.bind(this);

        catId = getIntent().getStringExtra("categoryId");
        bindViews();

        couponResults = new ArrayList<>();
        setUpAdapter();

        requestString = getIntent().getStringExtra("search_string");
        editText.setText(requestString);

        requestSort("");
    }

    private void setEmpty(){
        System.out.println("Size::" + couponResults.size());
        if (couponResults.isEmpty()){
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
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
        recyclerView.addOnScrollListener(new EndlessOnScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore() {
                requestResults(couponResults.size());
            }
        });
        setEmpty();
    }

    private void setUpAdapter() {
        recyclerView = (RecyclerView) findViewById(R.id.search_result_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        resetAdapter();
    }


    private void requestResults(int start) {
        System.out.println("request results at index = " + start + ", result = " + resultString);
        int count = 0;
        try {
            JSONObject obj = new JSONObject(resultString);
            JSONArray jsonArray = obj.getJSONArray("coupons");//TODO:need to change!!
            for (int i = start; i < jsonArray.length(); i++, count++) {
                if (count >= SEARCH_MAX_RESULT)
                    break;
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Coupon coupon = Coupon.decodeFromJSON(jsonObject);
                couponResults.add(coupon);
            }
            if (couponResults.size() < jsonArray.length())
                adapter.setFooterView(LayoutInflater.from(this).inflate(R.layout.load_footer, recyclerView, false));
            else
                adapter.setFooterView(null);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

        private View footerView;
        private Context mContext;
        private ArrayList<Coupon> mCouponList;
        public static final int TYPE_FOOTER = 0; //footer view
        public static final int TYPE_ITEM = 1; // normal list item


        public void addCoupon(Coupon coupon) {
            this.mCouponList.add(coupon);
        }


        public class ResultViewHolder extends RecyclerView.ViewHolder {


            CardView cardView;
            @BindView(R.id.coupon_item_image)
            ImageView imageView;
            @BindView(R.id.coupon_name_text)
            TextView nameText;
            @BindView(R.id.coupon_detail_text)
            TextView detailText;
            @BindView(R.id.coupon_special_word)
            TextView specialText;
            @BindView(R.id.coupon_price_text)
            TextView priceText;

            public ResultViewHolder(View view) {
                super(view);
                if (view == footerView)
                    return;
                cardView = (CardView) view;
                ButterKnife.bind(this, view);
            }

        }


        public ResultAdapter(ArrayList<Coupon> cList) {
            mCouponList = cList;
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
        public void onBindViewHolder(ResultViewHolder holder, int position) {
            if (position == getItemCount() - 1)
                return;
            final Coupon coupon = mCouponList.get(position);
            setImage(holder, coupon);
            holder.nameText.setText(coupon.getProduct());
            holder.detailText.setText(coupon.getExpiredtime());
            holder.priceText.setText(coupon.getListprice() + "");
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
            String url = GlobalConfig.base_URL + "/static/" + coupon.getPic();
            Glide.with(mContext).load(url).into(holder.imageView);
        }

        @Override
        public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            if (footerView != null && viewType == TYPE_FOOTER) {
                ProgressWheel progressWheel = (ProgressWheel) footerView.findViewById(R.id.pw_spinner);
                progressWheel.startSpinning();
                return new ResultViewHolder(footerView);
            }
            return new ResultViewHolder(LayoutInflater.from(mContext).inflate(R.layout.coupon_item, parent, false));
        }

        @Override
        public int getItemCount() {
            return mCouponList.size();
        }


        public void setFooterView(View footer) {
            footerView = footer;
            notifyItemInserted(getItemCount() - 1);
        }
    }
}
