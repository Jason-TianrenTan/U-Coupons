package com.example.administrator.ccoupons.Things;

/**
 * Created by CZJ on 2017/11/15.
 */

public class ThingsCategory  {

    private String name;
    private int resId;


    /**
     *
     * @param cname category name
     * @param cid category id
     */
    public ThingsCategory(String cname,int cid) {
        this.name = cname;
        this.resId = cid;
    }

    public String getProduct() {
        return this.name;
    }
    public int getResId() {
        return this.resId;
    }
}
