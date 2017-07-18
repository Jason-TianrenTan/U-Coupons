package com.example.administrator.ccoupons.Connections;

import android.content.Context;

import com.example.administrator.ccoupons.MyApp;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class RequestMessageThread extends Thread {

    private UHuiConnection connection;
    private static final String Request_URL = "";//TODO:请求url
    private String userId;
    public RequestMessageThread(Context context) {
        connection = new UHuiConnection(Request_URL, null);
        MyApp app = (MyApp)context;
        userId = app.getUserId();
    }

    @Override
    public void run() {
        connect();
    }

    public String getResponse() {
        return connection.getContent();
    }
    private void connect() {
        try {
            connection.setHeader("User-Agent", USER_AGENT);
            connection.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            connection.add("userId", userId);//名字暂定
            connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
