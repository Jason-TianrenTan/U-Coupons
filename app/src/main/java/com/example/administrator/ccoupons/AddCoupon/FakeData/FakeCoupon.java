package com.example.administrator.ccoupons.AddCoupon.FakeData;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class FakeCoupon {

    private String category;
    private String name;
    private String dicount;
    private String price;
    private String brand;
    private String img_url;

    public String getImg_url() { return img_url; }

    public void setImg_url(String str) { this.img_url = str; }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDicount() {
        return dicount;
    }

    public void setDicount(String dicount) {
        this.dicount = dicount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
