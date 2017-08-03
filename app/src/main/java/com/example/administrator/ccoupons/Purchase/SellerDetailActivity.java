package com.example.administrator.ccoupons.Purchase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.ImageManager;
import com.example.administrator.ccoupons.Tools.PixelUtils;
import com.example.administrator.ccoupons.User.MyCouponFragmentAdapter;
import com.example.administrator.ccoupons.User.UserCouponFragments.EmptyFragment;
import com.example.administrator.ccoupons.User.UserCouponFragments.NotOnSaleCouponFragment;
import com.example.administrator.ccoupons.User.UserCouponFragments.OnSaleCouponFragment;
import com.example.administrator.ccoupons.User.UserCouponFragments.UsedCouponFragment;
import com.example.administrator.ccoupons.User.UserMyCouponActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {


    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;

    private TextView sellerNameText;
    private ImageView sellerAvatar;
    private TextView onsaleText, soldText;

    private LinearLayout scrollBar;
    private Fragment fr1, fr2;

    private ArrayList<Coupon> onsaleList, soldList;

    private int screen_width;

    private int index;

    private UpdateUIReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_detail);

        bindViews();
        initViews();
        initSlidingBar();

        selectPage(index);

        initReceiver();

        String iStr = getIntent().getStringExtra("index");
        if (iStr != null)
            index = Integer.parseInt(iStr);
        requestData();
        mAppBarLayout.addOnOffsetChangedListener(this);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
    }


    private void initViews() {

        String url = DataHolder.base_URL + "/static/" + getIntent().getStringExtra("avatar");
        System.out.println("url = " + url);
        String name = getIntent().getStringExtra("nickname");
        sellerNameText.setText(name);
        sellerAvatar.setImageResource(R.drawable.testportrait);
        ImageManager.GlideImage(((MyApp) getApplicationContext()).getAvatar(), sellerAvatar, getApplicationContext());

        fr1 = new EmptyFragment();
        fr2 = new EmptyFragment();
        onsaleList = new ArrayList<>();
        soldList = new ArrayList<>();
    }


    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);

        sellerNameText = (TextView) findViewById(R.id.seller_name_text);
        sellerAvatar = (ImageView) findViewById(R.id.seller_avatar_imageview);

        scrollBar = (LinearLayout) findViewById(R.id.seller_coupon_scrollbar);
        onsaleText = (TextView) findViewById(R.id.seller_onsale_text);
        soldText = (TextView) findViewById(R.id.seller_sold_text);
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

            fr1 = new CouponDisplayFragment();
            fr1.setArguments(bundle);

        }
        if (soldList.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("coupons", soldList);
            bundle.putSerializable("index", 0);

            fr2 = new CouponDisplayFragment();
            fr2.setArguments(bundle);
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
        ArrayList<Fragment> frList = new ArrayList<Fragment>();
        frList.add(fr1);
        frList.add(fr2);
        MyCouponFragmentAdapter frAdapter = new MyCouponFragmentAdapter(fragmentManager, frList);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.mycoupon_viewpager);
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
        int preWidth = screen_width / 3;
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
        String url = DataHolder.base_URL + DataHolder.requestSellerInfo_URL;
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
/*
{"brand": [{"name": "\u80af\u5fb7\u57fa", "address": "\u7fa4\u5149"}],
"limit": [{"content": "\u53ea\u9650\u7fa4\u5149\u4f7f\u7528"}, {"content": "\u6bcf\u4e2a\u5ba2\u6237\u4f7f\u7528\u4e00\u4e00\u5f20"},
{"content": "\u6ee140\u5143\u53ef\u4f7f\u7528"}], "seller": [{"id": "1500627332dhgt",
"nickname": "\u6d4b\u8bd5\u8d26\u6237", "gender": "\u7537", "avatar": "images/avatar/1501769225141.jpg"}], "isLike": 1}
 */
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
