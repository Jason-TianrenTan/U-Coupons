package com.example.administrator.ccoupons.User;

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

import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.PixelUtils;
import com.example.administrator.ccoupons.User.UserCoupons.User.MyCouponFragmentAdapter;
import com.example.administrator.ccoupons.User.UserCoupons.User.UserOnsaleFragment;
import com.example.administrator.ccoupons.User.UserCoupons.User.UserUnsoldFragment;
import com.example.administrator.ccoupons.User.UserCoupons.User.UserUsedFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserMyCouponActivity extends AppCompatActivity {


    int screen_width = 0;
    CouponCommonFragment usedFragment = new UserUsedFragment(),
                        onsaleFragment = new UserOnsaleFragment(),
                        unsoldFragment = new UserUnsoldFragment();
    UpdateUIReceiver receiver;
    @BindView(R.id.user_mycoupons_toolbar)
    Toolbar toolbar;
    @BindView(R.id.used_title_text)
    TextView title_used;
    @BindView(R.id.onsale_title_text)
    TextView title_onsale;
    @BindView(R.id.nonsale_title_text)
    TextView title_unsold;
    @BindView(R.id.mycoupon_scrollbar)
    LinearLayout scrollBar;
    @BindView(R.id.mycoupon_viewpager)
    ViewPager mycouponViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_coupon);
        ButterKnife.bind(this);
        initViews();
        selectPage(0);
        initReceiver();
        initTabs();
    }


    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        title_used.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        title_onsale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        title_unsold.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        switch (position) {
            case 0:
                title_used.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                break;
            case 1:
                title_onsale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                break;
            case 2:
                title_unsold.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                break;
        }
    }

    private void initTabs() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArrayList<CouponCommonFragment> frList = new ArrayList<CouponCommonFragment>();
        frList.add(usedFragment);
        frList.add(onsaleFragment);
        frList.add(unsoldFragment);
        MyCouponFragmentAdapter frAdapter = new MyCouponFragmentAdapter(fragmentManager, frList);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.mycoupon_viewpager);
        viewPager.setAdapter(frAdapter);
        viewPager.setCurrentItem(0);
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

        title_unsold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPage(2);
                viewPager.setCurrentItem(2);
            }
        });
        System.out.println("init Views finished");
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
