package com.example.administrator.ccoupons;

import com.example.administrator.ccoupons.Main.Coupon;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class CouponListEvent {

    private String listname;
    private ArrayList<Coupon> list;

    public CouponListEvent(String name, ArrayList<Coupon> _list) {
        this.listname = name;
        this.list = _list;
    }

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public ArrayList<Coupon> getList() {
        return this.list;
    }
}
