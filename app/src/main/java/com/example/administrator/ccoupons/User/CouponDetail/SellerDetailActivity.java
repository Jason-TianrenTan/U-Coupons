package com.example.administrator.ccoupons.User.CouponDetail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.example.administrator.ccoupons.User.UserCoupons.User.MyCouponFragmentAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SellerDetailActivity extends AppCompatActivity {


    SellerOnsaleFragment onsaleFragment;
    SellerSoldFragment soldFragment;
    int screen_width = -1;
    UpdateUIReceiver receiver;
    @BindView(R.id.seller_avatar_imageview)
    CircleImageView sellerAvatar;
    @BindView(R.id.seller_name_text)
    TextView sellerNameText;
    @BindView(R.id.register_main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.seller_onsale_text)
    TextView onsaleText;
    @BindView(R.id.seller_sold_text)
    TextView soldText;
    @BindView(R.id.seller_coupon_scrollbar)
    LinearLayout scrollBar;
    @BindView(R.id.seller_coupon_viewpager)
    ViewPager sellerCouponViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_detail);
        ButterKnife.bind(this);
        initViews();
        selectPage(0);
        initReceiver();
        initTabs();

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
        WindowManager wm = getWindowManager();
        screen_width = wm.getDefaultDisplay().getWidth();
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
        onsaleFragment = new SellerOnsaleFragment();
        soldFragment = new SellerSoldFragment();
        Bundle onsaleBundle = new Bundle(),
                soldBundle = new Bundle();
        onsaleBundle.putString("sellerId", getIntent().getStringExtra("id"));
        soldBundle.putString("sellerId", getIntent().getStringExtra("id"));
        onsaleFragment.setArguments(onsaleBundle);
        soldFragment.setArguments(soldBundle);
        frList.add(onsaleFragment);
        frList.add(soldFragment);
        MyCouponFragmentAdapter frAdapter = new MyCouponFragmentAdapter(fragmentManager, frList);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.seller_coupon_viewpager);
        viewPager.setAdapter(frAdapter);
        viewPager.setCurrentItem(0);

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

}
