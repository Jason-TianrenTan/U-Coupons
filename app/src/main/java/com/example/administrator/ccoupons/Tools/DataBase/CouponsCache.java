package com.example.administrator.ccoupons.Tools.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.ccoupons.Main.Coupon;

import java.util.ArrayList;

/**
 * Created by CZJ on 2017/7/23.
 */

public class CouponsCache {
    protected DatabaseHelper databaseHelper;
    protected SQLiteDatabase db;
    static final String dbName = "CouponCache.db";

    public CouponsCache(Context context) {
        databaseHelper = new DatabaseHelper(context, dbName, null, 1);
        db = databaseHelper.getWritableDatabase();
        clear();
    }

    public CouponsCache addCoupon(Coupon coupon) {
        ContentValues values = new ContentValues();
        values.put("id", coupon.getCouponid());
        values.put("name", coupon.getProduct());
        values.put("brandId", coupon.getBrandName());
        values.put("catId", coupon.getCategory());
        values.put("listPrice", coupon.getListprice());
        values.put("evaluatePrice", coupon.getValue());
        values.put("discount", coupon.getDiscount());
        values.put("stat", coupon.getStat());
        values.put("imgURL", coupon.getPic());
        values.put("expireDate", coupon.getExpiredtime());
        //values.put("limit", coupon.getLimit());
        db.insert("Coupon", null, values);
        return this;
    }

    public ArrayList<Integer> findCouponByStat(int stat) {
        Cursor cursor = db.rawQuery("select id from Coupon where stat = ?", new String[]{String.valueOf(stat)});
        ArrayList<Integer> arrayList = collectCouponId(cursor);
        return arrayList;
    }

    public void updateCoupon(Coupon coupon) {
        String id = String.valueOf(coupon.getCouponid());
        db.execSQL("delete from Coupon where id = ?", new String[]{id});
        addCoupon(coupon);
    }

    public void deleteCoupon(int id) {
        db.execSQL("delete from Coupon where id = ?", new String[]{String.valueOf(id)});
    }

    public ArrayList<Integer> findAll() {
        Cursor cursor = db.rawQuery("select id from Coupon", null);
        ArrayList<Integer> arrayList = collectCouponId(cursor);
        return arrayList;
    }

    public void clear() {
        db.execSQL("delete from Coupon", null);
    }


    private ArrayList<Integer> collectCouponId(Cursor cursor) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                arrayList.add(id);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }
}
