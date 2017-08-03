package com.example.administrator.ccoupons.Fragments;
/*
*首页布局
 */

import android.app.Application;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.CardView;
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
import com.example.administrator.ccoupons.AddCoupon.FillFormActivity;
import com.example.administrator.ccoupons.Connections.MessageGetService;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.QRcodeActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainPageActivity extends AppCompatActivity {


    private ArrayList<Message> messageList;
    private boolean exit = false;
    private AlarmReceiver receiver;
    private CategoryFragment categoryFragment;
    private UserOptionFragment userOptionFragment;

    private TextView titleView_main, titleView_aboutme;
    private ImageView imgView_main, imgView_me;

    private Fragment[] fragments = new Fragment[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        initNavigationBar();
        initFragments();

        initService();
    }

    private void initService() {

        receiver = new AlarmReceiver();
        IntentFilter filter = new IntentFilter("com.example.administrator.ccoupons.MESSAGE_BROADCAST");
        registerReceiver(receiver, filter);

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
        imgView_main.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.main_mainpage_pressed));
    }


    private void showFragment(int index) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        hideAllFragments(ft);
        ft.show(fragments[index - 1]);
        ft.commitAllowingStateLoss();
        imgView_main.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.main_mainpage));
        imgView_me.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.main_me));
        titleView_main.setTextColor(Color.WHITE);
        titleView_aboutme.setTextColor(Color.WHITE);
        if (index == 1) {
            imgView_main.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.main_mainpage_pressed));
            titleView_main.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else {
            imgView_me.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.main_me_pressed));
            titleView_aboutme.setTextColor(ContextCompat.getColor(this, R.color.black));
        }
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
        titleView_main = (TextView) nav_item_main.findViewById(R.id.navigation_icon_text);
        titleView_main.setText("首页");

        imgView_main = (ImageView) nav_item_main.findViewById(R.id.navigation_icon);
        imgView_main.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.main_mainpage));
        titleView_aboutme = (TextView) nav_item_aboutme.findViewById(R.id.navigation_icon_text);
        titleView_aboutme.setText("我的");

        imgView_me = (ImageView) nav_item_aboutme.findViewById(R.id.navigation_icon);
        imgView_me.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.main_me));
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
                this.finish();
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


    /*

    {"messageResult":
    [{"messageid": "001", "content": "lalala", "time": "2017-01-01",
    "messagecat": "\u4e0a\u67b6\u7684\u4f18\u60e0\u5238\u88ab\u8d2d\u4e70", "hasread": 0, "couponid": "001"},
    {"messageid": "002", "content": "dididi", "time": "2017-01-01", "messagecat": "\u4e0a\u67b6\u7684\u4f18\u60e0\u5238\u5373\u5c06\u8fc7\u671f",
    "hasread": 0, "couponid": "002"}],
    "couponResult": [{"product": "\u542e\u6307\u539f\u5473\u9e21wh"}, {"product": "\u829d\u58eb\u85af\u6761wh"}]}
     */
    private void parseMessageJSON(String msg) {
        System.out.println("message = " + msg);
        //TODO:parse JSON string
        messageList = new ArrayList<>();
        try {
            JSONObject mainObj = new JSONObject(msg);
            JSONArray jsonArray = mainObj.getJSONArray("messageResult");
            JSONArray couponArray = mainObj.getJSONArray("couponResult");
            System.out.println(jsonArray.length() + ",,,," + couponArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Message message = Message.decodeFromJSON(this, obj);
                JSONObject couponObj = couponArray.getJSONObject(i);
                String couponName = couponObj.getString("product");
                message.setCouponName(couponName);
                String listprice = couponObj.getString("listprice");
                String img_url = couponObj.getString("pic");
                message.setCouponPrice(listprice);
                message.setCouponURL(img_url);
                messageList.add(message);
            }
            MyApp app = (MyApp) getApplicationContext();
            app.setMessageList(messageList);
            sendNotification("您有新的消息");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String json = intent.getStringExtra("content");
            parseMessageJSON(json);
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
                startActivity(new Intent(MainPageActivity.this, QRcodeActivity.class));
                mCameraDialog.dismiss();
            }
        });
        FillFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPageActivity.this, FillFormActivity.class));
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


    //发送通知
    public void sendNotification(String str) {
        Intent i = new Intent(this, MainPageActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Ticker")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("U惠")
                .setContentText(str)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .build();
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(0, notification);
    }
}
