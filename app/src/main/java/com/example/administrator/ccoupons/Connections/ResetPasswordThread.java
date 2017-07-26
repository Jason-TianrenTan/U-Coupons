package com.example.administrator.ccoupons.Connections;

import android.content.Context;
import android.os.Handler;

import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.Tools.PasswordEncoder;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public class ResetPasswordThread extends Thread{
    private UHuiConnection connection;
    private String password;
    private String url;
    private String phoneString;
    private Context mContext;
    private Handler handler;

    public ResetPasswordThread(String url, String phoneString, String pass,Handler handler, Context context) {
        this.url = url;
        try {
            this.password = new PasswordEncoder().EncodeByMd5(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.phoneString = phoneString;
        this.mContext = context;
        this.handler = handler;

    }

    public String getResponse() {
        return connection.getContent();
    }

    private void connect(String url){
        try {
            connection = new UHuiConnection(url, handler);
            connection.setHeader("User-Agent", USER_AGENT);
            connection.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            connection.add("phoneNum", phoneString);
            connection.add("password", password);
            connection.connect();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void run(){
        connect(this.url);
    }
}
