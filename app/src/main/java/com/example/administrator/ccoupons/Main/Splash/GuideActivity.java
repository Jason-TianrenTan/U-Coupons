package com.example.administrator.ccoupons.Main.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.ccoupons.Main.WelcomeActivity;
import com.example.administrator.ccoupons.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    @BindView(R.id.btn_enter)
    Button btnEnter;
    private GuideViewPagerAdapter adapter;
    private List<SplashFragment> splashFragments;

    // 引导页图片资源
    private static final int[] pics = {R.drawable.splash_slide1,
            R.drawable.splash_slide2, R.drawable.splash_slide3};

    private static final int[] titles = {R.string.splash_title1, R.string.splash_title2, R.string.splash_title3};

    private static final int[] contents_above = {R.string.splash_content_above1,
            R.string.splash_content_above2, R.string.splash_content_above3};

    private static final int[] contents_below = {R.string.splash_content_below1,
            R.string.splash_content_below2, R.string.splash_content_below3};

    String[] title_array, content_above_array, content_below_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        initResource();
        initViews();
    }


    private void initResource() {
        title_array = new String[titles.length];
        content_above_array = new String[contents_above.length];
        content_below_array = new String[contents_below.length];
        for (int i=0;i<titles.length;i++) {
            title_array[i] = getResources().getString(titles[i]);
            content_above_array[i] = getResources().getString(contents_above[i]);
            content_below_array[i] = getResources().getString(contents_below[i]);
        }
    }


    private void initViews() {
        splashFragments = new ArrayList<>();
        for (int i =0;i<pics.length;i++) {
            int id = pics[i];
            String title = title_array[i], content_above = content_above_array[i],
                                            content_below = content_below_array[i];
            Bundle bundle = new Bundle();
            bundle.putInt("res_id", id);
            bundle.putString("title", title);
            bundle.putString("content_above", content_above);
            bundle.putString("content_below", content_below);
            SplashFragment fragment = new SplashFragment();
            fragment.setArguments(bundle);
            splashFragments.add(fragment);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new GuideViewPagerAdapter(fragmentManager, splashFragments);
        vpGuide.setAdapter(adapter);
        vpGuide.setOnPageChangeListener(new PageChangeListener());
        vpGuide.setCurrentItem(0);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("enter")) {
            enterMainActivity();
            return;
        }
    }


    private void enterMainActivity() {
        Intent intent = new Intent(GuideActivity.this,
                WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int position) {
            // arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。

        }

        // 当前页面被滑动时调用
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

        }

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int position) {
            // 显示体验按钮
            if (position == splashFragments.size() - 1) {
                btnEnter.setVisibility(View.VISIBLE);// 显示
            } else {
                btnEnter.setVisibility(View.GONE);// 隐藏
            }
        }

    }
}
