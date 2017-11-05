package com.example.administrator.ccoupons.Fragments.Message;

import android.content.Context;

import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Main.Coupon;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class Message implements Serializable {


    private boolean hasRead = false;
    public int resId;//测试
    private int messageCat;
    private String messageId;
    private int userId;
    private Coupon coupon;
    private String content;

    public Message() {
        coupon = new Coupon();
    }


    //content
    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }
    //value
    public void setValue(String value) {
        this.coupon.setValue(value);
    }

    public String getValue() {
        return this.coupon.getValue();
    }
    //discount for coupon
    public void setDiscount(String discount) {
        this.coupon.setDiscount(discount);
    }

    public String getDiscount() {
        return this.getDiscount();
    }

    public void setCouponPrice(String price) {
        coupon.setListprice(price);
    }

    public void setCouponURL(String url) {
        this.coupon.setPic(url);
    }

    //if is read
    public boolean hasRead() {
        return this.hasRead;
    }

    public void setRead() {
        this.hasRead = true;
    }


    //expired time
    public void setExpireTime(String time) {
        this.coupon.setExpiredtime(time);
    }

    public String getExpireTime() {
        return this.coupon.getExpiredtime();
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

    public void setMessageCat(int i) {
        this.messageCat = i;
    }

    public String getCouponid() {
        return this.coupon.getCouponid();
    }

    public Coupon getCoupon() {
        return this.coupon;
    }


    /**
     *
     * @param context
     * @param obj JSON object to decode
     * @return
     */
    public static Message decodeFromJSON(Context context, JSONObject obj) {
        Message message = new Message();
        try {
            message.messageId = obj.getString("messageid");
            String cat_str = obj.getString("messagecat");
            for (int i = 0; i < GlobalConfig.MessageClasses.strings.length; i++) {
                String str = context.getResources().getString(GlobalConfig.MessageClasses.strings[i]);
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
