package com.example.administrator.ccoupons;
/*
*首页布局
 */
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.CategoryFragment;
import com.example.administrator.ccoupons.Main.MainActivity;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;

import java.util.ArrayList;
import java.util.List;


public class MainPageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,OnItemClickListener,BottomNavigationBar.OnTabSelectedListener {

    SpaceNavigationView spaceNavigationView;
    CategoryFragment categoryFragment;

    private ConvenientBanner convenientBanner;//顶部广告栏控件;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        initNavigationBar();
        initFragments();
    }

    private void initFragments() {
        categoryFragment = new CategoryFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_frame,categoryFragment);
        fragmentTransaction.commit();
        fragmentTransaction.show(categoryFragment);
    }


    //初始化底部导航栏
    private void initNavigationBar() {
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.lavenderpos, "买!").setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.purplepos, "卖!").setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.mipmap.lightgraypos, "消息").setActiveColorResource(R.color.red))
                .addItem(new BottomNavigationItem(R.mipmap.pos, "我的").setActiveColorResource(R.color.blue))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);

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

    @Override
    public void onItemClick(int position) {
        //点击第position个
    }

    @Override
    public void onTabSelected(int position) {
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
    //    hideFragments(ft);
        switch (position) {
            case 1:
                ft.show(categoryFragment);
                break;
            /*
            case 1:
                ft.show(fg2);
                break;
            case 2:
                ft.show(fg3);
                break;*/
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {

    }

}
