package com.example.administrator.ccoupons.AddCoupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AddCouponActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon);

        getCouponFromQR();
    }

    private Coupon getCouponFromQR() {
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        Coupon coupon = new Coupon();
        try {
            JSONObject mainObj = new JSONObject(result);
            coupon = new Coupon().decodeFromJSON(mainObj);
            coupon.getDetails(result);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(AddCouponActivity.this, "这不是优惠券的二维码，滚吧你", Toast.LENGTH_SHORT).show();
        }
        return coupon;
    }
}
