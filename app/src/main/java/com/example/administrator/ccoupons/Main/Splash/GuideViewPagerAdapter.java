package com.example.administrator.ccoupons.Main.Splash;

/**
 * Created by atsst on 2017/10/31.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class GuideViewPagerAdapter extends FragmentPagerAdapter {


    private List<SplashFragment> splashList;


    public GuideViewPagerAdapter(FragmentManager frManager, List<SplashFragment> splashFragments) {
        super(frManager);
        this.splashList = splashFragments;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return splashList.get(arg0);
    }


    /**
     * get list count
     * @return
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return splashList.size();
    }


}

