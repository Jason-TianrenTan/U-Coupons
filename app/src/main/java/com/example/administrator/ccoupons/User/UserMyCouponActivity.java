package com.example.administrator.ccoupons.User;

<<<<<<< HEAD
import android.support.v4.app.Fragment;
=======
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
>>>>>>> ttr
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

<<<<<<< HEAD
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
=======
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.PixelUtils.PixelUtils;
import com.example.administrator.ccoupons.User.UserCoupons.User.MyCouponFragmentAdapter;
import com.example.administrator.ccoupons.User.UserCoupons.User.UserOnsaleFragment;
import com.example.administrator.ccoupons.User.UserCoupons.User.UserUnsoldFragment;
import com.example.administrator.ccoupons.User.UserCoupons.User.UserUsedFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
<<<<<<< HEAD
>>>>>>> ttr
=======
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
>>>>>>> Czj


public class UserMyCouponActivity extends AppCompatActivity {


<<<<<<< HEAD
<<<<<<< HEAD
    ArrayList<Coupon> unusedList, onsaleList, notonsaleList;
    UnusedCouponFragment UnusedFragment;
    OnSaleCouponFragment OnSaleFragment;
    NotOnSaleCouponFragment NotOnSaleFragment;
    Fragment fr1, fr2, fr3;
    TextView title_unused, title_onsale, title_nonsale;
    int screen_width;
    LinearLayout scrollBar;
=======
=======
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



>>>>>>> Czj
    int screen_width = 0;
    int index = 0;
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

>>>>>>> ttr

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_coupon);
        ButterKnife.bind(this);
        String indexstr = getIntent().getStringExtra("index");
        if (indexstr != null)
            index = Integer.parseInt(indexstr);
        initViews();
        selectPage(index);
        initReceiver();
        initTabs();
    }


    @Override
    public void onBackPressed() {
        if (index == 0)
            finish();
        else {
            finish();
            startActivity(new Intent(UserMyCouponActivity.this, MainPageActivity.class));
        }
    }

<<<<<<< HEAD
        scrollBar = (LinearLayout) findViewById(R.id.mycoupon_scrollbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.user_mycoupons_toolbar);
=======

    /**
     * Initialize views
     */
    private void initViews() {
>>>>>>> ttr
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
<<<<<<< HEAD
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
=======
>>>>>>> ttr
        WindowManager wm = getWindowManager();
        screen_width = wm.getDefaultDisplay().getWidth();
    }


    /**
     * Slide to
     * @param pos
     */
    private void slideTo(int pos) {
        int preWidth = screen_width / 3;
        int margins = PixelUtils.dp2px(this, 12);
        int startX = pos * preWidth + margins;
        FrameLayout.LayoutParams scrollParams = (FrameLayout.LayoutParams) scrollBar.getLayoutParams();
        scrollParams.leftMargin = startX;
        scrollParams.width = preWidth - 2 * margins;
        scrollBar.setLayoutParams(scrollParams);
    }

<<<<<<< HEAD
    private void initData() {
        fr1 = new EmptyFragment();
        fr2 = new EmptyFragment();
        fr3 = new EmptyFragment();
        unusedList = new ArrayList<Coupon>();
        onsaleList = new ArrayList<Coupon>();
        notonsaleList = new ArrayList<Coupon>();

        //TODO:init coupons
        for (int i = 0; i < 10; i++) {
            Coupon coupon = new Coupon();
            unusedList.add(coupon);
            onsaleList.add(coupon);
            notonsaleList.add(coupon);
        }
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
    }
=======
>>>>>>> ttr

    /**
     * Select page at
     * @param position
     */
    private void selectPage(int position) {
        slideTo(position);
        title_unused.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        title_onsale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        title_unsold.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        switch (position) {
            case 0:
                title_unused.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                break;
            case 1:
                title_onsale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                break;
            case 2:
                title_unsold.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                break;
        }
    }


    /**
     * Init tabs
     */
    private void initTabs() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArrayList<CouponCommonFragment> frList = new ArrayList<CouponCommonFragment>();
        frList.add(usedFragment);
        frList.add(onsaleFragment);
        frList.add(unsoldFragment);
        MyCouponFragmentAdapter frAdapter = new MyCouponFragmentAdapter(fragmentManager, frList);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.mycoupon_viewpager);
        viewPager.setAdapter(frAdapter);
<<<<<<< HEAD
        viewPager.setCurrentItem(0);

        title_unused.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
=======
        viewPager.setCurrentItem(index);
        title_used.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
>>>>>>> ttr
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

        title_unused.setOnClickListener(new View.OnClickListener() {
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
<<<<<<< HEAD


    }

=======
        System.out.println("init Views finished");
    }


    /**
     * Initialize receiver
     */
    public void initReceiver() {
        IntentFilter filter = new IntentFilter("com.example.administrator.ccoupons.UPDATEVIEWS");
        receiver = new UpdateUIReceiver();
        registerReceiver(receiver, filter);
    }


    //UI receiver
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

>>>>>>> ttr

}
