package com.example.administrator.ccoupons.Purchase;

<<<<<<< HEAD
import android.speech.tts.TextToSpeech;
=======
import android.content.Context;
import android.os.Bundle;
>>>>>>> Czj
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

<<<<<<< HEAD
import com.example.administrator.ccoupons.Data.DataHolder;
=======
import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.GlobalConfig;
>>>>>>> ttr
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;

<<<<<<< HEAD
public class CouponPurchaseActivity extends AppCompatActivity {


    private ImageView couponImg;
    private TextView couponNameText, couponPriceText,couponConstraintsText, couponDiscountText;
=======
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CouponPurchaseActivity extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @BindView(R.id.coupon_purchase_toolbar)
    Toolbar toolbar;
    @BindView(R.id.purchase_coupon_img)
    ImageView couponImg;
    @BindView(R.id.purchase_coupon_name_text)
    TextView couponNameText;
    @BindView(R.id.purchase_discount_text)
    TextView couponDiscountText;
    @BindView(R.id.purchase_constraints_text)
    TextView couponConstraintsText;
    @BindView(R.id.purchase_price_text)
    TextView couponPriceText;

    private Coupon coupon;

    @OnClick(R.id.purchase_button)
    public void onClick() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("couponID", coupon.getCouponid());
        MyApp app = (MyApp) getApplicationContext();
        String userId = app.getUserId();
        map.put("userID", userId);
        ConnectionManager connectionManager = new ConnectionManager(GlobalConfig.base_URL + GlobalConfig.purchase_URL, map);
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


    /**
     * parse response message
     * @param response response string from server
     */
    private void parseMessage(String response) {
        System.out.println("Response for purchase = " + response);
        if (response.contains("error")) { //error while purchase
            try {
                JSONObject jsonObject = new JSONObject(response);
                int errNo = Integer.parseInt(jsonObject.getString("error"));
                if (errNo == 107)
                    Toast.makeText(getApplicationContext(), "UCoin余额不足!", Toast.LENGTH_SHORT).show();
                    //后续加入Dialog
                else
                    Toast.makeText(getApplicationContext(), "遇到未知错误，请稍后重试", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "遇到未知错误，请稍后重试", Toast.LENGTH_LONG).show();
            }
        } else if (response.contains("result"))
            Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
        //页面跳转
    }
>>>>>>> ttr


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_purchase);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.coupon_purchase_toolbar);
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

<<<<<<< HEAD
    private void bindViews() {

        couponImg = (ImageView) findViewById(R.id.purchase_coupon_img);
        couponNameText = (TextView) findViewById(R.id.purchase_coupon_name_text);
        couponPriceText = (TextView) findViewById(R.id.purchase_price_text);
        couponConstraintsText = (TextView) findViewById(R.id.purchase_constraints_text);
        couponDiscountText = (TextView) findViewById(R.id.purchase_discount_text);

    }
=======
>>>>>>> ttr

    /**
     * Initialize information
     */
    private void initInfo() {
<<<<<<< HEAD
        Coupon coupon = (Coupon) getIntent().getSerializableExtra("coupon");
        ImageManager.GlideImage(DataHolder.base_URL + coupon.getImgURL(), couponImg);
        couponNameText.setText(coupon.getName());
        couponPriceText.setText("¥" + coupon.getListPrice());
=======
        coupon = (Coupon) getIntent().getSerializableExtra("coupon");
        Glide.with(this)
                .load(GlobalConfig.base_URL + "/static/" + coupon.getPic())
                .into(couponImg);
        couponNameText.setText(coupon.getProduct());
<<<<<<< HEAD
        couponPriceText.setText("¥" + coupon.getListprice());
>>>>>>> ttr
=======
        couponPriceText.setText(coupon.getListprice() + "U");
>>>>>>> Czj

        String[] constraints = coupon.getConstraints();
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (String str : constraints)
            sb.append(index + ". " + str + '\n');
        couponConstraintsText.setText(sb.toString());
        couponDiscountText.setText("¥" + coupon.getDiscount());
    }
}
