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
        values.put("id", coupon.getCouponId());
        values.put("name", coupon.getName());
        values.put("brandId", coupon.getBrandName());
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

    public Coupon findCouponById(int id) {
        Cursor cursor = db.rawQuery("select * from Coupon where id = ?", new String[]{String.valueOf(id)});
        Coupon c = collectOneCoupon(cursor);
        cursor.close();
        return c;
    }

    public ArrayList<Integer> findCouponByStat(int stat) {
        Cursor cursor = db.rawQuery("select id from Coupon where stat = ?", new String[]{String.valueOf(stat)});
        ArrayList<Integer> arrayList = collectCouponId(cursor);
        return arrayList;
    }

    public void updateCoupon(Coupon coupon) {
        String id = String.valueOf(coupon.getCouponId());
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

    private Coupon collectOneCoupon(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String id = cursor.getString(cursor.getColumnIndex("id"));
        String brandId = cursor.getString(cursor.getColumnIndex("brandId"));
        String catId = cursor.getString(cursor.getColumnIndex("catId"));
        double listPrice = cursor.getDouble(cursor.getColumnIndex("listPrice"));
        double evaluatePrice = cursor.getDouble(cursor.getColumnIndex("evaluatePrice"));
        String discount = cursor.getString(cursor.getColumnIndex("discount"));
        int stat = cursor.getInt(cursor.getColumnIndex("stat"));
        String imgURL = cursor.getString(cursor.getColumnIndex("imgURL"));
        String expireDate = cursor.getString(cursor.getColumnIndex("expireDate"));
        String limit = cursor.getString(cursor.getColumnIndex("limit"));
        Coupon coupon = new Coupon(name, id, brandId, catId, listPrice, evaluatePrice, discount, stat, imgURL, expireDate);
        return coupon;
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
