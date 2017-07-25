package com.example.administrator.ccoupons.Connections;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

import android.content.Context;
import android.os.Handler;

import com.example.administrator.ccoupons.Tools.PasswordEncoder;

import static org.apache.http.protocol.HTTP.USER_AGENT;
public class RegisterThread extends Thread{

    private UHuiConnection connection;
    private String username, password, nickname;
    private int gender;
    private String url;
    private Context mContext;
    private Handler handler;
    String[] GenderChars = {"男", "女"};

    public RegisterThread(String url, String name, String pass, String nick, int gender, Handler handler, Context context) {
        this.url = url;
        this.username = name;
        try {
            this.password = new PasswordEncoder().EncodeByMd5(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.nickname = nick;
        this.gender = gender;
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
            connection.add("username", username);
            connection.add("password", password);
            connection.add("nickname", nickname);
            connection.add("gender", GenderChars[gender]);
            connection.connect();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void run(){
        connect(this.url);
    }
}

