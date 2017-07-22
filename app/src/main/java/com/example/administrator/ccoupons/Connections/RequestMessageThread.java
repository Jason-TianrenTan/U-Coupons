package com.example.administrator.ccoupons.Connections;

import android.content.Context;
import android.os.Handler;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.MyApp;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class RequestMessageThread extends Thread {

    private UHuiConnection connection;
    private static String Request_URL = DataHolder.base_URL + DataHolder.requestMsg_URL;//TODO:请求url
    private String userId;
    public RequestMessageThread(Handler handler, Context context) {
        connection = new UHuiConnection(Request_URL, handler);
        MyApp app = (MyApp)context;
        userId = app.getUserId();
        System.out.println("User Id = " + userId);
    //    userId = "1500435256jagt";
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
            System.out.println("Connecting " + Request_URL);
            connection.setHeader("User-Agent", USER_AGENT);
            connection.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            connection.add("userID", userId);//名字暂定
            connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
