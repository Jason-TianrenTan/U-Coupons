package com.example.administrator.ccoupons;

/**
 * Created by Administrator on 2017/7/18 0018.
 */
import android.app.Application;

public class MyApp extends Application {

    private static MyApp instance = null;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId = id;
    }
}
