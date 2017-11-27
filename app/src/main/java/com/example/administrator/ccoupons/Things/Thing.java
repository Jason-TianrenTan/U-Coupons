package com.example.administrator.ccoupons.Things;

import com.example.administrator.ccoupons.Data.GlobalConfig;

/**
 * Created by CZJ on 2017/11/16.
 */

public class Thing {
    private int imgId;
    private String name;
    private String brand;
    private int category;
    private double prize;
    private int number;

    public Thing(int imgId, String name, String brand, int category, double prize, int number) {
        this.imgId = imgId;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.prize = prize;
        this.number = number;
    }

    public int getImgId() {
        return imgId;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return GlobalConfig.ThingsCategories.thingsList[category];
    }

    public double getPrize() {
        return prize;
    }

    public int getNumber() {
        return number;
    }
}
