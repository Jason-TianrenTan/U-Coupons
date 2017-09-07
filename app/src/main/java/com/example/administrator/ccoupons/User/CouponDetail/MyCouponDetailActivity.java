package com.example.administrator.ccoupons.User.CouponDetail;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.PixelUtils;
import com.example.administrator.ccoupons.User.UserCoupons.CouponModifiedEvent;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.administrator.ccoupons.User.UserCouponInfoAdapter.INFO_CODE;

/**
 * 属于我的优惠券
 */
public class MyCouponDetailActivity extends BaseDetailActivity {

    @BindView(R.id.page_button_stat)
    ImageView statButton;
    boolean isOnsale = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        String statString = getIntent().getStringExtra("stat");
        if (statString.equals("onsale"))
            isOnsale = true;
    }


    private void refreshStat() {
        if (isOnsale)
            statButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.coupon_store));
        else
            statButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.coupon_sale));
    }

    @Override
    public void initBottomViews(boolean isLiked) {
        super.inflateBottomView(R.layout.stat_bottom_bar);
        refreshStat();

        statButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnsale)
                    sendStatRequest("store");
                else sendStatRequest("onsale");
            }
        });

    }


    @Override
    public void onKeyBack() {
        System.out.println("on key back");
        EventBus.getDefault().post(new CouponModifiedEvent());
    }


    private void parseMessage(String response) {
        System.out.println("received response = " + response);
        try {
            JSONObject obj = new JSONObject(response);
            String result = obj.getString("errno");
            int code = Integer.parseInt(result);
            if (code == 0) {
                /*
                Intent intent = new Intent("com.example.administrator.ccoupons.UPDATEVIEWS");
                sendBroadcast(intent);*/
                this.isOnsale = !this.isOnsale;
                System.out.println("now on sale stat = " + this.isOnsale);
                Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "已经上架/下架！", Toast.LENGTH_SHORT).show();
            refreshStat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendStatRequest(String type) {
        String url = DataHolder.base_URL + DataHolder.postChangeCouponState_URL;
        HashMap<String, String> map = new HashMap<>();
        map.put("couponID", coupon.getCouponid());
        map.put("state", type);
        ConnectionManager connectionManager = new ConnectionManager(url, map);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
                parseMessage(response);
            }

            @Override
            public void onConnectionTimeOut() {

            }

            @Override
            public void onConnectionFailed() {

            }
        });
        connectionManager.connect();
    }
}
