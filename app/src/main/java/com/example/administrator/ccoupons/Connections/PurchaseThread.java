package com.example.administrator.ccoupons.Connections;

import android.content.Context;
import android.os.Handler;

import com.example.administrator.ccoupons.MyApp;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public class PurchaseThread extends Thread{

    private UHuiConnection connection;
    private String couponId;
    private String url;
    private Handler handler;
    private Context mContext;
    private String userId;

    /**
     *
     * @param url connection target URL
     * @param couponId ID for coupon
     * @param mContext context
     * @param handler handler
     */
    public PurchaseThread(String url, String couponId, Context mContext, Handler handler) {
        this.url = url;
        this.handler = handler;
        this.mContext = mContext;
        this.couponId = couponId;
        MyApp app = (MyApp)mContext.getApplicationContext();
        userId = app.getUserId();
    }


    //return response
    public String getResponse() {
        return connection.getContent();
    }


    /**
     * connect to target URL
     * @param url
     */
    private void connect(String url) {
        try {
            connection = new UHuiConnection(url, handler);
            connection.setHeader("User-Agent", USER_AGENT);
            connection.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            connection.add("couponID", couponId);
            connection.add("userID", userId);
            System.out.println("USERID = " + userId);
            connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        connect(this.url);
    }
}
