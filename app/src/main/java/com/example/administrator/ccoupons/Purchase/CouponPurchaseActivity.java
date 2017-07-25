package com.example.administrator.ccoupons.Purchase;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.ImageManager;

public class CouponPurchaseActivity extends AppCompatActivity {


    private ImageView couponImg;
    private TextView couponNameText, couponPriceText,couponConstraintsText, couponDiscountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_purchase);

        bindViews();
        Toolbar toolbar = (Toolbar)findViewById(R.id.coupon_purchase_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initInfo();
    }

    private void bindViews() {

        couponImg = (ImageView) findViewById(R.id.purchase_coupon_img);
        couponNameText = (TextView) findViewById(R.id.purchase_coupon_name_text);
        couponPriceText = (TextView) findViewById(R.id.purchase_price_text);
        couponConstraintsText = (TextView) findViewById(R.id.purchase_constraints_text);
        couponDiscountText = (TextView) findViewById(R.id.purchase_discount_text);

    }

    private void initInfo() {
        Coupon coupon = (Coupon) getIntent().getSerializableExtra("coupon");
        ImageManager.GlideImage(DataHolder.base_URL + coupon.getImgURL(), couponImg);
        couponNameText.setText(coupon.getName());
        couponPriceText.setText("¥" + coupon.getListPrice());

        String[] constraints = coupon.getConstraints();
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (String str : constraints)
            sb.append(index + ". " + str + '\n');
        couponConstraintsText.setText(sb.toString());
        couponDiscountText.setText("¥" + coupon.getDiscount());
    }



}
