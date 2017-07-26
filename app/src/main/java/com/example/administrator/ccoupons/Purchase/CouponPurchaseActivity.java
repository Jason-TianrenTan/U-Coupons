package com.example.administrator.ccoupons.Purchase;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.PurchaseThread;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.Main.LoginActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.ImageManager;
import com.example.administrator.ccoupons.Tools.MessageType;

import org.json.JSONObject;

public class CouponPurchaseActivity extends AppCompatActivity {


    private Coupon coupon;
    private ImageView couponImg;
    private TextView couponNameText, couponPriceText,couponConstraintsText, couponDiscountText;
    private Button purchaseButton;
    private PurchaseThread thread;
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
                    parseMessage(thread.getResponse());
                    break;
            }
        }
    };
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
                thread = new PurchaseThread(DataHolder.base_URL + DataHolder.purchase_URL, coupon.getCouponId(), getApplicationContext(), handler);
                thread.start();
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
