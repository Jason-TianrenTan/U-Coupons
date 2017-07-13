package com.example.administrator.ccoupons;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class Category {
    private String name;
    private int resId;
    public Category(String cname,int cid) {
        this.name = cname;
        this.resId = cid;
    }
    public String getName() {
        return this.name;
    }
    public int getResId() {
        return this.resId;
    }
}
