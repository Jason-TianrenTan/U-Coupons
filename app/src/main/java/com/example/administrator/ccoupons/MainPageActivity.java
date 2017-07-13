package com.example.administrator.ccoupons;
/*
*首页布局
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.CategoryFragment;
import com.example.administrator.ccoupons.Fragments.UserOptionFragment;
import com.example.administrator.ccoupons.Main.MainActivity;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;

import java.util.ArrayList;
import java.util.List;


public class MainPageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    CategoryFragment categoryFragment;
    UserOptionFragment userOptionFragment;

    Fragment[] fragments = new Fragment[2];
    private ConvenientBanner convenientBanner;//顶部广告栏控件;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        initFragments();
        initNavigationBar();

    }

    private void initFragments() {
        categoryFragment = new CategoryFragment();
        userOptionFragment = new UserOptionFragment();
        fragments[0] = categoryFragment;
        fragments[1] = userOptionFragment;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_frame, categoryFragment);
        fragmentTransaction.add(R.id.fragment_frame, userOptionFragment);
        fragmentTransaction.commit();
        fragmentTransaction.hide(userOptionFragment);
        fragmentTransaction.show(categoryFragment);
    }


    private void showFragment(int index) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        hideAllFragments(ft);
        ft.show(fragments[index - 1]);
        ft.commitAllowingStateLoss();
    }

    private void hideAllFragments(FragmentTransaction ft) {
        if (categoryFragment != null)
            ft.hide(categoryFragment);
        if (userOptionFragment != null) {
            ft.hide(userOptionFragment);
        }
    }

    //初始化底部导航栏
    private void initNavigationBar() {
        LinearLayout navigationBar = (LinearLayout) findViewById(R.id.bottom_nav_container);
        LinearLayout nav_item_main = (LinearLayout) navigationBar.findViewById(R.id.id_left1),
                nav_item_aboutme = (LinearLayout) navigationBar.findViewById(R.id.id_right1);
        nav_item_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("fragment 1");
                showFragment(1);
            }
        });
        nav_item_aboutme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("fragment 2");
                showFragment(2);
            }
        });
    }


    /*
    //初始化展示板
    private void initBanner(){
        for (int i = 0; i< DataHolder.Banners.covers.length; i++)
            localImages.add(DataHolder.Banners.covers[i]);

        //本地图片例子
        convenientBanner = (ConvenientBanner)findViewById(R.id.convenientBanner);
        convenientBanner.setPages(
                new com.bigkoo.convenientbanner.holder.CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                .setOnItemClickListener(this);
                //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件

    }*/


    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        //  convenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        //  convenientBanner.stopTurning();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
