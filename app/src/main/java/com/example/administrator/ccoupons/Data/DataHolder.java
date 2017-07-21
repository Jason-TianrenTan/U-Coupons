package com.example.administrator.ccoupons.Data;

import com.example.administrator.ccoupons.R;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class DataHolder {

    public static final String SID = "ACd40e7f8fa4c7538dbf15d38354f096ec";
    public static final String TOKEN = "4f6f13d3a03bccff6aa95333ed2389b5";

    static final String localBase = "http://192.168.207.221:8000";
    static final String teamBase = "http://192.168.204.83:1080";
    public static final String base_URL = localBase;
    public static final String login_URL = "/post_loginForAndroid";
    public static final String register_URL = "/post_signUpForAndroid";
    public static final String requestMsg_URL = "/post_sendMessage";
    public static final String requestSearch_URL = "/post_searchForAndroid";

    public static class Banners {
        public static String[] nameList = "Coupon1,Coupon2,Coupon3,Coupon4,Coupon5,Coupon6,Coupon7".split(",");
        public static String[] detailList = "Title1,Title2,Title3,Title4,Title5,Title6,Title7".split(",");
        public static int[] covers = {R.mipmap.ic_cover_1, R.mipmap.ic_cover_2, R.mipmap.ic_cover_3, R.mipmap.ic_cover_4,
                R.mipmap.ic_cover_5, R.mipmap.ic_cover_6, R.mipmap.ic_cover_7};
    }
    public static class Cities {
        public static String[] cityList = "北京 上海 广州 杭州 成都 苏州 深圳 南京 天津 重庆 厦门 西安".split(" ");
    }

    public static class Categories {
        public static String[] nameList = "生活百货 美妆装饰 文娱体育 家具家居 电子产品 服装装饰 旅行住宿 饮食保健".split(" ");
        public static int[] covers = {R.drawable.category_daily, R.drawable.category_decorate, R.drawable.category_sports, R.drawable.category_furnitures,
                R.drawable.category_electronics, R.drawable.category_cloths, R.drawable.category_travel, R.drawable.category_food};
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
