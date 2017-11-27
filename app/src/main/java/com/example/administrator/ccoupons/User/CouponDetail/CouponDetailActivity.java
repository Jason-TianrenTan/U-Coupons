package com.example.administrator.ccoupons.User.CouponDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.Purchase.CouponPurchaseActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.User.UserCoupons.CouponModifiedEvent;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 不属于我的优惠券
 */
public class CouponDetailActivity extends BaseDetailActivity {


    ImageView mainButton, followButton, purchaseButton;
    boolean _isLiked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onKeyBack() {
        EventBus.getDefault().post(new CouponModifiedEvent());
    }


    @Override
    public void initBottomViews(boolean isLiked) {
        this._isLiked = isLiked;
        System.out.println("is like == " + isLiked);
        super.inflateBottomView(R.layout.purchase_bottom_bar);

        mainButton = (ImageView) bottomView.findViewById(R.id.page_button_mainpage);
        followButton = (ImageView) bottomView.findViewById(R.id.page_button_follow);
        purchaseButton = (ImageView) bottomView.findViewById(R.id.page_button_purchase);

        if (isLiked)
            followButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.follow_pressed));
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CouponDetailActivity.this, MainPageActivity.class));
                finish();
            }
        });


        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关注
                HashMap<String, String> map = new HashMap<String, String>();
                MyApp app = (MyApp) getApplicationContext();
                map.put("couponID", coupon.getCouponid());
                map.put("userID", app.getUserId());
                String url = GlobalConfig.base_URL + GlobalConfig.postUnFollow_URL;
                if (!_isLiked)
                    url = GlobalConfig.base_URL + GlobalConfig.postFollow_URL; //没关注就关注
                ConnectionManager connectionManager = new ConnectionManager(url, map);
                connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
                    @Override
                    public void onConnectionSuccess(String response) {
                        System.out.println("Response = " + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");
                            if (result.equals("200")) { //连接成功
                                if (_isLiked) {//已经关注，取消关注
                                    Toast.makeText(getApplicationContext(), "取消关注", Toast.LENGTH_SHORT).show();
                                    followButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.follow));
                                    _isLiked = false;
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "关注成功", Toast.LENGTH_SHORT).show();
                                    followButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.follow_pressed));
                                    _isLiked = true;
                                }
                            }
                            else {
                                if (result.equals("115"))
                                    Toast.makeText(getApplicationContext(), "已经关注过该优惠券啦！", Toast.LENGTH_SHORT).show();
                                if (result.equals("116"))
                                    Toast.makeText(getApplicationContext(), "还没有关注这张优惠券哦", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onConnectionTimeOut() {
                        Toast.makeText(getApplicationContext(), "连接服务器超时，请稍后重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onConnectionFailed() {
                        Toast.makeText(getApplicationContext(), "连接服务器超时，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                });
                connectionManager.connect();
            }
        });

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = coupon.getCouponid();
                Intent intent = new Intent(CouponDetailActivity.this, CouponPurchaseActivity.class);
                intent.putExtra("coupon", coupon);
                startActivity(intent);
            }
        });
    }
}