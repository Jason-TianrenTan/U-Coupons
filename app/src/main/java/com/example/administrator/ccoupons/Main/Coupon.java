package com.example.administrator.ccoupons.Main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

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
    //地址
    private String address;

    //类别
    private String catId;
    //品牌
    private String brandName;

    //状态
    private int stat;
    //卖家id
    private String sellerId;


    // 限制
    private String[] constraints;
    //卖家名字
    private String sellerNickname;
    // 头像URL
    private String sellerAvatarURL;

    public Coupon() {

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

    public String getSellerId() {
        return this.sellerId;
    }

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


    public void setCategory(String cat) {
        this.catId = cat;
    }

    public String getCategory() {
        return this.catId;
    }


    public int getStat() {
        return this.stat;
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
            coupon.couponid = jsonObject.getString("couponid");
            coupon.listprice = jsonObject.getString("listprice");
            coupon.value = jsonObject.getString("value");
            coupon.product = jsonObject.getString("product");
            coupon.discount = jsonObject.getString("discount");
            coupon.expiredtime = jsonObject.getString("expiredtime");
            coupon.pic = jsonObject.getString("pic");
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
            json.put("expiredTime", expiredtime);
            json.put("listPrice", listprice);
            json.put("product", product);
            json.put("discount", discount);
            //json.put("stat", stat);
            JSONArray jsonArray = new JSONArray();
            for (String str : constraints) {
                jsonArray.put(new JSONObject().put("content", str));
            }
            json.put("limit", jsonArray);
            //Todo:图片
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static Coupon decodeFromQRJSON(String jsonString) {
        Coupon coupon = new Coupon();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            coupon.product = jsonObject.getString("product");
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
            coupon.expiredtime = jsonObject.getString("expiredTime");
        } catch (Exception e) {
            System.out.println("Error when decoding coupon json");
            e.printStackTrace();
        }
        return coupon;
    }

    public String getWord() {
        String[] group1 = {"限时", "超值", "专享", "活动"};
        String[] group2 = {"优惠", "折扣", "促销", "抢购"};
        String[] group3 = {"心动不如行动", "大家都在抢", "抓紧机会", "编辑推荐"};
        int a = (int) (Math.random() * 4);
        int b = (int) (Math.random() * 4);
        int c = (int) (Math.random() * 4);
        String result = group1[a] + group2[b] + "，" + group3[c] + "！";
        return result;
    }

    private String couponid;
    private String product;
    private String listprice;
    private String value;
    private String expiredtime;
    private String discount;
    private String pic;

    public String getCouponid() {
        return couponid;
    }

    public void setCouponid(String couponid) {
        this.couponid = couponid;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getListprice() {
        return listprice;
    }

    public void setListprice(String listprice) {
        this.listprice = listprice;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpiredtime() {
        return expiredtime;
    }

    public void setExpiredtime(String expiredtime) {
        this.expiredtime = expiredtime;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

}
