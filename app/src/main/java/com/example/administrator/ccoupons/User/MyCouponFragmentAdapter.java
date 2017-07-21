package com.example.administrator.ccoupons.User;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class MyCouponFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> frList;
    public MyCouponFragmentAdapter(FragmentManager frManager, ArrayList<Fragment> list) {
        super(frManager);
        frList = list;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return frList.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return frList.size();
    }

}
