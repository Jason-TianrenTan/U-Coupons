package com.example.administrator.ccoupons.Main;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class Coupon {

    private static final int STAT_ONSALE = 1;
    private static final int STAT_EXPIRED = 2;
    private static final int STAT_USED = 3;
    private static final int STAT_STORE = 4;


    private String name;//=>product
    private int couponId;
    private int brandId;//品牌id
    private int catId;//类别
    private double listPrice;//用户列出来的价格
    private double evaluatePrice;//估值价格 =>value
    private double discount;//打折多少 20表示20元
    private int stat;//状态 详见下
    private String imgURL;//url
    private String expireDate;//过期时间

    /*
    状态:
    onSale => 正在卖
    expired =>过期的
    used  =>用过的
    store =>储存的
     */
    public Coupon() {

    }

    public void setName(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public void setListPrice(double price) {
        this.listPrice = price;
    }

    public void setEvaluatePrice(double price) {
        this.evaluatePrice = price;
    }

    public double getListPrice() {
        return this.listPrice;
    }

    public double getEvaluatePrice() {
        return this.evaluatePrice;
    }

    public int getCategory() {
        return this.catId;
    }

    public int getBrand() {
        return this.brandId;
    }

    public int getCouponId() {
        return this.couponId;
    }

    public double getDiscount() {
        return this.discount;
    }

    public int getStat() {
        return this.stat;
    }

    public String getImgURL() {
        return this.imgURL;
    }

    public String getExpireDate() {
        return this.expireDate;
    }


    public static Coupon decodeFromJSON(JSONObject jsonObject) {
        Coupon coupon = new Coupon();
        try {
            coupon.couponId = Integer.parseInt(jsonObject.getString("couponid"));
            coupon.brandId = Integer.parseInt(jsonObject.getString("brandid_id"));
            coupon.catId = Integer.parseInt(jsonObject.getString("catid_id"));
            coupon.listPrice = Double.parseDouble(jsonObject.getString("listprice"));
            coupon.evaluatePrice = Double.parseDouble(jsonObject.getString("value"));
            coupon.name = jsonObject.getString("product");
            coupon.discount = Double.parseDouble(jsonObject.getString("discount"));

            String statStr = jsonObject.getString("stat");
            int coupon_stat = -1;
            if (statStr.equals("onSale"))
                coupon_stat = STAT_ONSALE;
            if (statStr.equals("expired"))
                coupon_stat = STAT_EXPIRED;
            if (statStr.equals("used"))
                coupon_stat = STAT_USED;
            if (statStr.equals("store"))
                coupon_stat = STAT_STORE;

            coupon.stat = coupon_stat;
            coupon.imgURL = jsonObject.getString("pic");
            coupon.expireDate = jsonObject.getString("expiredtime");

        } catch (Exception e) {
            System.out.println("Error when decoding coupon json");
            e.printStackTrace();
        }
        //name price detail expire_date


        return coupon;
    }
}
