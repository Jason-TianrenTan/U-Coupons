package com.example.administrator.ccoupons.Fragments.Message;

import com.example.administrator.ccoupons.Fragments.Message.Message;
import com.example.administrator.ccoupons.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class MessageClass {

    private static int[] resIds = {R.drawable.ic_coupon_bought, R.drawable.ic_coupon_abouttoexpire, R.drawable.ic_coupon_expired,
                                R.drawable.ic_coupon_followed_abouttoexpire, R.drawable.ic_coupon_mine_aboutto_expire,
                                R.drawable.ic_coupon_system};
    private String ClassName;
    private ArrayList<Message> messageList;
    private int resId;//测试

    /**
     *
     * @param clsName name for message class
     * @param msg corresponding message list
     */
    public MessageClass(String clsName, ArrayList<Message> msg) {
        this.ClassName = clsName;
        this.messageList = msg;
        this.resId = R.drawable.message_icon;
    }
<<<<<<< HEAD:app/src/main/java/com/example/administrator/ccoupons/Fragments/MessageClass.java
=======


    /**
     *
     * @param clsName name of message class
     */
    public MessageClass(String clsName, int index) {
        this.ClassName = clsName;
        this.resId = resIds[index];
        this.messageList = new ArrayList<>();
    }


    /**
     * add message to list
     * @param msg
     */
    public void add(Message msg) {
        this.messageList.add(msg);
    }


    /**
     * set message list
     * @param list
     */
    public void setMessageList(ArrayList<Message> list) {
        this.messageList = list;
    }


>>>>>>> ttr:app/src/main/java/com/example/administrator/ccoupons/Fragments/Message/MessageClass.java
    public String getClassName() {
        return this.ClassName;
    }


    public ArrayList<Message> getMessages() {
        return this.messageList;
    }


    /**
     * return subtitle of message class
     * @return
     */
    public String getSubtitle() {
<<<<<<< HEAD:app/src/main/java/com/example/administrator/ccoupons/Fragments/MessageClass.java
        String ret = "反正你嫖不到的哟~";
=======
        String ret = "暂无消息";
<<<<<<< HEAD
>>>>>>> ttr:app/src/main/java/com/example/administrator/ccoupons/Fragments/Message/MessageClass.java
        for (Message msg:messageList) {
            ret = msg.getCouponName();
        }
=======
        if (messageList.size() > 0)
            ret = messageList.get(messageList.size() -1).getContent();
>>>>>>> Czj
        return ret;
    }


    /**
     * return expired time of coupon
     * @return
     */
    public String getTime() {
<<<<<<< HEAD
        String ret = "嫖不到了还在嫖";
        for (Message msg:messageList) {
            ret = msg.getExpireTime();
        }
=======
        String ret = "暂无消息";
        if (messageList.size() > 0)
            ret = messageList.get(messageList.size() -1).getExpireTime();
>>>>>>> Czj
        return ret;
    }


    /**
     * return Resource ID
     * @return
     */
    public int getResId() {
        return this.resId;
    }
}
