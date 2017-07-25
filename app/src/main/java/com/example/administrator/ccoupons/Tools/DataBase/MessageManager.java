package com.example.administrator.ccoupons.Tools.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.ccoupons.Fragments.Message;
import com.example.administrator.ccoupons.Tools.DataBase.DatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;

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
