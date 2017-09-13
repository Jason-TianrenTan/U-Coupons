package com.example.administrator.ccoupons;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.example.administrator.ccoupons.Fragments.Message.Message;
import com.mob.MobApplication;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class MyApp extends MobApplication {


    private static MyApp instance = null;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        Ucoin = 0;
        nickname = "default_str";
        avatar = "";
        gender = Gender.MALE;
    }


    private ArrayList<Message> messageList;

    private String location;
    private String userId;
    private int Ucoin;
    private String nickname;
    private String avatar;
    private String phoneNumber;
    private int gender;

    public ArrayList<Message> getMessageList() {
        return this.messageList;
    }

    public void setMessageList(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId = id;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String str) {
        this.nickname = str;
    }

    public int getUcoin() {
        return this.Ucoin;
    }

    public void setUcoin(int ucoin) {
        this.Ucoin = ucoin;
    }

    public void setAvatar(String url) {
        this.avatar = url;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setLocation(String loc) {
        this.location = loc;
    }
    public String getLocation() {
        return this.location;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }



}
