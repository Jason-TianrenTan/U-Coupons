package com.example.administrator.ccoupons.User;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.Message;
import com.example.administrator.ccoupons.Fragments.MessageClass;
import com.example.administrator.ccoupons.Fragments.MessageFragment;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.PixelUtils;
import com.example.administrator.ccoupons.User.UserCouponFragments.EmptyFragment;
import com.example.administrator.ccoupons.User.UserCouponFragments.NotOnSaleCouponFragment;
import com.example.administrator.ccoupons.User.UserCouponFragments.OnSaleCouponFragment;
import com.example.administrator.ccoupons.User.UserCouponFragments.UnusedCouponFragment;

import java.util.ArrayList;


public class UserMyCouponActivity extends AppCompatActivity {


    ArrayList<Coupon> unusedList, onsaleList, notonsaleList;
    UnusedCouponFragment UnusedFragment;
    OnSaleCouponFragment OnSaleFragment;
    NotOnSaleCouponFragment NotOnSaleFragment;
    Fragment fr1, fr2, fr3;
    TextView title_unused, title_onsale, title_nonsale;
    int screen_width;
    LinearLayout scrollBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_coupon);

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
        title_unused = (TextView) findViewById(R.id.unused_title_text);
        title_onsale = (TextView) findViewById(R.id.onsale_title_text);
        title_nonsale = (TextView) findViewById(R.id.nonsale_title_text);

        UnusedFragment = new UnusedCouponFragment();
        OnSaleFragment = new OnSaleCouponFragment();
        NotOnSaleFragment = new NotOnSaleCouponFragment();
        initData();
        initTabs();
        initSlidingBar();

        slideTo(0);
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
        fr1 = new EmptyFragment();
        fr2 = new EmptyFragment();
        fr3 = new EmptyFragment();
        unusedList = new ArrayList<Coupon>();
        onsaleList = new ArrayList<Coupon>();
        notonsaleList = new ArrayList<Coupon>();
        if (unusedList.size() > 0) {
            UnusedFragment.setData(unusedList);
            fr1 = UnusedFragment;
        }
        if (onsaleList.size() > 0) {
            OnSaleFragment.setData(onsaleList);
            fr2 = OnSaleFragment;
        }
        if (notonsaleList.size() > 0) {
            NotOnSaleFragment.setData(notonsaleList);
            fr3 = NotOnSaleFragment;
        }
        //TODO:init Coupons
    }

    private void initTabs() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArrayList<Fragment> frList = new ArrayList<Fragment>();
        frList.add(fr1);
        frList.add(fr2);
        frList.add(fr3);
        MyCouponFragmentAdapter frAdapter = new MyCouponFragmentAdapter(fragmentManager, frList);
        ViewPager viewPager = (ViewPager) findViewById(R.id.mycoupon_viewpager);
        viewPager.setAdapter(frAdapter);
        viewPager.setCurrentItem(0);

        title_unused.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                slideTo(position);
                title_unused.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                title_onsale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                title_nonsale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                switch (position) {
                    case 0:
                        title_unused.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                        break;
                    case 1:
                        title_onsale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                        break;
                    case 2:
                        title_nonsale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


}
