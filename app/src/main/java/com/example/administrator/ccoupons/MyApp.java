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
<<<<<<< HEAD
import com.mob.MobApplication;

public class MyApp extends MobApplication {

=======
=======
import com.example.administrator.ccoupons.Data.GlobalConfig;
>>>>>>> Czj
import com.example.administrator.ccoupons.Fragments.Message.Message;
import com.example.administrator.ccoupons.Tools.FontUtils.FontUtils;
import com.example.administrator.ccoupons.Tools.MessageUtils.MessageUtil;
import com.mob.MobApplication;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class MyApp extends MobApplication {


>>>>>>> ttr
    private static MyApp instance = null;

    private static final String DROID_ITALIC_FONT = "DroidSerif-Italic.ttf",
                                DROID_REGULAR_FONT = "DroidSerif-Regular.ttf",
                                PINGFANG_FONT = "PingFang Regular.ttf",
                                PINGFANG_LIGHT_FONT = "PingFang Light.ttf";

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
<<<<<<< HEAD
>>>>>>> ttr
    }

=======

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(PINGFANG_FONT)
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        initMessages();
    }


    private void initMessages() {
        messageList = new ArrayList<>();
        for (int i=0;i< GlobalConfig.MessageClasses.strings.length;i++) {
            Message message = MessageUtil.generateMessage(i);
            messageList.add(message);
        }
    }



    private ArrayList<Message> messageList;

    private String location;
>>>>>>> Czj
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
