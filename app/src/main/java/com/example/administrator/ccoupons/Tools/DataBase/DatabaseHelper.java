package com.example.administrator.ccoupons.Tools.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by CZJ on 2017/7/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_MASSAGE = "create table Message ("
            + "id text primary key, "
            + "isNew integer, "
            + "messageCat integer, "
            + "userId text, "
            + "time text, "
            + "content text, "
            + "couponName text, "
            + "couponId integer, "
            + "couponURL integer)";

    public static final String CREATE_COUPON = "create table Coupon ("
            + "id text primary key, "
            + "name text, "
            + "brandId text, "
            + "catId text, "
            + "listPrice real, "
            + "evaluatePrice real, "
            + "discount real, "
            + "stat integer, "
            + "imgURL text, "
            + "expireDate text, "
            + "limit text)";

    private Context mContext;
    private String dbName;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        dbName = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (dbName.equals("Message.db")){
            db.execSQL(CREATE_MASSAGE);
        }
        if (dbName.equals("Coupon.db")){
            db.execSQL(CREATE_COUPON);
        }
        if (dbName.equals("CouponCache.db")){
            db.execSQL(CREATE_COUPON);
        }
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
