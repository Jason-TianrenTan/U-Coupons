package com.example.administrator.ccoupons.Main;

import com.example.administrator.ccoupons.MyApp;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class Coupon implements Serializable {

    private static final int STAT_ONSALE = 1;
    private static final int STAT_EXPIRED = 2;
    private static final int STAT_USED = 3;
    private static final int STAT_STORE = 4;
    private static final int LIKED = 1;
    private static final int UNLIKED = 0;

    private boolean liked = false;
    private String address;//地址
    private String name;//=>product 优惠券名字
    private String couponId;
    private String brandName;//品牌id ->可口可乐、吮指原味鸡->肯德基
    private String catId;//类别
    private double listPrice;//用户列出来的价格
    private double evaluatePrice;//估值价格 =>value
    private String discount;//打折多少 20表示20元
    private int stat;//状态 详见下
    private String sellerId;//卖家id
    private String imgURL;//url
    private String expireDate;//过期时间
    private String[] constraints;//限制

    private String sellerNickname;//卖家名字
    private String sellerAvatarURL;//头像URL

    /*
    状态:
    onSale => 正在卖
    expired =>过期的
    used  =>用过的
    store =>储存的
     */
    public Coupon() {

    }

    public Coupon(String name, String couponId, String brand, String catId, double listPrice, double evaluatePrice, String discount, int stat, String imgURL, String expireDate) {
        this.name = name;
        this.couponId = couponId;
        this.brandName = brand;
        this.catId = catId;
        this.listPrice = listPrice;
        this.evaluatePrice = evaluatePrice;
        this.discount = discount;
        this.stat = stat;
        this.imgURL = imgURL;
        this.expireDate = expireDate;
    }

    //名称
    public void setName(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    //地址
    public void setAddress(String str) {
        this.address = str;
    }

    public String getAddress() {
        return this.address;
    }

    //商家信息
    public void setBrandName(String str) {
        this.brandName = str;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public String getSellerId() { return this.sellerId; }

    //卖家昵称
    public void setSellerName(String str) {
        this.sellerNickname = str;
    }

    public String getSellerNickname() {
        return this.sellerNickname;
    }

    public void setSellerAvatarURL(String url) {
        this.sellerAvatarURL = url;
    }

    public String getSellerAvatarURL() {
        return this.sellerAvatarURL;
    }

    //是否关注
    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isLiked() {
        return this.liked;
    }

    //使用限制
    public void setConstraints(String[] arr) {
        this.constraints = arr;
    }

    public String[] getConstraints() {
        return this.constraints;
    }


    public void setImgURL(String url) {
        this.imgURL = url;
    }

    public void setListPrice(double price) {
        this.listPrice = price;
    }

    public void setEvaluatePrice(double price) {
        this.evaluatePrice = price;
    }


    public double getListPrice() {
        return this.listPrice;
    }

    public double getEvaluatePrice() {
        return this.evaluatePrice;
    }

    public void setCategory(String cat) {
        this.catId = cat;
    }
    public String getCategory() {
        return this.catId;
    }


    public void setCouponId(String id) {
        this.couponId = id;
    }

    public String getCouponId() {
        return this.couponId;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscount() {
        return this.discount;
    }

    public int getStat() {
        return this.stat;
    }

    public String getImgURL() {
        return this.imgURL;
    }

    public void setExpireDate(String date) {
        this.expireDate = date;
    }

    public String getExpireDate() {
        return this.expireDate;
    }


    private void setCouponStat(String statStr) {
        int coupon_stat = -1;
        if (statStr.equals("onSale"))
            coupon_stat = STAT_ONSALE;
        if (statStr.equals("expired"))
            coupon_stat = STAT_EXPIRED;
        if (statStr.equals("used"))
            coupon_stat = STAT_USED;
        if (statStr.equals("store"))
            coupon_stat = STAT_STORE;
    }

    //Todo:改一改
    public static Coupon decodeFromJSON(JSONObject jsonObject) {
        Coupon coupon = new Coupon();
        try {
            coupon.couponId = jsonObject.getString("couponid");
            coupon.listPrice = Double.parseDouble(jsonObject.getString("listprice"));
            coupon.evaluatePrice = Double.parseDouble(jsonObject.getString("value"));
            coupon.name = jsonObject.getString("product");
            coupon.discount = jsonObject.getString("discount");
            coupon.expireDate = jsonObject.getString("expiredtime");
            coupon.imgURL = jsonObject.getString("pic");
        } catch (Exception e) {
            System.out.println("Error when decoding coupon json");
            e.printStackTrace();
        }
        //name price detail expire_date

        return coupon;
    }

    //二次解析
    public void getDetails(String str) {
        try {
            JSONObject mainObj = new JSONObject(str);

            //brand
            JSONObject brandObj = mainObj.getJSONArray("brand").getJSONObject(0);
            this.brandName = brandObj.getString("name");
            this.address = brandObj.getString("address");

            //limit
            JSONArray limitArray = mainObj.getJSONArray("limit");
            String[] constraintList = new String[limitArray.length()];
            for (int i = 0; i < limitArray.length(); i++) {
                JSONObject contentObj = limitArray.getJSONObject(i);
                String content = contentObj.getString("content");
                constraintList[i] = content;
            }


            this.constraints = constraintList;

            //关注
            String likeStr = mainObj.getString("isLike");
            this.liked = false;
            if (likeStr.equals("1")) {
                System.out.println(this.name + " is liked");
                this.liked = true;
            }

            //seller 卖家
            JSONObject sellerObj = mainObj.getJSONArray("seller").getJSONObject(0);
            this.sellerNickname = sellerObj.getString("nickname");
            this.sellerAvatarURL = sellerObj.getString("avatar");
            this.sellerId = sellerObj.getString("id");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*{"brand": [{"name": "\u80af\u5fb7\u57fa",
    "address": "\u7fa4\u5149"}],
    "limit": [{"content": "\u53ea\u9650\u7fa4\u5149\u4f7f\u7528"},
     {"content": "\u6bcf\u4e2a\u5ba2\u6237\u4f7f\u7528\u4e00\u4e00\u5f20"},
     {"content": "\u6ee140\u5143\u53ef\u4f7f\u7528"}], "seller": [{"nickname": "\u5988\u5356\u6279\u54e6", "avatar": null}]}
      */

    public JSONObject generateJSON(String userID) {
        JSONObject json = new JSONObject();
        try {
            json.put("userID", userID);
            json.put("brand", brandName);
            json.put("category", catId);
            json.put("expiredTime", expireDate);
            json.put("listPrice", listPrice);
            json.put("product", name);
            json.put("discount", discount);
            //json.put("stat", stat);
            JSONArray jsonArray = new JSONArray();
            for (String str : constraints){
                jsonArray.put(new JSONObject().put("content", str));
            }
            json.put("limit", jsonArray);
            //Todo:图片
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static Coupon decodeFromQRJSON (String jsonString) {
        Coupon coupon = new Coupon();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            coupon.name = jsonObject.getString("product");
            coupon.brandName = jsonObject.getString("brand");
            coupon.catId = jsonObject.getString("category");
            JSONArray limitArray = jsonObject.getJSONArray("limit");
            String[] constraintList = new String[limitArray.length()];
            for (int i = 0; i < limitArray.length(); i++) {
                JSONObject contentObj = limitArray.getJSONObject(i);
                String content = contentObj.getString("content");
                constraintList[i] = content;
            }
            coupon.constraints = constraintList;
            coupon.discount = jsonObject.getString("discount");
            coupon.expireDate = jsonObject.getString("expiredTime");
        } catch (Exception e) {
            System.out.println("Error when decoding coupon json");
            e.printStackTrace();
        }
        return coupon;
    }
}
