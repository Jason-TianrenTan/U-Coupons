package com.example.administrator.ccoupons.User.CouponDetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseDetailActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {



    @BindView(R.id.coupon_image)
    ImageView mImageView;
    @BindView(R.id.coupon_detail_name_text)
    TextView nameText;
    @BindView(R.id.coupon_detail_list_price)
    TextView listpriceText;
    @BindView(R.id.coupon_detail_evaluate_price)
    TextView evalpriceText;
    @BindView(R.id.coupon_detail_discount_price)
    TextView discountText;
    @BindView(R.id.coupon_detail_brand_name)
    TextView brandNameText;
    @BindView(R.id.coupon_detail_seller_name)
    TextView sellerNameText;
    @BindView(R.id.seller_avatar_img)
    ImageView sellerAvatar;
    @BindView(R.id.to_seller_button)
    LinearLayout toSellerButton;
    @BindView(R.id.coupon_detail_expire_date)
    TextView expireText;
    @BindView(R.id.coupon_detail_constraints_text)
    TextView constaintsText;
    @BindView(R.id.scroll)
    ObservableScrollView mScrollView;
    @BindView(R.id.fading_toolbar)
    Toolbar mToolbarView;
    @BindView(R.id.detail_rootview)
    RelativeLayout detailRootview;
    @OnClick(R.id.to_seller_button)
    public void click() {
        Intent intent = new Intent(BaseDetailActivity.this, SellerDetailActivity.class);
        intent.putExtra("nickname", coupon.getSellerNickname());
        intent.putExtra("avatar", coupon.getSellerAvatarURL());
        intent.putExtra("id", coupon.getSellerId());
        startActivity(intent);
    }
    protected int mParallaxImageHeight;
    protected String msgId = null;
    protected Coupon coupon;
    protected View bottomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);
        ButterKnife.bind(this);

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

    /**
     * inflate bottom view
     * TYPES:
     * Purchase Coupon -> buy like
     * My Coupon -> store sell
     * Coupon from Message -> nothing
     */
    public void inflateBottomView(int id) {
        RelativeLayout rootView = (RelativeLayout) findViewById(R.id.detail_rootview);
        LayoutInflater inflater = LayoutInflater.from(this);
        bottomView = inflater.inflate(id, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PixelUtils.dp2px(this, 45));
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bottomView.setLayoutParams(params);
        rootView.addView(bottomView);
    }


    public abstract void initBottomViews();

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
                //卖家
                sellerNameText.setText(coupon.getSellerNickname());
                //    ImageManager.GlideImage(DataHolder.base_URL + "/static/" + coupon.getSellerAvatarURL(), sellerAvatar);
                //商家 品牌
                brandNameText.setText(coupon.getBrandName());

                //使用限制
                String[] constraints = coupon.getConstraints();
                StringBuilder sb = new StringBuilder();
                int index = 1;
                for (String str : constraints) {
                    sb.append(index + ". " + str + '\n');
                    index++;
                }
                constaintsText.setText(sb.toString());
                initBottomViews();
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
