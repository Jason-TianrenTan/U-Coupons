package com.example.administrator.ccoupons.AddCoupon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.User.UserMyCouponActivity;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThirdAddActivity extends AppCompatActivity {


    private String category, productName, brandName, picPath, expireDate, vid, discount;
    private String[] constraintList;
    private Coupon coupon;
    private ZLoadingDialog dialog;

    private static final int TO_MYCOUPON = 1,
            TO_ONSALE = 2;

    @OnClick(R.id.use_eval_button)
    public void onClick1() {
        listPriceEditText.setText("100");
    }

    @OnClick(R.id.btn_add2mycoupon)
    public void onClick2() {
        String price = listPriceEditText.getText().toString();
        if (price.length() > 0) {
            coupon.setListprice(price);
            requestAddCoupon(TO_MYCOUPON);
        } else
            Toast.makeText(this, "请输入估值", Toast.LENGTH_SHORT);
    }

    @OnClick(R.id.btn_set2onsale)
    public void onClick3() {
        String price = listPriceEditText.getText().toString();
        if (price.length() > 0) {
            coupon.setListprice(price);
            requestAddCoupon(TO_ONSALE);
        } else
            Toast.makeText(this, "请输入估值", Toast.LENGTH_SHORT);
    }

    @OnClick(R.id.tv_addcoupon_back)
    public void click() {
        finish();
    }

    @BindView(R.id.et_add_coupon_listprice)
    EditText listPriceEditText;
    @BindView(R.id.tv_add_coupon_eval)
    TextView evalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_add_view);
        ButterKnife.bind(this);

        initCoupon();
    }


    private void initCoupon() {
        coupon = new Coupon();
        Intent intent = this.getIntent();
        constraintList = intent.getStringArrayExtra("constraints");
        category = intent.getStringExtra("category");
        productName = intent.getStringExtra("product");
        brandName = intent.getStringExtra("brand");
        expireDate = intent.getStringExtra("expire");
        picPath = intent.getStringExtra("picture");
        discount = intent.getStringExtra("discount");
        coupon.setConstraints(constraintList);
        coupon.setCategory(category);
        coupon.setProduct(productName);
        coupon.setBrandName(brandName);
        coupon.setExpiredtime(expireDate);
        coupon.setPic(picPath);
        coupon.setDiscount(discount);

        requestCouponValue();
    }


    //request evaluation for coupon
    private void requestCouponValue() {
        String url = GlobalConfig.base_URL + GlobalConfig.postGetEvaluation_URL;
        HashMap<String, String> map = new HashMap<>();
        map.put("discount", coupon.getDiscount());
        dialog = new ZLoadingDialog(ThirdAddActivity.this);
        dialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)
                .setLoadingColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCanceledOnTouchOutside(false)
                .setHintText("正在获取优惠券估值...")
                .setHintTextSize(16)
                .show();
        ConnectionManager connectionManager = new ConnectionManager(url, map, dialog);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
                parseMessage(response);
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


    //parse return message
    private void parseMessage(String response) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 800);
        System.out.println("Response = " + response);
        String value = null;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            JSONObject valueObj = jsonArray.getJSONObject(0);
            value = valueObj.getString("value");
            vid = valueObj.getString("vid");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (value != null) {
            System.out.println("coupon value = " + value);
            coupon.setValue(value);
        }
        evalTextView.setText(coupon.getValue() + "");
    }


    /**
     * @param type TO_MYCOUPON: add to "my coupons" directly
     *             TO_ONSALE: add to "my onsale list" directly
     */
    private void requestAddCoupon(int type) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userID", ((MyApp) getApplicationContext()).getUserId());
        map.put("brand", coupon.getBrandName());
        map.put("category", coupon.getCategory());
        map.put("expiredTime", coupon.getExpiredtime());
        map.put("listPrice", coupon.getListprice());
        map.put("product", coupon.getProduct());
        map.put("discount", coupon.getDiscount());
        map.put("value", vid);
        //    map.put("pic", coupon.getPic());
        new UpLoadCoupon(map, coupon.getConstraints(), coupon.getPic(), type).execute();
    }


    public class UpLoadCoupon extends AsyncTask<Void, Integer, String> {

        private HashMap<String, String> map;
        private String[] list;
        private String filepath;
        private int type;
        String url = GlobalConfig.base_URL + GlobalConfig.postAddCoupon_URL;
        String[] statStr = {"store", "onsale"};


        /**
         * @param map            coupon attr
         * @param constraintList constraints
         * @param url            target url
         * @param type           type
         */
        public UpLoadCoupon(HashMap<String, String> map, String[] constraintList, String url, int type) {
            this.map = map;
            this.list = constraintList;
            this.filepath = url;
            this.type = type;
        }

        @Override
        protected String doInBackground(Void... params) {
            uploadFile(filepath);
            return null;
        }

        protected void onPostExecute(String result) {

        }

        protected void onProgressUpdate(Integer... progress) {

        }

        // upload photos
        public void uploadFile(String uploadFile) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);/*
                httppost.setHeader(HTTP.CONTENT_TYPE,
                        "application/x-www-form-urlencoded;charset=UTF-8");*/
                MultipartEntity mpEntity = new MultipartEntity();
                File file = new File(uploadFile);
                ContentBody cbFile = new FileBody(file);

                for (HashMap.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey(),
                            value = entry.getValue();
                    System.out.println("key = " + key + ", entry = " + entry);
                    mpEntity.addPart(key, new StringBody(value, Charset.forName("UTF-8")));
                }
                for (int i = 0; i < list.length; i++) {
                    mpEntity.addPart("limit[]", new StringBody(list[i], Charset.forName("UTF-8")));
                }
                mpEntity.addPart("stat", new StringBody(statStr[type - 1], Charset.forName("UTF-8")));
                mpEntity.addPart("pic", cbFile);

                httppost.setEntity(mpEntity);
                HttpResponse response = httpclient.execute(httppost);
                int statusCode = response.getStatusLine().getStatusCode();

                String result = EntityUtils.toString(response.getEntity());
                if (result.contains("200")) {
                    Message msg = new Message();
                    msg.what = START_ACTIVITY;
                    handler.sendMessage(msg);
                }
                httpclient.getConnectionManager().shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static final int START_ACTIVITY = -190;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START_ACTIVITY:
                    Toast.makeText(getApplicationContext(), "添加优惠券成功!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ThirdAddActivity.this, UserMyCouponActivity.class);
                    intent.putExtra("index", "2");
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
}
