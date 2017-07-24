package com.example.administrator.ccoupons.Tools.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.ccoupons.Main.Coupon;

/**
 * Created by CZJ on 2017/7/22.
 */

public class CouponsManager {

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    public CouponsManager(Context context) {
        databaseHelper = new DatabaseHelper(context, "Coupon.db", null, 1);
        db = databaseHelper.getWritableDatabase();
    }

    public CouponsManager addCoupon(Coupon coupon) {
        ContentValues values = new ContentValues();
        values.put("id", coupon.getCouponId());
        values.put("name", coupon.getName());
        values.put("brandId", coupon.getBrand());
        values.put("catId", coupon.getCategory());
        values.put("listPrice", coupon.getListPrice());
        values.put("evaluatePrice", coupon.getEvaluatePrice());
        values.put("discount", coupon.getDiscount());
        values.put("stat", coupon.getStat());
        values.put("imgURL", coupon.getImgURL());
        values.put("expireDate", coupon.getExpireDate());
        //values.put("limit", coupon.getLimit());
        db.insert("Coupon", null, values);
        return this;
    }

    public Coupon findCouponById(String id) {
        Cursor cursor = db.rawQuery("select * from Coupon where id = ?", new String[]{id});
        Coupon c = collectOneCoupon(cursor);
        cursor.close();
        return c;
    }

    private Coupon collectOneCoupon(Cursor cursor){
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String id = cursor.getString(cursor.getColumnIndex("id"));
        String brandId = cursor.getString(cursor.getColumnIndex("brandId"));
        String catId = cursor.getString(cursor.getColumnIndex("catId"));
        double listPrice = cursor.getDouble(cursor.getColumnIndex("listPrice"));
        double evaluatePrice = cursor.getDouble(cursor.getColumnIndex("evaluatePrice"));
        double discount = cursor.getDouble(cursor.getColumnIndex("discount"));
        int stat = cursor.getInt(cursor.getColumnIndex("stat"));
        String imgURL = cursor.getString(cursor.getColumnIndex("imgURL"));
        String expireDate = cursor.getString(cursor.getColumnIndex("expireDate"));
        String limit = cursor.getString(cursor.getColumnIndex("limit"));
        Coupon coupon = new Coupon(name,id,brandId,catId,listPrice,evaluatePrice,discount,stat,imgURL,expireDate);
        return coupon;
    }
}

