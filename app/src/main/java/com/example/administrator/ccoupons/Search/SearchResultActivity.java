package com.example.administrator.ccoupons.Search;

import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
import android.os.Handler;
import android.os.Message;
=======
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
>>>>>>> ttr
import android.support.v4.content.ContextCompat;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
<<<<<<< HEAD
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
import com.example.administrator.ccoupons.Tools.ImageManager;
import com.example.administrator.ccoupons.Tools.MessageType;
import com.example.administrator.ccoupons.UI.CustomDialog;
=======
import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Connections.UniversalPresenter;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Events.CouponListEvent;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.User.CouponDetail.CouponDetailActivity;
import com.todddavies.components.progressbar.ProgressWheel;
>>>>>>> ttr

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
<<<<<<< HEAD

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
=======
import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SearchResultActivity extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    SearchCommonFragment fragment;
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
                fragment.requestSort("expiredtime");
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
                        fragment.requestSort("value");
                    else
                        fragment.requestSort("-value");
                } else {
                    evalPressed = true;
                    eval_img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sort_ascend));
                    fragment.requestSort("value");
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
                        fragment.requestSort("listprice");
                    else
                        fragment.requestSort("-listprice");
                } else {
                    pricePressed = true;
                    price_img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sort_ascend));
                    fragment.requestSort("listprice");
                }
                break;
        }
    }

    private String requestString;
    private boolean pricePressed = false, evalPressed = false;
    private int priceStat = 0, evalStat = 0; // 0 ascend 1 descend
    private int[] resId = {R.drawable.sort_ascend, R.drawable.sort_descend};


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
     * clear button view states
     */
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

        requestString = getIntent().getStringExtra("search_string");
        editText.setText(requestString);

        Bundle bundle = new Bundle();
        bundle.putString("keyWord", requestString);
        if (catId != null) {
            bundle.putString("catId", catId);
            System.out.println("put string in bundle 'catid = '" + catId);
            fragment = new SearchCatResultFragment();
        }
        else
            fragment = new SearchResultFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.search_result_fragment, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.show(fragment);
    }

<<<<<<< HEAD
>>>>>>> ttr
=======
    /*
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
        //    @BindView(R.id.coupon_detail_text)
            TextView detailText;
        //    @BindView(R.id.coupon_special_word)
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
            holder.specialText.setText("限量优惠");
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
    */
>>>>>>> Czj
}
