package com.example.administrator.ccoupons.User;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.PixelUtils;
import com.example.administrator.ccoupons.User.UserCouponFragments.EmptyFragment;
import com.example.administrator.ccoupons.User.UserCouponFragments.NotOnSaleCouponFragment;
import com.example.administrator.ccoupons.User.UserCouponFragments.OnSaleCouponFragment;
import com.example.administrator.ccoupons.User.UserCouponFragments.UsedCouponFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class UserMyCouponActivity extends AppCompatActivity {


    boolean update = false;
    int index = 0;
    ArrayList<Coupon> usedList, onsaleList, notonsaleList;
    UsedCouponFragment usedFragment;
    OnSaleCouponFragment OnSaleFragment;
    NotOnSaleCouponFragment NotOnSaleFragment;
    Fragment fr1, fr2, fr3;
    TextView title_used, title_onsale, title_nonsale;
    int screen_width;
    LinearLayout scrollBar;
    UpdateUIReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_coupon);

        String iStr = getIntent().getStringExtra("index");
        if (iStr != null && iStr.length() > 0)
            index = Integer.parseInt(iStr);
        scrollBar = (LinearLayout) findViewById(R.id.mycoupon_scrollbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.user_mycoupons_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_used = (TextView) findViewById(R.id.used_title_text);
        title_onsale = (TextView) findViewById(R.id.onsale_title_text);
        title_nonsale = (TextView) findViewById(R.id.nonsale_title_text);

        fr1 = new EmptyFragment();
        fr2 = new EmptyFragment();
        fr3 = new EmptyFragment();
        notonsaleList = new ArrayList<>();
        usedList = new ArrayList<>();
        onsaleList = new ArrayList<>();
        requestCoupons();

        initSlidingBar();

        selectPage(index);

        initReceiver();

        title_used.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        title_onsale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        title_nonsale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
    }

    private void initSlidingBar() {
        WindowManager wm = getWindowManager();
        screen_width = wm.getDefaultDisplay().getWidth();
    }

    private void slideTo(int pos) {
        int preWidth = screen_width / 3;
        int margins = PixelUtils.dp2px(this, 12);
        int startX = pos * preWidth + margins;
        FrameLayout.LayoutParams scrollParams = (FrameLayout.LayoutParams) scrollBar.getLayoutParams();
        scrollParams.leftMargin = startX;
        scrollParams.width = preWidth - 2 * margins;
        scrollBar.setLayoutParams(scrollParams);
    }

    private void initData() {
        usedFragment = new UsedCouponFragment();
        OnSaleFragment = new OnSaleCouponFragment();
        NotOnSaleFragment = new NotOnSaleCouponFragment();
        System.out.println(usedList.size() + "," + onsaleList.size() + "," + notonsaleList.size() + ", update = " + update);
        //TODO:init coupons
        if (usedList.size() > 0) {
            if (!update) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("coupons", usedList);
                usedFragment.setArguments(bundle);

            }

            fr1 = usedFragment;
        }
        if (onsaleList.size() > 0) {
            if (!update) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("coupons", onsaleList);
                OnSaleFragment.setArguments(bundle);

            }

            fr2 = OnSaleFragment;
        }
        if (notonsaleList.size() > 0) {
            if (!update){
                Bundle bundle = new Bundle();
                bundle.putSerializable("coupons", notonsaleList);
                NotOnSaleFragment.setArguments(bundle);
            }

            fr3 = NotOnSaleFragment;
        }

        initTabs();

    }

    private void selectPage(int position) {
        System.out.println("page selected = " + position + ", index=  " + index);
        slideTo(position);
        title_used.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        title_onsale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        title_nonsale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        switch (position) {
            case 0:
                title_used.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                break;
            case 1:
                title_onsale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                break;
            case 2:
                title_nonsale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                break;
        }
    }

    private void initTabs() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArrayList<Fragment> frList = new ArrayList<Fragment>();
        frList.add(fr1);
        frList.add(fr2);
        frList.add(fr3);
        MyCouponFragmentAdapter frAdapter = new MyCouponFragmentAdapter(fragmentManager, frList);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.mycoupon_viewpager);
        viewPager.setAdapter(frAdapter);
        viewPager.setCurrentItem(index);

        title_used.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        title_used.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPage(0);
                viewPager.setCurrentItem(0);
            }
        });

        title_onsale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPage(1);
                viewPager.setCurrentItem(1);
            }
        });

        title_nonsale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPage(2);
                viewPager.setCurrentItem(2);
            }
        });

        selectPage(index);


    }

    //请求获取优惠券
    private void requestCoupons() {
        String url = DataHolder.base_URL + DataHolder.requestOwnList_URL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userID", ((MyApp) getApplicationContext()).getUserId());
        ConnectionManager connectionManager = new ConnectionManager(url, map);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
                parseMessage(response);
            }

            @Override
            public void onConnectionTimeOut() {

            }

            @Override
            public void onConnectionFailed() {

            }
        });
        connectionManager.connect();
    }

    /*
     {"onSaleList": [{"couponid": "003", "product": "\u9ea6\u8fa3\u9e21\u7fc5wh", "listprice": "1", "value": "1", "expiredtime": "2017-01-01", "discount": "\u6ee150\u51cf20"},
     {"couponid": "004", "product": "\u9e21\u8089\u5377wh", "listprice": "1", "value": "1", "expiredtime": "2017-01-01", "discount": "\u6ee120\u51cf5"},
     {"couponid": "005", "product": "\u677f\u70e7\u9e21\u817f\u5821wh", "listprice": "1", "value": "1", "expiredtime": "2017-01-01", "discount": "\u6ee110\u51cf10"}],

     "storeList": [{"couponid": "001", "product": "\u542e\u6307\u539f\u5473\u9e21wh", "listprice": "1", "value": "1", "expiredtime": "2017-01-01", "discount": "20"}],

     "usedList": []}
     */
    private void parseMessage(String response) {
        try {
            JSONObject mainObj = new JSONObject(response);
            JSONArray onSaleArray = mainObj.getJSONArray("onSaleList"),
                    storeArray = mainObj.getJSONArray("storeList"),
                    usedArrray = mainObj.getJSONArray("usedList");
            for (int i = 0; i < onSaleArray.length(); i++) {
                JSONObject obj = onSaleArray.getJSONObject(i);
                Coupon coupon = Coupon.decodeFromJSON(obj);

                onsaleList.add(coupon);
            }
            for (int i = 0; i < storeArray.length(); i++) {
                JSONObject obj = storeArray.getJSONObject(i);
                Coupon coupon = Coupon.decodeFromJSON(obj);
                notonsaleList.add(coupon);
            }
            for (int i = 0; i < usedArrray.length(); i++) {
                JSONObject obj = usedArrray.getJSONObject(i);
                Coupon coupon = Coupon.decodeFromJSON(obj);
                usedList.add(coupon);
            }
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initReceiver() {
        IntentFilter filter = new IntentFilter("com.example.administrator.ccoupons.UPDATEVIEWS");
        receiver = new UpdateUIReceiver();
        registerReceiver(receiver, filter);
    }

    public class UpdateUIReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


}
