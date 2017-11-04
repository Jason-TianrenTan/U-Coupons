package com.example.administrator.ccoupons.Events;

/**
 * Created by Administrator on 2017/11/5 0005.
 */

public class SelectLocationEvent {
    private String location;

    public String getLocation() {
        return this.location;
    }

    public SelectLocationEvent(String loc) {
        this.location = loc;
    }
}
