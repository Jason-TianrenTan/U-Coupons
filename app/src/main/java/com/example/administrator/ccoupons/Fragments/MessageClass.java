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

    public MessageClass(String clsName) {
        this.ClassName = clsName;
        this.resId = R.drawable.message_icon;
        this.messageList = new ArrayList<>();
    }

    public void add(Message msg) {
        this.messageList.add(msg);
    }

    public void setMessageList(ArrayList<Message> list) {
        this.messageList = list;
    }

    public String getClassName() {
        return this.ClassName;
    }
    public ArrayList<Message> getMessages() {
        return this.messageList;
    }
    public String getSubtitle() {
        String ret = "无消息哟";
        for (Message msg:messageList) {
            ret = msg.getCouponName();
        }
        return ret;
    }

    public String getTime() {
        String ret = " ";
        for (Message msg:messageList) {
            ret = msg.getTime();
        }
        return ret;
    }
    public int getResId() {
        return this.resId;
    }
}
