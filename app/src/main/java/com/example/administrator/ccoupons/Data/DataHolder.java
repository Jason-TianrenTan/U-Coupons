package com.example.administrator.ccoupons.Data;

import com.example.administrator.ccoupons.R;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class DataHolder {
    public static class Banners {
        public static String[] nameList = "Coupon1,Coupon2,Coupon3,Coupon4,Coupon5,Coupon6,Coupon7".split(",");
        public static String[] detailList = "Title1,Title2,Title3,Title4,Title5,Title6,Title7".split(",");
        public static int[] covers = {R.mipmap.ic_cover_1, R.mipmap.ic_cover_2, R.mipmap.ic_cover_3, R.mipmap.ic_cover_4,
                R.mipmap.ic_cover_5, R.mipmap.ic_cover_6, R.mipmap.ic_cover_7};
    }

    public static class Coupons {
        public static double[] priceList = {119.99,233.3,399.99,499.99,566.66,666.66,789,899.99,1999.99,2999.9,5000.00,1246.00,6668,8888,10000};
        public static String[] nameList= ("Naruto Shippuden,Dota2,League of Legends,Diablo III,World of Warcraft," +
                "Tokyo Ghoul,Starcraft II,C++,C#,Java,Python,PHP,iOS,Android").split(",");
        public static String[] detailList = ("火影忍者疾风传,刀塔2,英雄联盟,暗黑破坏神3,魔兽世界,东京喰种,星际争霸II," +
                ".cpp,.cs,.java,.py,.php,.swift,.apk").split(",");
        public static int[] resIds = {R.mipmap.naruto,R.mipmap.dota2,R.mipmap.lol,R.mipmap.diablo3,R.mipmap.wow,R.mipmap.tokyoghoul,
            R.mipmap.sc2,R.mipmap.cpp,R.mipmap.csharp,R.mipmap.java,R.mipmap.python,R.mipmap.php,R.mipmap.apple,R.mipmap.android};
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
        public static String phonenumber = "1234567890";
        public static boolean sex = true;//true for man false for woman
        public static int age = 18;
        public static int portraitId = R.drawable.testportrait;
        public static int UB = 100;
    }
}
