package com.example.administrator.ccoupons.Tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by CZJ on 2017/7/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public  static final String CREATE_MASSAGE = "create table Massage ("
            + "id text primary key, "
            + "fromSystem integer, "
            + "send integer, "
            + "read integer, "
            + "destinationId text, "
            + "content text, "
            + "timestamp real)";

    private Context mContext;
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MASSAGE);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
