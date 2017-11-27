package com.example.administrator.ccoupons.Fragments.Message;

<<<<<<< HEAD:app/src/main/java/com/example/administrator/ccoupons/Fragments/Message.java
=======
import android.content.Context;

import com.example.administrator.ccoupons.Data.GlobalConfig;
>>>>>>> ttr:app/src/main/java/com/example/administrator/ccoupons/Fragments/Message/Message.java
import com.example.administrator.ccoupons.Main.Coupon;

import org.json.JSONObject;

<<<<<<< HEAD:app/src/main/java/com/example/administrator/ccoupons/Fragments/Message.java
=======
import java.io.Serializable;
import java.util.ArrayList;

>>>>>>> ttr:app/src/main/java/com/example/administrator/ccoupons/Fragments/Message/Message.java
/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class Message {

<<<<<<< HEAD:app/src/main/java/com/example/administrator/ccoupons/Fragments/Message.java
    private boolean isNew;
    private String couponName, couponURL;
=======

    private boolean hasRead = false;
>>>>>>> ttr:app/src/main/java/com/example/administrator/ccoupons/Fragments/Message/Message.java
    public int resId;//测试
    private Coupon coupon;
    private int messageCat;
    private int messageId;
    private int userId;
<<<<<<< HEAD:app/src/main/java/com/example/administrator/ccoupons/Fragments/Message.java
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
=======
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
>>>>>>> ttr:app/src/main/java/com/example/administrator/ccoupons/Fragments/Message/Message.java
    }

    public String getCouponURL() {
        return this.couponURL;
    }

<<<<<<< HEAD:app/src/main/java/com/example/administrator/ccoupons/Fragments/Message.java
    public String getTime() {
        return this.time;
    }

<<<<<<< HEAD
=======
=======
    public void setMessageCat(int i) {
        this.messageCat = i;
    }

>>>>>>> Czj
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
>>>>>>> ttr:app/src/main/java/com/example/administrator/ccoupons/Fragments/Message/Message.java

}
