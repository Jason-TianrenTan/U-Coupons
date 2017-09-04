package com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.User.CouponCommonFragment;
import com.example.administrator.ccoupons.User.UserCouponInfoAdapter;
import com.example.administrator.ccoupons.User.UserCoupons.Seller.SellerSoldFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class CouponCommonActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private FrameLayout frameView;
    private UserCouponInfoAdapter adapter;

    private CouponCommonFragment commonFragment;
    private ArrayList<Coupon> couponList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int resId) {
        super.setContentView(resId);
        initViews();
    }

    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.common_coupons_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        frameView = (FrameLayout) findViewById(R.id.common_frame);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    public void initData(String url) {
        HashMap<String, String> map = new HashMap<>();
        MyApp app = (MyApp) getApplicationContext();
        map.put("userID", app.getUserId());
        ConnectionManager connectionManager = new ConnectionManager(url, map);
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
                Toast.makeText(getApplicationContext(), "连接服务器超时，请稍后重试", Toast.LENGTH_SHORT).show();
            }
        });
        connectionManager.connect();
    }


    private void parseMessage(String str) {
        System.out.println("Response = " + str);
        commonFragment = new SingleListFragment();
        couponList = new ArrayList<>();
        try {
            String parseString = null;
            String[] parseList = "boughtList,soldList,likeList".split(",");
            for (String pStr : parseList)
                if (str.indexOf(pStr) != -1)
                    parseString = pStr;
            parseJSONMessage((new JSONObject(str)).getJSONArray(parseString));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.common_frame, commonFragment);

            Bundle bundle = new Bundle();
            bundle.putSerializable("coupons", couponList);
            commonFragment.setArguments(bundle);
            fragmentTransaction.show(commonFragment);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void parseJSONMessage(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Coupon coupon = new Coupon();
                coupon.setCouponid(obj.getString("couponid"));
                coupon.setProduct(obj.getString("product"));
                coupon.setListprice(obj.getString("listprice"));
                coupon.setValue(obj.getString("value"));
                coupon.setExpiredtime(obj.getString("expiredtime"));
                coupon.setDiscount(obj.getString("discount"));
                coupon.setPic(obj.getString("pic"));
                couponList.add(coupon);
                System.out.println("parsing " + obj.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
