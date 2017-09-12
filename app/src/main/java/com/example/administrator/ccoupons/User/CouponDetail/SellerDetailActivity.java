package com.example.administrator.ccoupons.User.CouponDetail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.PixelUtils;
import com.example.administrator.ccoupons.User.CouponCommonFragment;
import com.example.administrator.ccoupons.User.UserCoupons.Seller.SellerOnsaleFragment;
import com.example.administrator.ccoupons.User.UserCoupons.Seller.SellerSoldFragment;
import com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList.MyCouponFragmentAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SellerDetailActivity extends AppCompatActivity{
    @BindView(R.id.seller_name_text)
    TextView sellerNameText;
    @BindView(R.id.seller_avatar_imageview)
    CircleImageView sellerAvatar;
    @BindView(R.id.seller_onsale_text)
    TextView onsaleText;
    @BindView(R.id.seller_sold_text)
    TextView soldText;
    @BindView(R.id.seller_coupon_scrollbar)
    LinearLayout scrollBar;

    private CouponCommonFragment onsaleFragment = new SellerOnsaleFragment(),
            soldFragment = new SellerSoldFragment();

    private ArrayList<Coupon> onsaleList, soldList;

    private int screen_width;

    private int index = 0;

    private UpdateUIReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_detail);
        ButterKnife.bind(this);

        initViews();
        initSlidingBar();

        selectPage(index);

        initReceiver();

        String iStr = getIntent().getStringExtra("index");
        if (iStr != null)
            index = Integer.parseInt(iStr);
        requestData();
    }


    private void initViews() {

        String url = GlobalConfig.base_URL + "/static/" + getIntent().getStringExtra("avatar");
        System.out.println("url=" + url);
        String name = getIntent().getStringExtra("nickname");
        sellerNameText.setText(name);
        sellerAvatar.setImageResource(R.drawable.testportrait);
        if (url != "") {
            Glide.with(this)
                    .load(url)
                    .into(sellerAvatar);
        }
        onsaleList = new ArrayList<>();
        soldList = new ArrayList<>();
    }

    //解析商家列表
    private void parseMessage(String response) {
        try {
            JSONObject mainObj = new JSONObject(response);

            //on sale
            JSONArray onsale_Arr = mainObj.getJSONArray("onSale");
            for (int i = 0; i < onsale_Arr.length(); i++) {
                JSONObject obj = onsale_Arr.getJSONObject(i);
                Coupon coupon = Coupon.decodeFromJSON(obj);
                onsaleList.add(coupon);
            }

            //JSONArray
            JSONArray sold_Arr = mainObj.getJSONArray("sold");
            for (int i = 0; i < sold_Arr.length(); i++) {
                JSONObject obj = sold_Arr.getJSONObject(i);
                Coupon coupon = Coupon.decodeFromJSON(obj);
                soldList.add(coupon);
            }


            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initData() {

        //TODO:init coupons
        if (onsaleList.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("coupons", onsaleList);
            bundle.putSerializable("index", 0);


        }
        if (soldList.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("coupons", soldList);
            bundle.putSerializable("index", 1);
        }

        initTabs();
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

    private void initTabs() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArrayList<CouponCommonFragment> frList = new ArrayList<>();
        frList.add(onsaleFragment);
        frList.add(soldFragment);
        MyCouponFragmentAdapter frAdapter = new MyCouponFragmentAdapter(fragmentManager, frList);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.seller_coupon_viewpager);
        viewPager.setAdapter(frAdapter);
        viewPager.setCurrentItem(index);

        onsaleText.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
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

        onsaleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPage(0);
                viewPager.setCurrentItem(0);
            }
        });

        soldText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPage(1);
                viewPager.setCurrentItem(1);
            }
        });

        selectPage(index);
    }


    private void initSlidingBar() {
        WindowManager wm = getWindowManager();
        screen_width = wm.getDefaultDisplay().getWidth();
    }

    private void slideTo(int pos) {
        int preWidth = screen_width / 2;
        int margins = PixelUtils.dp2px(this, 12);
        int startX = pos * preWidth + margins;
        FrameLayout.LayoutParams scrollParams = (FrameLayout.LayoutParams) scrollBar.getLayoutParams();
        scrollParams.leftMargin = startX;
        scrollParams.width = preWidth - 2 * margins;
        scrollBar.setLayoutParams(scrollParams);
    }

    private void selectPage(int position) {
        slideTo(position);
        onsaleText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        soldText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        switch (position) {
            case 0:
                onsaleText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                break;
            case 1:
                soldText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                break;
        }
    }

    //获取卖家信息
    private void requestData() {
        String url = GlobalConfig.base_URL + GlobalConfig.requestSellerInfo_URL;
        HashMap<String, String> map = new HashMap<>();
        String sellerId = getIntent().getStringExtra("id");
        map.put("sellerID", sellerId);
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
}
