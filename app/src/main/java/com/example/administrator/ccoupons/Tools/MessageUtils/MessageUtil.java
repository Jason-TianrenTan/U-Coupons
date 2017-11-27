package com.example.administrator.ccoupons.Tools.MessageUtils;

import com.example.administrator.ccoupons.Fragments.Message.Message;

/**
 * Created by Administrator on 2017/11/5 0005.
 */

public class MessageUtil {

    static double[] constraints = {5, 10, 3000, 4000, 10000};
    static double[] discounts = {3.99, 4.99, 299.99, 188.00, 0.99};
    static double[] prices = {2.00, 3.00, 190.00, 60.00, 0.01};
    static double[] values = {1.55, 4.00, 175.00, 59.99, 1.00};
    static String[] msgSuffix = "刚刚被购买了 即将过期了! 已经过期了! 即将过期! 即将过期! (勿回复)".split(" "),
            msgPrefix = "您的优惠券 您的优惠券 您的优惠券 您关注的优惠券 您的优惠券 [系统]".split(" ");
    static String[] couponName = "金拱门,肯德基,Google Pixel XL,New Surface Pro,iPhone X".split(",");
    public static Message generateMessage(int index) {
        Message message = new Message();
        if (index < 5) {
            message.setContent(msgPrefix[index] + "\"" + couponName[index] + "\"" + msgSuffix[index]);
            message.setValue(Double.toString(values[index]));
            message.setDiscount("满" + constraints[index] + "减" + discounts[index] + "元");
            message.setCouponPrice(Double.toString(prices[index]));
            message.setExpireTime("2017-11-11");
            message.setCouponName(couponName[index]);
        }
        else {
            message.setContent("双十一购物狂欢节，一起剁手吧！");
            message.setExpireTime("刚刚");
        }
        message.setMessageCat(index);
        return message;
    }
}
