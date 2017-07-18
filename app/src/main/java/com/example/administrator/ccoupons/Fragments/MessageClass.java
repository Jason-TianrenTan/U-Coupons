package com.example.administrator.ccoupons.Fragments;

import com.example.administrator.ccoupons.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

//保存消息类别的类
    /*
    分为
        上架的优惠券被购买
        上架的优惠券即将过期
        上架的优惠券已过期
        关注的优惠券即将过期
        我的优惠券即将过期
        系统通知
     */
public class MessageClass {

    private String ClassName;
    private ArrayList<Message> messageList;
    private int resId;//测试

    public MessageClass(String clsName, ArrayList<Message> msg) {
        this.ClassName = clsName;
        this.messageList = msg;
        this.resId = R.drawable.message_icon;
    }
    public String getClassName() {
        return this.ClassName;
    }
    public ArrayList<Message> getMessages() {
        return this.messageList;
    }
    public String getSubtitle() {
        String ret = "反正你嫖不到的哟~";
        for (Message msg:messageList) {
            ret = msg.getCouponName();
        }
        return ret;
    }

    public String getTime() {
        String ret = "嫖不到了还在嫖";
        for (Message msg:messageList) {
            ret = msg.getTime();
        }
        return ret;
    }
    public int getResId() {
        return this.resId;
    }
}
