package com.example.administrator.ccoupons.Fragments;

import com.example.administrator.ccoupons.Main.Coupon;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class Message {

    private boolean isNew;
    private String couponName, couponURL;
    public int resId;//测试
    private Coupon coupon;
    private int messageCat;
    private int messageId;
    private int userId;
    private String time;
    private String content;
    public Message(String str) {
        isNew = true;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str);
            couponName = jsonObject.getString("couponName");
            couponURL = jsonObject.getString("couponURL");
            messageCat = Integer.parseInt(jsonObject.getString("messageCat"));
            messageId = Integer.parseInt(jsonObject.getString("messageID"));
            userId = Integer.parseInt(jsonObject.getString("userID"));
            //content = jsonObject.getString("content"); 暂定不用
            time = jsonObject.getString("time");
        } catch (Exception e) {
            System.out.println("Error when decoding message json");
            e.printStackTrace();
        }

    }
    public boolean isNew() {
        return this.isNew;
    }

    public void setRead() {
        this.isNew = false;
    }

    public String getCouponName() {
        return this.couponName;
    }

    public String getCouponURL() {
        return this.couponURL;
    }

    public String getTime() {
        return this.time;
    }


}
