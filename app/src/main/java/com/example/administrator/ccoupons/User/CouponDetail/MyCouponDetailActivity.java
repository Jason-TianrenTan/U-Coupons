package com.example.administrator.ccoupons.User.CouponDetail;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.PixelUtils;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import org.json.JSONObject;

import java.util.HashMap;

public class MyCouponDetailActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {


    private String msgId = null;
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
        setContentView(R.layout.activity_my_coupon_detail);


        String iStr = getIntent().getStringExtra("index");
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


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screen_width = displayMetrics.widthPixels;
        mParallaxImageHeight = PixelUtils.px2dp(this, screen_width);
        showInfo();
    }



    private void inflateBottomStatView() {
        ImageView saleButton, storeButton;
        RelativeLayout rootView = (RelativeLayout) findViewById(R.id.detail_rootview);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.stat_bottom_bar, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PixelUtils.dp2px(this, 45));
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        view.setLayoutParams(params);
        rootView.addView(view);


        saleButton = (ImageView) view.findViewById(R.id.page_button_sale);
        storeButton = (ImageView) view.findViewById(R.id.page_button_store);

        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendStatRequest("onSale");
            }
        });

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendStatRequest("store");
            }
        });

    }


    private void parseMessage(String response) {

        try {
            JSONObject obj = new JSONObject(response);
            String result = obj.getString("errno");
            int code = Integer.parseInt(result);
            if (code == 0) {
                /*
                Intent intent = new Intent("com.example.administrator.ccoupons.UPDATEVIEWS");
                sendBroadcast(intent);*/

                Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "已经上架/下架！", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendStatRequest(String type) {
        String url = DataHolder.base_URL + DataHolder.postChangeCouponState_URL;
        HashMap<String, String> map = new HashMap<>();
        map.put("couponID", coupon.getCouponid());
        map.put("state", type);
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
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void showInfo() {
        coupon = (Coupon) getIntent().getSerializableExtra("Coupon");

        //url
        String url = DataHolder.base_URL + "/static/" + coupon.getPic();
        Glide.with(this)
                .load(url)
                .into(mImageView);

        //name
        String name = coupon.getProduct();
        nameText.setText(name);

        //list price
        String listprice = coupon.getListprice();
        listpriceText.setText("¥" + listprice + "");

        //eval price
        String evalprice = coupon.getValue();
        evalpriceText.setText("¥" + evalprice + "");

        //优惠额度
        String discount = coupon.getDiscount();
        discountText.setText("¥" + discount);

        //商家名
        String brandName = "懒得起名字的公司" + coupon.getBrandName();
        brandNameText.setText(brandName);

        String expireDate = coupon.getExpiredtime();
        expireText.setText(expireDate + "");


        String constraints = "后台居然懒到没加这个=.=";
        constaintsText.setText(constraints);

        HashMap<String, String> map = new HashMap<>();
        map.put("couponID", coupon.getCouponid() + "");
        map.put("userID", ((MyApp) getApplicationContext()).getUserId());

        if (msgId != null) {
            map.put("messageID", msgId);
        }

        ConnectionManager connectionManager = new ConnectionManager(DataHolder.base_URL + DataHolder.requestDetail_URL, map);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {

            @Override
            public void onConnectionSuccess(String response) {
                coupon.getDetails(response);
                System.out.println("Response = " + response);
                //    ImageManager.GlideImage(DataHolder.base_URL + "/static/" + coupon.getSellerAvatarURL(), sellerAvatar);
                //商家 品牌
                brandNameText.setText(coupon.getBrandName());

                //使用限制
                String[] constraints = coupon.getConstraints();
                StringBuilder sb = new StringBuilder();
                int index = 1;
                for (String str : constraints)
                    sb.append(index + ". " + str + '\n');
                constaintsText.setText(sb.toString());

                //关注
                if (coupon.isLiked() && followButton != null)
                    followButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.follow_pressed));
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
