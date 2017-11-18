package com.example.administrator.ccoupons.User.CouponDetail;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.User.UserCoupons.CouponModifiedEvent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 属于我的优惠券
 */
public class MyCouponDetailActivity extends BaseDetailActivity {


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
        statButton = (ImageView)bottomView.findViewById(R.id.page_button_stat);
        refreshStat();

        statButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnsale)
                    sendStatRequest("store");
                else sendStatRequest("onSale");
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
            if (result.equals("0")) { //状态修改成功
                Toast.makeText(getApplicationContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                isOnsale = !isOnsale;
            }
            else {
                if (result.equals("1"))
                    Toast.makeText(getApplicationContext(), "遇到了错误，请刷新后重试", Toast.LENGTH_SHORT).show();
            }
            refreshStat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static final String putOnSaleURL = "/post_putOnSale",
                                putOffSaleURL = "/post_putOffSale";
    private void sendStatRequest(String type) {
        String url;
        if (type.equals("store"))
            url = GlobalConfig.base_URL + this.putOffSaleURL;
        else url = GlobalConfig.base_URL + this.putOnSaleURL;
        System.out.println("url = " + url);
        HashMap<String, String> map = new HashMap<>();
        map.put("couponID", coupon.getCouponid());
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
