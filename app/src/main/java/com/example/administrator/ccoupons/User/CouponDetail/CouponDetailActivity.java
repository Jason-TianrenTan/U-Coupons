package com.example.administrator.ccoupons.User.CouponDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.Purchase.CouponPurchaseActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.PixelUtils;

import org.json.JSONObject;

import java.util.HashMap;

public class CouponDetailActivity extends BaseDetailActivity {


    ImageView mainButton, followButton, purchaseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void initBottomViews() {
        super.inflateBottomView(R.layout.purchase_bottom_bar);

        mainButton = (ImageView) bottomView.findViewById(R.id.page_button_mainpage);
        followButton = (ImageView) bottomView.findViewById(R.id.page_button_follow);
        purchaseButton = (ImageView) bottomView.findViewById(R.id.page_button_purchase);

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
                ConnectionManager connectionManager = new ConnectionManager(DataHolder.base_URL + DataHolder.postFollow_URL, map);
                connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
                    @Override
                    public void onConnectionSuccess(String response) {
                        System.out.println("Response = " + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String alreadyLike = obj.getString("result");
                            if (alreadyLike.equals("dislike")) {
                                Toast.makeText(getApplicationContext(), "取消关注", Toast.LENGTH_SHORT).show();
                                followButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.follow));
                            } else {
                                Toast.makeText(getApplicationContext(), "关注成功", Toast.LENGTH_SHORT).show();
                                followButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.follow_pressed));
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

    private void inflateBottomStatView() {
        ImageView saleButton, storeButton;
        RelativeLayout rootView = (RelativeLayout) findViewById(R.id.detail_rootview);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.stat_bottom_bar, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PixelUtils.dp2px(this, 45));
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        view.setLayoutParams(params);
        rootView.addView(view);

        saleButton = (ImageView) view.findViewById(R.id.page_button_sale);
        storeButton = (ImageView) view.findViewById(R.id.page_button_store);

        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendStatRequest("onSale");
            }
        });

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendStatRequest("store");
            }
        });

    }


    private void parseMessage(String response) {

        try {
            JSONObject obj = new JSONObject(response);
            String result = obj.getString("errno");
            int code = Integer.parseInt(result);
            if (code == 0) {
                /*
                Intent intent = new Intent("com.example.administrator.ccoupons.UPDATEVIEWS");
                sendBroadcast(intent);*/

                Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "已经上架/下架！", Toast.LENGTH_SHORT).show();
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