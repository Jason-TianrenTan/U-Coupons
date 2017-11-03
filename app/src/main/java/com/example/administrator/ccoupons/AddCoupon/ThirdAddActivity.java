package com.example.administrator.ccoupons.AddCoupon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.ccoupons.R;

import butterknife.OnClick;

public class ThirdAddActivity extends AppCompatActivity {

    @OnClick(R.id.tv_addcoupon_back)
    public void click() {
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_add_view);
    }
}
