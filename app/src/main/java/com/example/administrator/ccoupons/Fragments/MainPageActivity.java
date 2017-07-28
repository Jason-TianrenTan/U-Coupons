package com.example.administrator.ccoupons.Fragments;
/*
*首页布局
 */

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.AddCoupon.AddCouponActivity;
import com.example.administrator.ccoupons.Connections.MessageGetService;
import com.example.administrator.ccoupons.R;

import java.util.Timer;
import java.util.TimerTask;


public class MainPageActivity extends AppCompatActivity {
    private boolean exit = false;
    private AlarmReceiver receiver;
    private CategoryFragment categoryFragment;
    private UserOptionFragment userOptionFragment;


    private Fragment[] fragments = new Fragment[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        initFragments();
        initNavigationBar();

        initService();
    }

    private void initService() {

        receiver = new AlarmReceiver();
        IntentFilter filter = new IntentFilter("com.example.administrator.ccoupons.MESSAGE_BROADCAST");
        registerReceiver(receiver,filter);

        Intent intent = MessageGetService.newIntent(this);
        startService(intent);
        MessageGetService.setServiceAlarm(this);

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
    }

    //初始化底部导航栏
    private void initNavigationBar() {

        LinearLayout navigationBar = (LinearLayout) findViewById(R.id.bottom_nav_container);
        LinearLayout nav_item_main = (LinearLayout) navigationBar.findViewById(R.id.id_left1),
                nav_item_aboutme = (LinearLayout) navigationBar.findViewById(R.id.id_right1);
        TextView titleView_main = (TextView) nav_item_main.findViewById(R.id.navigation_icon_text);
        titleView_main.setText("首页");
        TextView titleView_aboutme = (TextView) nav_item_aboutme.findViewById(R.id.navigation_icon_text);
        titleView_aboutme.setText("我的");
        ImageView sellButton = (ImageView) findViewById(R.id.mainpage_button_sell);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog();
            }
        });
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
    }

    //按返回键回到F1,在F1双击返回键退出
    @Override
    public void onBackPressed() {
        if (!categoryFragment.isHidden()) {
            if (exit) {
                super.onBackPressed();
            } else {
                exit = true;
                Toast.makeText(this,
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
            System.out.println("Received, content = " + json);
        }

        public AlarmReceiver() {

        }

    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void setDialog() {
        final Dialog mCameraDialog = new Dialog(this, R.style.BottomDialog);
        RelativeLayout root = (RelativeLayout) LayoutInflater.from(this).inflate(
                R.layout.bottom_sheet, null);

        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        root.measure(0, 0);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        lp.width = displayMetrics.widthPixels;
        lp.height = displayMetrics.heightPixels;

        lp.alpha = 1f; // 透明度

        //设置监听器
        ImageView closeButton = (ImageView) mCameraDialog.findViewById(R.id.close_btn_bottom_sheet);
        ImageView QRScanButton = (ImageView) mCameraDialog.findViewById(R.id.sell_btn_scanqr),
                FillFormButton = (ImageView) mCameraDialog.findViewById(R.id.sell_btn_fillform);
        QRScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        FillFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPageActivity.this, AddCouponActivity.class));
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraDialog.dismiss();
            }
        });
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }
}
