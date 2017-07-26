package com.example.administrator.ccoupons.Purchase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.ImageManager;
import com.example.administrator.ccoupons.Tools.PixelUtils;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import java.util.HashMap;

public class CouponDetailActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private Coupon coupon;
    private ImageView mImageView;
    private ImageView mainButton, followButton, purchaseButton;
    private ObservableScrollView mScrollView;
    private Toolbar mToolbarView;
    private int mParallaxImageHeight;
    private TextView nameText, listpriceText, evalpriceText,
            discountText, brandNameText, expireText, constaintsText, sellerNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);

        bindViews();

        setSupportActionBar(mToolbarView);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary)));

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CouponDetailActivity.this, MainPageActivity.class));
                finish();
            }
        });

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "关注成功", Toast.LENGTH_SHORT).show();
            }
        });

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = coupon.getCouponId();
                Intent intent = new Intent(CouponDetailActivity.this, CouponPurchaseActivity.class);
                intent.putExtra("coupon",coupon);
                startActivity(intent);
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screen_width = displayMetrics.widthPixels;
        mParallaxImageHeight = PixelUtils.px2dp(this, screen_width);
        showInfo();
    }

    private void bindViews() {
        mImageView = (ImageView) findViewById(R.id.coupon_image);
        mToolbarView = (Toolbar) findViewById(R.id.fading_toolbar);
        nameText = (TextView) findViewById(R.id.coupon_detail_name_text);
        listpriceText = (TextView) findViewById(R.id.coupon_detail_list_price);
        evalpriceText = (TextView) findViewById(R.id.coupon_detail_evaluate_price);
        discountText = (TextView) findViewById(R.id.coupon_detail_discount_price);
        brandNameText = (TextView) findViewById(R.id.coupon_detail_brand_name);//品牌
        expireText = (TextView) findViewById(R.id.coupon_detail_expire_date);
        constaintsText = (TextView) findViewById(R.id.coupon_detail_constraints_text);
        sellerNameText = (TextView) findViewById(R.id.coupon_detail_seller_name);

        mainButton = (ImageView) findViewById(R.id.page_button_mainpage);
        followButton = (ImageView) findViewById(R.id.page_button_follow);
        purchaseButton = (ImageView) findViewById(R.id.page_button_purchase);
    }

    private void showInfo() {
        coupon = (Coupon) getIntent().getSerializableExtra("Coupon");

        //url
        String url = DataHolder.base_URL + coupon.getImgURL();
        ImageManager.GlideImage(url, mImageView, getApplicationContext());

        //name
        String name = coupon.getName();
        nameText.setText(name);

        //list price
        double listprice = coupon.getListPrice();
        listpriceText.setText("¥" + listprice + "");

        //eval price
        double evalprice = coupon.getEvaluatePrice();
        evalpriceText.setText("¥" + evalprice + "");

        //优惠额度
        double discount = coupon.getDiscount();
        discountText.setText("¥" + discount);

        //商家名
        String brandName = "懒得起名字的公司" + coupon.getBrandName();
        brandNameText.setText(brandName);

        String expireDate = coupon.getExpireDate();
        expireText.setText(expireDate + "");


        String constraints = "后台居然懒到没加这个=.=";
        constaintsText.setText(constraints);

        HashMap<String,String> map = new HashMap<>();
        map.put("couponID", coupon.getCouponId() + "");
        ConnectionManager connectionManager = new ConnectionManager(url, map);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
                coupon.getDetails(response);
                System.out.println("Response = " + response);
                //卖家
                sellerNameText.setText(coupon.getSellerNickname());
                //商家 品牌
                brandNameText.setText(coupon.getBrandName());

                //使用限制
                String[] constraints = coupon.getConstraints();
                StringBuilder sb = new StringBuilder();
                int index = 1;
                for (String str : constraints)
                    sb.append(index + ". " + str + '\n');
                constaintsText.setText(sb.toString());
            }

            @Override
            public void onConnectionTimeOut() {
                Toast.makeText(getApplicationContext(), "连接服务器超时，请检查网络连接!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onConnectionFailed() {
                Toast.makeText(getApplicationContext(), "连接服务器遇到问题，请检查网络连接!", Toast.LENGTH_LONG).show();
            }
        });
        connectionManager.connect();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.colorPrimary);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }
}
