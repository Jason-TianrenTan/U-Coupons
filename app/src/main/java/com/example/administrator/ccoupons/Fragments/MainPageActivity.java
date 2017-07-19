package com.example.administrator.ccoupons.Fragments;
/*
*首页布局
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
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
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.administrator.ccoupons.Connections.MessageGetService;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.CategoryFragment;
import com.example.administrator.ccoupons.Fragments.UserOptionFragment;
import com.example.administrator.ccoupons.R;

import java.util.Timer;
import java.util.TimerTask;


public class MainPageActivity extends AppCompatActivity {
    private boolean exit = false;

    private CategoryFragment categoryFragment;
    private UserOptionFragment userOptionFragment;
    private MessageFragment messageFragment;




    private Fragment[] fragments = new Fragment[3];
    private ConvenientBanner convenientBanner;//顶部广告栏控件;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        initService();
        initFragments();
        initNavigationBar();

    }

    private void initService() {
        Intent intent = new Intent(this, MessageGetService.class);
        startService(intent);
    }


    private void initFragments() {
        categoryFragment = new CategoryFragment();
        userOptionFragment = new UserOptionFragment();
        messageFragment = new MessageFragment();
        fragments[0] = categoryFragment;
        fragments[1] = userOptionFragment;
        fragments[2] = messageFragment;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_frame, categoryFragment);
        fragmentTransaction.add(R.id.fragment_frame, userOptionFragment);
        fragmentTransaction.add(R.id.fragment_frame, messageFragment);
        fragmentTransaction.commit();
        showFragment(1);
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
        if (messageFragment != null)
            ft.hide(messageFragment);
    }

    //初始化底部导航栏
    private void initNavigationBar() {
        LinearLayout navigationBar = (LinearLayout) findViewById(R.id.bottom_nav_container);
        LinearLayout nav_item_main = (LinearLayout) navigationBar.findViewById(R.id.id_left1),
                nav_item_aboutme = (LinearLayout) navigationBar.findViewById(R.id.id_right1),
                nav_item_message = (LinearLayout) navigationBar.findViewById(R.id.id_left2);
        TextView titleView_main = (TextView) nav_item_main.findViewById(R.id.navigation_icon_text);
        titleView_main.setText("首页");
        TextView titleView_message = (TextView) nav_item_message.findViewById(R.id.navigation_icon_text);
        titleView_message.setText("消息");
        TextView titleView_aboutme = (TextView) nav_item_aboutme.findViewById(R.id.navigation_icon_text);
        titleView_aboutme.setText("我的");
        nav_item_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(1);
            }
        });
        nav_item_aboutme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(2);
            }
        });
        nav_item_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(3);
            }
        });
    }

    //按返回键回到F1,在F1双击返回键退出
    @Override
    public void onBackPressed() {
        if (!categoryFragment.isHidden()) {
            if (exit) {
                super.onBackPressed();
            } else {
                exit = true;
                Toast.makeText(getApplicationContext(),
                        "再按返回键退出程序", Toast.LENGTH_SHORT).show();
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        exit = false;
                        timer.cancel();
                    }
                }, 2000);
            }
        } else showFragment(1);
    }

    private void parseMessageJSON(String msg) {
        //TODO:parse JSON string
    }

    public class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String json = intent.getStringExtra("content");
            parseMessageJSON(json);
            System.out.println("Another loop starts...");
            Intent i = new Intent(context, MessageGetService.class);
            context.startService(i);
        }

    }

}
