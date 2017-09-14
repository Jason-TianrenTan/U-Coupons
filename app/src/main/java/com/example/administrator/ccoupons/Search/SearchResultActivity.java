package com.example.administrator.ccoupons.Search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
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

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Connections.UniversalPresenter;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Events.CouponListEvent;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.User.CouponDetail.CouponDetailActivity;
import com.example.administrator.ccoupons.R;
import com.todddavies.components.progressbar.ProgressWheel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchResultActivity extends AppCompatActivity {


    SearchCommonFragment fragment;
    public static final int SEARCH_MAX_RESULT = 4;//最大获取结果数
    private String catId;

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
        System.out.println("cat id = " + catId);
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

}
