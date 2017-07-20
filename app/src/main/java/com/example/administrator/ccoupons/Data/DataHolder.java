package com.example.administrator.ccoupons.Data;

import com.example.administrator.ccoupons.R;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class DataHolder {
    public static final String base_URL = "http://192.168.207.221";
    public static final String login_URL = ":8000/post_loginForAndroid";
    public static final String register_URL = ":8000/post_signUpForAndroid";
    public static final String requestMsg_URL = ":8000/post_sendMessage";
    public static final String requestSearch_URL = ":8000/post_searchForAndroid";

    public static class Banners {
        public static String[] nameList = "Coupon1,Coupon2,Coupon3,Coupon4,Coupon5,Coupon6,Coupon7".split(",");
        public static String[] detailList = "Title1,Title2,Title3,Title4,Title5,Title6,Title7".split(",");
        public static int[] covers = {R.mipmap.ic_cover_1, R.mipmap.ic_cover_2, R.mipmap.ic_cover_3, R.mipmap.ic_cover_4,
                R.mipmap.ic_cover_5, R.mipmap.ic_cover_6, R.mipmap.ic_cover_7};
    }


    public static class Categories {
        public static String[] nameList = "类别1 类别2 类别3 类别4 类别5 类别6 类别7 类别8 类别9".split(" ");
        public static int[] covers = {R.drawable.category1, R.drawable.category2, R.drawable.category3, R.drawable.category4,
                R.drawable.category5, R.drawable.category6, R.drawable.category7, R.drawable.category8, R.drawable.category9};
    }

    public static class History {
        public static String requestData(int index) {
            return historyList[index];
        }

        public static String[] historyList = ("history1,h2,str3,a4,c5,v6,k7,s8,o9,i10,g234").split(",");
    }

    //用户信息
    public static class User {
        public static String username = "用户名";
        public static boolean sex = true;//TODO:要改成gender那样的
        public static int age = 18;
        public static int portraitId = R.drawable.testportrait;
        public static int UB = 100;
    }

    public static class MessageClasses {
        public static int[] strings = {R.string.coupon_bought_title, R.string.coupon_abouttoexpire_title,
                R.string.coupon_expired_title, R.string.coupon_followed_abouttoexpire_title, R.string.coupon_mine_aboutto_expire_title,
                R.string.coupon_system_title};
    }
}
