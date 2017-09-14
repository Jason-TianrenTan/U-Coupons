package com.example.administrator.ccoupons.AddCoupon;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.Main.LoginActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
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

public class AddCouponActivity extends AppCompatActivity {

    private Coupon coupon;
    private TextView couponEvalText, couponNameText, couponDiscountText, couponBrandText, couponCatText,
            couponConstraintsText;
    private EditText couponListPriceText;
    private ImageView couponImg;
    private TextView nextButton,
        useEvalButton;
    private ZLoadingDialog dialog;
    private String vid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon);
        bindViews();
        getCouponInfo();
        if (coupon != null)
            requestCouponValue();
    }


    //请求优惠券估值
    private void requestCouponValue() {
        String url = GlobalConfig.base_URL + GlobalConfig.postGetEvaluation_URL;
        HashMap<String, String> map = new HashMap<>();
        map.put("discount", coupon.getDiscount());
        dialog = new ZLoadingDialog(AddCouponActivity.this);
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
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (value != null) {
            System.out.println("coupon value = " + value);
            coupon.setValue(value);
        }
        couponEvalText.setText(coupon.getValue() + "");
        //TODO:如果一定要添加图片的话 请修改
        if (coupon.getPic() != null && !coupon.getPic().equals("")) {
            Glide.with(this)
                    .load(coupon.getPic())
                    .into(couponImg);
        }
        couponNameText.setText(coupon.getProduct());
        couponBrandText.setText(coupon.getBrandName());
        couponDiscountText.setText(coupon.getDiscount());
        couponCatText.setText(GlobalConfig.Categories.nameList[Integer.parseInt(coupon.getCategory()) - 1]);
        StringBuilder sb = new StringBuilder("");
        String[] constraints = coupon.getConstraints();
        for (int i = 0; i < constraints.length; i++) {
            sb.append((i + 1) + ". " + constraints[i] + "\n");
        }
        couponConstraintsText.setText(sb.toString());
    }


    private void getCouponInfo() {
        coupon = (Coupon) getIntent().getSerializableExtra("coupon");
    }


    private void requestAddCoupon() {
        HashMap<String, String> map = new HashMap<>();
        map.put("userID", ((MyApp) getApplicationContext()).getUserId());
        map.put("brand", coupon.getBrandName());
        map.put("category", coupon.getCategory());
        map.put("expiredTime", coupon.getExpiredtime());
        map.put("listPrice", couponListPriceText.getText().toString());
        map.put("product", coupon.getProduct());
        map.put("discount", coupon.getDiscount());
        map.put("value", vid);
        //    map.put("pic", coupon.getPic());
        new UpLoadCoupon(map, coupon.getConstraints(), coupon.getPic()).execute();
    }


    private void bindViews() {

        nextButton = (TextView) findViewById(R.id.form_preview_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (couponListPriceText.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "请输入价格!", Toast.LENGTH_SHORT).show();
                } else {
                    //请求添加优惠券
                    requestAddCoupon();
                }
            }
        });

        couponEvalText = (TextView) findViewById(R.id.textview_coupon_eval);
        couponListPriceText = (EditText) findViewById(R.id.edittext_coupon_listprice);
        couponImg = (ImageView) findViewById(R.id.purchase_coupon_img);
        couponNameText = (TextView) findViewById(R.id.purchase_coupon_name_text);
        couponDiscountText = (TextView) findViewById(R.id.coupon_discount_text);
        couponBrandText = (TextView) findViewById(R.id.coupon_brand_textview);
        couponCatText = (TextView) findViewById(R.id.coupon_cat_textview);
        couponConstraintsText = (TextView) findViewById(R.id.purchase_constraints_text);
        useEvalButton = (TextView) findViewById(R.id.use_eval_button);
        useEvalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = coupon.getValue();
                if (value != null && value.length() > 0)
                    couponListPriceText.setText(value);
            }
        });
    }


    public class UpLoadCoupon extends AsyncTask<Void, Integer, String> {

        private HashMap<String, String> map;
        private String[] list;
        private String filepath;
        String url = GlobalConfig.base_URL + GlobalConfig.postAddCoupon_URL;

        public UpLoadCoupon(HashMap<String, String> map, String[] constraintList, String url) {
            this.map = map;
            this.list = constraintList;
            this.filepath = url;
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
                    mpEntity.addPart(key, new StringBody(value, Charset.forName("UTF-8")));
                }
                for (int i = 0; i < list.length; i++) {
                    mpEntity.addPart("limit[]", new StringBody(list[i], Charset.forName("UTF-8")));
                }
                mpEntity.addPart("pic", cbFile);

                httppost.setEntity(mpEntity);
                HttpResponse response = httpclient.execute(httppost);
                int statusCode = response.getStatusLine().getStatusCode();

                String result = EntityUtils.toString(response.getEntity());
                System.out.println("RRRRRRREEEEEEEEESULT = " + result);
                httpclient.getConnectionManager().shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
