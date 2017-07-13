package com.example.administrator.ccoupons.Main;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class Coupon {
    private String name;
    private double price;
    private String detail;
    private int resId;//temporary
    public Coupon(String sName,double Iprice,String sDetail,int id) {
        this.name = sName;
        this.price = Iprice;
        this.detail = sDetail;
        this.resId = id;
    }
    public void setResId(int id) {
        this.resId = id;
    }
    public void setName(String str) {
        this.name = str;
    }
    public void setPrice(int p) {
        this.price = p;
    }
    public void setDetail(String det) {
        this.detail = det;
    }
    public String getName() {
        return this.name;
    }
    public double getPrice() {
        return this.price;
    }
    public String getDetail() {
        return this.detail;
    }
    public int getResId() {
        return this.resId;
    }
}
