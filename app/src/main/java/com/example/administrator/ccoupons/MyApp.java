package com.example.administrator.ccoupons;

<<<<<<< HEAD
/**
 * Created by Administrator on 2017/7/18 0018.
 */
=======
>>>>>>> ttr
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

<<<<<<< HEAD
import com.mob.MobApplication;

public class MyApp extends MobApplication {

=======
import com.example.administrator.ccoupons.Fragments.Message.Message;
import com.mob.MobApplication;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class MyApp extends MobApplication {


>>>>>>> ttr
    private static MyApp instance = null;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
<<<<<<< HEAD

        instance = this;
=======
        Ucoin = 0;
        nickname = "default_str";
        avatar = "";
        gender = Gender.MALE;
>>>>>>> ttr
    }

    private String userId;
<<<<<<< HEAD
=======
    private int Ucoin;
    private String nickname;
    private String avatar;
    private String phoneNumber;
    private int gender;


    //Message list
    public ArrayList<Message> getMessageList() {
        return this.messageList;
    }

    public void setMessageList(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }


    //Gender
    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
>>>>>>> ttr


    //User ID
    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId = id;
    }
<<<<<<< HEAD
=======


    //User nickname
    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String str) {
        this.nickname = str;
    }


    //User remaining Ucoins
    public int getUcoin() {
        return this.Ucoin;
    }

    public void setUcoin(int ucoin) {
        this.Ucoin = ucoin;
    }


    //User avatar
    public void setAvatar(String url) {
        this.avatar = url;
    }

    public String getAvatar() {
        return this.avatar;
    }


    //User phonenumber
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }


    //User location
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



>>>>>>> ttr
}
