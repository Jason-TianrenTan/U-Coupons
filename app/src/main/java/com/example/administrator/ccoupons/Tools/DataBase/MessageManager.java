package com.example.administrator.ccoupons.Tools.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by CZJ on 2017/7/16.
 */

public class MessageManager {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private static final String dbName = "Message.db";


    public MessageManager(Context context) {
        databaseHelper = new DatabaseHelper(context, dbName, null, 1);
        db = databaseHelper.getWritableDatabase();
    }
}
