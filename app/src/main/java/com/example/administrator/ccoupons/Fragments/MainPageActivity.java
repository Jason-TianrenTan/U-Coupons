package com.example.administrator.ccoupons.Fragments;
/*
*首页布局
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
<<<<<<< HEAD
=======
import android.app.Notification;
import android.app.PendingIntent;
>>>>>>> ttr
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
<<<<<<< HEAD
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
=======
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
>>>>>>> ttr
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
<<<<<<< HEAD
import android.widget.LinearLayout;
import android.widget.TextView;
=======
import android.widget.RelativeLayout;
>>>>>>> ttr
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
<<<<<<< HEAD
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.administrator.ccoupons.Connections.MessageGetService;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.CategoryFragment;
import com.example.administrator.ccoupons.Fragments.UserOptionFragment;
import com.example.administrator.ccoupons.R;
=======
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
>>>>>>> ttr

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainPageActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

<<<<<<< HEAD
public class MainPageActivity extends AppCompatActivity {
=======

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @BindView(R.id.main_bottombar)
    BottomNavigationBar bottomLayout;

    private ArrayList<Message> messageList;
>>>>>>> ttr
    private boolean exit = false;
    private AlarmReceiver receiver;

    private CategoryFragment categoryFragment;
    private UserOptionFragment userOptionFragment;
    private MessageFragment messageFragment;

<<<<<<< HEAD

    private Fragment[] fragments = new Fragment[3];
=======
    private Fragment[] fragments = new Fragment[3];
    private BottomNavigationItem mainNavItem, messageNavItem, meNavItem;
    private ShapeBadgeItem messageBadgeItem;
    private boolean hasUnread = false;
>>>>>>> ttr

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
<<<<<<< HEAD

        initFragments();
        initNavigationBar();

=======
        ButterKnife.bind(this);
        initFragments();
        initNavigationBar();
<<<<<<< HEAD
>>>>>>> ttr
        initService();

=======
    //    initService();
>>>>>>> Czj
    }


    //init service for requesting message
    private void initService() {

        receiver = new AlarmReceiver();
        IntentFilter filter = new IntentFilter("com.example.administrator.ccoupons.MESSAGE_BROADCAST");
        registerReceiver(receiver,filter);

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
<<<<<<< HEAD
        showFragment(1);
=======
        showFragment(0);
>>>>>>> ttr
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
<<<<<<< HEAD
        LinearLayout navigationBar = (LinearLayout) findViewById(R.id.bottom_nav_container);
        LinearLayout nav_item_main = (LinearLayout) navigationBar.findViewById(R.id.id_left1),
                nav_item_aboutme = (LinearLayout) navigationBar.findViewById(R.id.id_right1);
        TextView titleView_main = (TextView) nav_item_main.findViewById(R.id.navigation_icon_text);
        titleView_main.setText("首页");
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
=======
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

>>>>>>> ttr
    }

    @Override
<<<<<<< HEAD
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

=======
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
>>>>>>> ttr
    private void parseMessageJSON(String msg) {
        //TODO:parse JSON string
<<<<<<< HEAD
=======
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
>>>>>>> ttr
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
            System.out.println("Received, content = " + json);
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
<<<<<<< HEAD
=======


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
>>>>>>> ttr
}
