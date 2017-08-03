package com.example.administrator.ccoupons.Purchase;

import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.ImageManager;
import com.example.administrator.ccoupons.Tools.PixelUtils;
import com.example.administrator.ccoupons.User.UserMyCouponActivity;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import org.json.JSONObject;

import java.util.HashMap;

public class CouponDetailActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {


    private String msgId = null;
    private boolean statType = false;
    private boolean sigmaType = false;
    private int index = 0;
    private Coupon coupon;
    private ImageView mImageView;
    private ImageView mainButton, followButton, purchaseButton;
    private LinearLayout toSellerButton;

    private ObservableScrollView mScrollView;
    private Toolbar mToolbarView;
    private int mParallaxImageHeight;
    private TextView nameText, listpriceText, evalpriceText,
            discountText, brandNameText, expireText, constaintsText, sellerNameText;
    private ImageView sellerAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);


        String iStr = getIntent().getStringExtra("index");
        if (iStr != null && iStr.length() > 0)
            index = Integer.parseInt(getIntent().getStringExtra("index"));

        String type = getIntent().getStringExtra("type");
        if (type.equals("purchase"))
            inflateBottomPurchaseView();//初始化底部栏样式
        if (type.equals("show")) {
            statType = true;
            inflateBottomStatView();
        }
        if (type.equals("message")) {
            //TODO:
            String catStr = getIntent().getStringExtra("msgCat");
            msgId = getIntent().getStringExtra("msgId");
            if (catStr.equals("1") || catStr.equals("3"))
                inflateBottomPurchaseView();
            else
                inflateBottomStatView();
        }
        if (type.equals("seller")) {
            sigmaType = true;
            inflateBottomPurchaseView();
        }
        bindViews();

        setSupportActionBar(mToolbarView);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pullback();

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


    private void pullback() {
        System.out.println("statType = " + statType);
        if (statType) {
            Intent intent = new Intent(CouponDetailActivity.this, UserMyCouponActivity.class);
            intent.putExtra("index", index + "");
            startActivity(intent);
        }
        if (sigmaType) {
            Intent intent = new Intent(CouponDetailActivity.this, SellerDetailActivity.class);
            intent.putExtra("index", index + "");
            startActivity(intent);
        }

        finish();
    }

    private void inflateBottomPurchaseView() {

        RelativeLayout rootView = (RelativeLayout) findViewById(R.id.detail_rootview);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.purchase_bottom_bar, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PixelUtils.dp2px(this, 45));
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        view.setLayoutParams(params);
        rootView.addView(view);


        mainButton = (ImageView) view.findViewById(R.id.page_button_mainpage);
        followButton = (ImageView) view.findViewById(R.id.page_button_follow);
        purchaseButton = (ImageView) view.findViewById(R.id.page_button_purchase);

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
                //关注
                HashMap<String, String> map = new HashMap<String, String>();
                MyApp app = (MyApp) getApplicationContext();
                map.put("couponID", coupon.getCouponId());
                map.put("userID", app.getUserId());
                ConnectionManager connectionManager = new ConnectionManager(DataHolder.base_URL + DataHolder.postFollow_URL, map);
                connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
                    @Override
                    public void onConnectionSuccess(String response) {
                        System.out.println("Response = " + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String alreadyLike = obj.getString("result");
                            if (alreadyLike.equals("dislike")) {
                                Toast.makeText(getApplicationContext(), "取消关注", Toast.LENGTH_SHORT).show();
                                followButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.follow));
                            } else {
                                Toast.makeText(getApplicationContext(), "关注成功", Toast.LENGTH_SHORT).show();
                                followButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.follow_pressed));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onConnectionTimeOut() {
                        Toast.makeText(getApplicationContext(), "连接服务器超时，请稍后重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onConnectionFailed() {
                        Toast.makeText(getApplicationContext(), "连接服务器超时，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                });
                connectionManager.connect();
            }
        });

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = coupon.getCouponId();
                Intent intent = new Intent(CouponDetailActivity.this, CouponPurchaseActivity.class);
                intent.putExtra("coupon", coupon);
                startActivity(intent);
            }
        });

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
        map.put("couponID", coupon.getCouponId());
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
        sellerNameText = (TextView) findViewById(R.id.coupon_detail_seller_name);
        sellerAvatar = (ImageView) findViewById(R.id.seller_avatar_img);
        toSellerButton = (LinearLayout) findViewById(R.id.to_seller_button);


        //进入到商家信息界面
        toSellerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CouponDetailActivity.this, SellerDetailActivity.class);
                intent.putExtra("nickname", coupon.getSellerNickname());
                intent.putExtra("avatar", coupon.getSellerAvatarURL());
                intent.putExtra("id", coupon.getSellerId());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            pullback();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void showInfo() {
        coupon = (Coupon) getIntent().getSerializableExtra("Coupon");

        //url
        String url = DataHolder.base_URL + "/static/" + coupon.getImgURL();
        ImageManager.GlideImage(url, mImageView);

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
        String discount = coupon.getDiscount();
        discountText.setText("¥" + discount);

        //商家名
        String brandName = "懒得起名字的公司" + coupon.getBrandName();
        brandNameText.setText(brandName);

        String expireDate = coupon.getExpireDate();
        expireText.setText(expireDate + "");


        String constraints = "后台居然懒到没加这个=.=";
        constaintsText.setText(constraints);

        HashMap<String, String> map = new HashMap<>();
        map.put("couponID", coupon.getCouponId() + "");
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
                //卖家
                sellerNameText.setText(coupon.getSellerNickname());
                //    ImageManager.GlideImage(DataHolder.base_URL + "/static/" + coupon.getSellerAvatarURL(), sellerAvatar);
                //商家 品牌
                brandNameText.setText(coupon.getBrandName());

                //使用限制
                String[] constraints = coupon.getConstraints();
                StringBuilder sb = new StringBuilder();
                int index = 1;
                for (String str : constraints)
                    sb.append(index++ + ". " + str + '\n');
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
