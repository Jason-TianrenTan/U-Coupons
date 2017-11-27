package com.example.administrator.ccoupons.Banner;

/**
 * Created by Administrator on 2017/7/15 0015.
 */

public class BannerPicture {
    private String name;
    private int resId;


    /**
     *
     * @param sname name for picture
     * @param id resource id
     */
    public BannerPicture(String sname, int id) {
        name = sname;
        resId = id;
    }

    public int getResId() {
        return this.resId;
    }

    public String getProduct() {
        return this.name;
    }
}
