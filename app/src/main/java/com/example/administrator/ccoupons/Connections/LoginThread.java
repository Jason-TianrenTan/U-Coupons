package com.example.administrator.ccoupons.Connections;

import android.content.Context;
import android.os.Handler;

<<<<<<< HEAD:app/src/main/java/com/example/administrator/ccoupons/Connections/LoginThread.java
import com.example.administrator.ccoupons.Tools.PasswordEncoder;
=======
import com.example.administrator.ccoupons.MyApp;
>>>>>>> ttr:app/src/main/java/com/example/administrator/ccoupons/Connections/PurchaseThread.java

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class LoginThread extends Thread {

    private UHuiConnection connection;
    private String username, password;
    private String url;
    private Handler handler;
    private Context mContext;
<<<<<<< HEAD:app/src/main/java/com/example/administrator/ccoupons/Connections/LoginThread.java
    public LoginThread(String url, String name, String pass,Handler handler,Context context) {
=======
    private String userId;

    /**
     *
     * @param url connection target URL
     * @param couponId ID for coupon
     * @param mContext context
     * @param handler handler
     */
    public PurchaseThread(String url, String couponId, Context mContext, Handler handler) {
>>>>>>> ttr:app/src/main/java/com/example/administrator/ccoupons/Connections/PurchaseThread.java
        this.url = url;
        this.username = name;
        this.mContext = context;
        this.handler = handler;
        try {
            this.password = new PasswordEncoder().EncodeByMd5(pass);
        } catch (Exception e) {
            Toast.makeText(mContext, "遇到未知错误(MD5),我也很绝望", Toast.LENGTH_SHORT).show();
        }
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
            connection.add("username", username);
            connection.add("password", password);
            connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        connect(this.url);
    }

}