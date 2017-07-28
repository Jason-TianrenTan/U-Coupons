package com.example.administrator.ccoupons.User;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.LocationSelectActivity;
import com.example.administrator.ccoupons.Fragments.MyMessageActivity;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Search.SearchActivity;
import com.example.administrator.ccoupons.User.UserCouponFragments.EmptyFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class CouponCommonActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FrameLayout frameView;
    private UserCouponInfoAdapter adapter;

    private CouponCommonFragment commonFragment;
    private CommonEmptyFragment emptyFragment;
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
                parseMessgae(response);
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

    //{"broughtStoreList": [], "broughtUsedList": []} 买到的
/*
    {"likeList": [{"couponid": "001", "product": "\u542e\u6307\u539f\u5473\u9e21wh", "listprice": "1", "value": "1",
    "expiredtime": "2017-01-01", "discount": "20"}
     */
    private void parseMessgae(String str) {
        commonFragment = new CouponCommonFragment();
        emptyFragment = new CommonEmptyFragment();
        couponList = new ArrayList<>();
        try {
            System.out.println("Response = " + str);
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = null;
            if (str.indexOf("boughtList") != -1) {

                jsonArray = jsonObject.getJSONArray("boughtList");
            }
            if (str.indexOf("soldList") != -1) {
                jsonArray = jsonObject.getJSONArray("soldList");
            }
            if (str.indexOf("likeList") != -1) {
                jsonArray = jsonObject.getJSONArray("likeList");
            }

            parseJSONMessage(jsonArray);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.common_frame, commonFragment);
            fragmentTransaction.add(R.id.common_frame, emptyFragment);
            if (couponList.size() > 0) {
                fragmentTransaction.hide(emptyFragment);
                fragmentTransaction.show(commonFragment);
                Bundle bundle = new Bundle();
                bundle.putSerializable("coupons", couponList);
                commonFragment.setArguments(bundle);
                //    commonFragment.setData(couponList);
            } else {
                fragmentTransaction.show(emptyFragment);
                fragmentTransaction.hide(commonFragment);
            }
            fragmentTransaction.commit();
            System.out.println("commit()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void parseJSONMessage(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Coupon coupon = new Coupon();
                coupon.setCouponId(obj.getString("couponid"));
                coupon.setName(obj.getString("product"));
                coupon.setListPrice(Double.parseDouble(obj.getString("listprice")));
                coupon.setEvaluatePrice(Double.parseDouble(obj.getString("value")));
                coupon.setExpireDate(obj.getString("expiredtime"));
                coupon.setDiscount(obj.getString("discount"));
                couponList.add(coupon);
                System.out.println("parsing " + obj.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
