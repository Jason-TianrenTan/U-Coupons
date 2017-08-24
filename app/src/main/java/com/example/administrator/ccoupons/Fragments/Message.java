package com.example.administrator.ccoupons.Fragments;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class Message implements Serializable {

    private boolean hasRead = false;
    public int resId;//测试
    private int messageCat;
    private String messageId;
    private int userId;
    private String time;
    private String content;
    private Coupon coupon;

    public Message() {
        coupon = new Coupon();
    }


    public void setCouponPrice(String price) {
        coupon.setListprice(price);
    }

    public void setCouponURL(String url) {
        this.coupon.setPic(url);
    }

    public boolean hasRead() {
        return this.hasRead;
    }

    public void setRead() {
        this.hasRead = true;
    }

    public String getTime() {
        return this.time;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setCouponName(String name) {
        this.coupon.setProduct(name);
    }

    public String getCouponName() {
        return this.coupon.getProduct();
    }

    public int getMessageCat() {
        return this.messageCat;
    }

    public String getCouponid() {
        return this.coupon.getCouponid();
    }

    public Coupon getCoupon() {
        return this.coupon;
    }

    /*
    {"messageid": "001", "content": "lalala", "time": "2017-01-01",
        "messagecat": "\u4e0a\u67b6\u7684\u4f18\u60e0\u5238\u88ab\u8d2d\u4e70", "hasread": 0, "couponid": "001"},
    */
    public static Message decodeFromJSON(Context context, JSONObject obj) {
        Message message = new Message();
        try {
            message.messageId = obj.getString("messageid");
            message.content = obj.getString("content");
            message.time = obj.getString("time");

            String cat_str = obj.getString("messagecat");
            for (int i = 0; i < DataHolder.MessageClasses.strings.length; i++) {
                String str = context.getResources().getString(DataHolder.MessageClasses.strings[i]);
                if (cat_str.equals(str)) {
                    message.messageCat = i;
                }

            }

            String read = obj.getString("hasread");
            if (read.equals("0"))
                message.hasRead = false;
            else message.hasRead = true;


            message.coupon.setCouponid(obj.getString("couponid"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}
