package com.example.administrator.ccoupons.Purchase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;

public class CouponDetailActivity extends AppCompatActivity {

    Coupon coupon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);

        initViews();
        showInfo();
    }

    private void initViews() {
        ImageView backButton = (ImageView)findViewById(R.id.coupon_detail_back_button);
        backButton.getBackground().setAlpha(50);
    }

    private void showInfo() {

    }
}
