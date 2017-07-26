package com.example.administrator.ccoupons;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

import android.app.Application;

import com.mob.MobApplication;

public class MyApp extends MobApplication {
    private String userId;
    private int Ucoin;
    private String nickname;
    private String avatar;
    private int gender;

    private static MyApp instance = null;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Ucoin = 0;
        nickname = "秒切后排的大菜刀";
        avatar = "";
        gender = Gender.MALE;
        instance = this;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
