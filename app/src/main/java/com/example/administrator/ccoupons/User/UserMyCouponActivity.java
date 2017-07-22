package com.example.administrator.ccoupons.User;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.Message;
import com.example.administrator.ccoupons.Fragments.MessageClass;
import com.example.administrator.ccoupons.Fragments.MessageFragment;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.User.UserCouponFragments.NotOnSaleCouponFragment;
import com.example.administrator.ccoupons.User.UserCouponFragments.OnSaleCouponFragment;
import com.example.administrator.ccoupons.User.UserCouponFragments.UnusedCouponFragment;
import java.util.ArrayList;


public class UserMyCouponActivity extends AppCompatActivity {


    ArrayList<Coupon> unusedList, onsaleList, notonsaleList;
    UnusedCouponFragment UnusedFragment;
    OnSaleCouponFragment OnSaleFragment;
    NotOnSaleCouponFragment NotOnSaleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_coupon);

        Toolbar toolbar = (Toolbar) findViewById(R.id.user_mycoupons_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        UnusedFragment = new UnusedCouponFragment();
        OnSaleFragment = new OnSaleCouponFragment();
        NotOnSaleFragment = new NotOnSaleCouponFragment();
        initData();
        initTabs();
    }

    private void initData() {
        unusedList = new ArrayList<Coupon>();
        onsaleList = new ArrayList<Coupon>();
        notonsaleList = new ArrayList<Coupon>();
        UnusedFragment.setData(unusedList);
        OnSaleFragment.setData(onsaleList);
        NotOnSaleFragment.setData(notonsaleList);
        //TODO:init Coupons
    }

    private void initTabs() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArrayList<Fragment> frList = new ArrayList<Fragment>();
        frList.add(UnusedFragment);
        frList.add(OnSaleFragment);
        frList.add(NotOnSaleFragment);
        MyCouponFragmentAdapter frAdapter = new MyCouponFragmentAdapter(fragmentManager, frList);
        ViewPager viewPager = (ViewPager)findViewById(R.id.mycoupon_viewpager);
        viewPager.setAdapter(frAdapter);
        viewPager.setCurrentItem(0);
    }


}
