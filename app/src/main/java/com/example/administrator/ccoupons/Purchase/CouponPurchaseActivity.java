package com.example.administrator.ccoupons.Purchase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.ImageManager;

import org.json.JSONObject;

import java.util.HashMap;

public class CouponPurchaseActivity extends AppCompatActivity {


    private Coupon coupon;
    private ImageView couponImg;
    private TextView couponNameText, couponPriceText,couponConstraintsText, couponDiscountText;
    private Button purchaseButton;
    //处理返回回来的json
    private void parseMessage(String response) {
        if (response.indexOf("errno") >= 0) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                int errNo = Integer.parseInt(jsonObject.getString("errno"));
                if (errNo == 0) {
                    //支付成功
                    Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();

                } else {
                    String errMessage = jsonObject.getString("message");
                    Toast.makeText(getApplicationContext(), errMessage, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "遇到未知错误，请稍后重试", Toast.LENGTH_LONG).show();
            }
        }
        else
            Toast.makeText(getApplicationContext(), "遇到未知错误，请稍后重试", Toast.LENGTH_LONG).show();
    }

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
        purchaseButton = (Button) findViewById(R.id.purchase_button);
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("couponID", coupon.getCouponId());
                MyApp app = (MyApp)getApplicationContext();
                String userId = app.getUserId();
                map.put("userID", userId);
                ConnectionManager connectionManager = new ConnectionManager(DataHolder.base_URL + DataHolder.purchase_URL, map);
                connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
                    @Override
                    public void onConnectionSuccess(String response) {
                        parseMessage(response);
                    }

                    @Override
                    public void onConnectionTimeOut() {
                        Toast.makeText(getApplicationContext(), "连接服务器超时，请稍后重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onConnectionFailed() {
                        Toast.makeText(getApplicationContext(), "连接服务器遇到问题，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                });
                connectionManager.connect();
            }
        });
    }

    private void initInfo() {
        coupon = (Coupon) getIntent().getSerializableExtra("coupon");
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
