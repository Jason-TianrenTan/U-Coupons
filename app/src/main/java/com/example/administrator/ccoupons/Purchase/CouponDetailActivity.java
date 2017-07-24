package com.example.administrator.ccoupons.Purchase;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Double2;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ImageFetchr;
import com.example.administrator.ccoupons.Connections.RequestCouponDetailThread;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.ImageLruCache;
import com.example.administrator.ccoupons.Tools.MessageType;
import com.example.administrator.ccoupons.Tools.PixelUtils;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import org.w3c.dom.Text;

public class CouponDetailActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private Coupon coupon;
    private ImageView mImageView;
    private ObservableScrollView mScrollView;
    private Toolbar mToolbarView;
    private int mParallaxImageHeight;
    private TextView nameText, listpriceText, evalpriceText,
            discountText, brandNameText, expireText, constaintsText, sellerNameText;
    private RequestCouponDetailThread detailThread;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessageType.CONNECTION_ERROR:
                    Toast.makeText(getApplicationContext(), "连接服务器遇到问题，请检查网络连接!", Toast.LENGTH_LONG).show();
                    break;
                case MessageType.CONNECTION_TIMEOUT:
                    Toast.makeText(getApplicationContext(), "连接服务器超时，请检查网络连接!", Toast.LENGTH_LONG).show();
                    break;
                case MessageType.CONNECTION_SUCCESS:
                    coupon.getDetails(detailThread.getResponse());
                    System.out.println("Response = " + detailThread.getResponse());
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
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);
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


    private void showInfo() {
        coupon = (Coupon) getIntent().getSerializableExtra("Coupon");

        //url
        String url = DataHolder.base_URL + coupon.getImgURL();
        Bitmap bitmap = ImageLruCache.getInstance().getFromMemoryCache(url);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        mImageView.setImageDrawable(drawable);

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

        System.out.println("Coupon id  = " + coupon.getCouponId());
        detailThread = new RequestCouponDetailThread(DataHolder.base_URL + DataHolder.requestDetail_URL, coupon.getCouponId(), handler, this);
        detailThread.start();
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
