package com.example.administrator.ccoupons.Fragments;
/*
*首页布局
 */

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.example.administrator.ccoupons.AddCoupon.FillFormActivity;
import com.example.administrator.ccoupons.AddCoupon.QRcodeActivity;
import com.example.administrator.ccoupons.Connections.MessageGetService;
import com.example.administrator.ccoupons.Events.MessageRefreshEvent;
import com.example.administrator.ccoupons.Fragments.Category.CategoryFragment;
import com.example.administrator.ccoupons.Fragments.Message.Message;
import com.example.administrator.ccoupons.Fragments.Message.MessageFragment;
import com.example.administrator.ccoupons.Fragments.User.UserOptionFragment;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainPageActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @BindView(R.id.main_bottombar)
    BottomNavigationBar bottomLayout;

    private ArrayList<Message> messageList;
    private boolean exit = false;
    private AlarmReceiver receiver;

    private CategoryFragment categoryFragment;
    private UserOptionFragment userOptionFragment;
    private MessageFragment messageFragment;

    private Fragment[] fragments = new Fragment[3];
    private BottomNavigationItem mainNavItem, messageNavItem, meNavItem;
    private ShapeBadgeItem messageBadgeItem;
    private boolean hasUnread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ButterKnife.bind(this);
        initFragments();
        initNavigationBar();
    //    initService();
    }


    //init service for requesting message
    private void initService() {

        receiver = new AlarmReceiver();
        IntentFilter filter = new IntentFilter("com.example.administrator.ccoupons.MESSAGE_BROADCAST");
        registerReceiver(receiver, filter);

        Intent intent = MessageGetService.newIntent(this);
        startService(intent);
        MessageGetService.setServiceAlarm(this);

    }


    //init mainpage fragments
    private void initFragments() {
        categoryFragment = new CategoryFragment();
        userOptionFragment = new UserOptionFragment();
        messageFragment = new MessageFragment();
        fragments[0] = categoryFragment;
        fragments[1] = messageFragment;
        fragments[2] = userOptionFragment;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_frame, categoryFragment);
        fragmentTransaction.add(R.id.fragment_frame, messageFragment);
        fragmentTransaction.add(R.id.fragment_frame, userOptionFragment);
        fragmentTransaction.commit();
        showFragment(0);
    }


    /**
     * show fragment at index
     * @param index the selected index
     */
    private void showFragment(int index) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        hideAllFragments(ft);
        ft.show(fragments[index]);
        ft.commitAllowingStateLoss();
    }


    /**
     * hide fragments
     * @param ft
     */
    private void hideAllFragments(FragmentTransaction ft) {
        if (categoryFragment != null)
            ft.hide(categoryFragment);
        if (userOptionFragment != null)
            ft.hide(userOptionFragment);
        if (messageFragment != null)
            ft.hide(messageFragment);
    }


    //init bottom navigation bar
    private void initNavigationBar() {
        mainNavItem = new BottomNavigationItem(R.drawable.main_mainpage, "主页").setActiveColor(R.color.blue);
        messageNavItem = new BottomNavigationItem(R.drawable.message, "消息").setActiveColor(R.color.red);
        meNavItem = new BottomNavigationItem(R.drawable.main_me, "我的").setActiveColor(R.color.yellow);
        addMessageBadge();
        bottomLayout.setMode(BottomNavigationBar.MODE_FIXED);
        bottomLayout.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomLayout.addItem(mainNavItem)
                .addItem(messageNavItem)
                .addItem(meNavItem)
                .setFirstSelectedPosition(0)
                .initialise();
        bottomLayout.setTabSelectedListener(this);
    }


    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            showFragment(position);
        }

    }

    @Override
    public void onTabUnselected(int position) {
    }


    @Override
    public void onTabReselected(int position) {

    }


    //press back twice to exit
    @Override
    public void onBackPressed() {
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
    }


    /**
     * parse message from main page
     * @param msg
     */
    private void parseMessageJSON(String msg) {
        System.out.println("message = " + msg);
        //TODO:parse JSON string
        messageList = new ArrayList<>();
        try {
            MyApp app = (MyApp) getApplicationContext();
            JSONObject mainObj = new JSONObject(msg);
            JSONArray jsonArray = mainObj.getJSONArray("messageResult");
            JSONArray couponArray = mainObj.getJSONArray("couponResult");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Message message = Message.decodeFromJSON(this, obj);
                if (app.getMessageList()!=null && !app.getMessageList().contains(message)) {
                    JSONObject couponObj = couponArray.getJSONObject(i);
                    String couponName = couponObj.getString("product");
                    message.setCouponName(couponName);
                    String listprice = couponObj.getString("listprice");
                    String img_url = couponObj.getString("pic");
                    String discount = couponObj.getString("discount");
                    String value = couponObj.getString("value");
                    String expireTime = couponObj.getString("expiredtime");

                    message.setDiscount(discount);
                    message.setExpireTime(expireTime);
                    message.setCouponPrice(listprice);
                    message.setCouponURL(img_url);
                    message.setValue(value);
                    messageList.add(message);
                }
            }

            app.setMessageList(messageList);
            sendNotification("您有新的消息");
            EventBus.getDefault().post(new MessageRefreshEvent());
            hasUnread = true;
            messageBadgeItem.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * add badge to message fragment
     */
    public void addMessageBadge() {
        messageBadgeItem = new ShapeBadgeItem();
        messageBadgeItem.setShape(ShapeBadgeItem.SHAPE_OVAL)
                .setShapeColorResource(R.color.red)
                .setSizeInDp(this, 10, 10)
                .setHideOnSelect(false);
        messageNavItem.setBadgeItem(messageBadgeItem);
        messageBadgeItem.hide();
    }


    //alarm receiver
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
        if (receiver != null)
            unregisterReceiver(receiver);
        super.onDestroy();
    }


    //send notifications to user
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
