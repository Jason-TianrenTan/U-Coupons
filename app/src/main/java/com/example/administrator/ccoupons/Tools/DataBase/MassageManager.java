package com.example.administrator.ccoupons.Tools.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by CZJ on 2017/7/16.
 */

public class MassageManager {
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    public MassageManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        databaseHelper = new DatabaseHelper(context, name, factory, version);
        db = databaseHelper.getWritableDatabase();
    }

    public MassageManager addMassage(Massage massage) {
        ContentValues values = new ContentValues();
        values.put("id", massage.getId());
        values.put("fromSystem", massage.getFromSystem());
        values.put("destinationId", massage.getDestinationId());
        values.put("read", massage.getRead());
        values.put("send", massage.getSend());
        values.put("content", massage.getContent());
        values.put("timestamp", massage.getTimeStamp());
        db.insert("Massage", null, values);
        return this;
    }

    public ArrayList<Massage> findSystemMassage() {
        ArrayList<Massage> arrayList = new ArrayList<Massage>();
        Cursor cursor = db.rawQuery("select * from Massage where fromSystem = ?", new String[]{"1"});
        collectMassage(cursor, arrayList);
        cursor.close();
        sortMassage(arrayList);
        return arrayList;
    }

    public ArrayList<Massage> findUserMassage(String userId) {
        ArrayList<Massage> arrayList = new ArrayList<Massage>();
        Cursor cursor = db.rawQuery("select * from Massage where fromId = ?", new String[]{userId});
        collectMassage(cursor, arrayList);
        cursor.close();
        sortMassage(arrayList);
        return arrayList;
    }

    public ArrayList<Massage> findAllMassage() {
        ArrayList<Massage> arrayList = new ArrayList<Massage>();
        Cursor cursor = db.rawQuery("select * from Massage", null);
        collectMassage(cursor, arrayList);
        cursor.close();
        sortMassage(arrayList);
        return arrayList;
    }

    public void setToRead(String destinationId) {
        db.execSQL("update Massage set read = ? where destinationId = ?", new String[]{"1", destinationId});
    }

    public void deleteMassage(String destinationId) {
        db.execSQL("delete from Massage where destinationId = ?", new String[]{destinationId});
    }

    public void clearMassage(){
        db.execSQL("delete from Massage");
    }

    private void collectMassage(Cursor cursor, ArrayList<Massage> arrayList) {
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                boolean fromSystem = (cursor.getInt(cursor.getColumnIndex("fromSystem")) == 1);
                boolean send = (cursor.getInt(cursor.getColumnIndex("send")) == 1);
                boolean read = (cursor.getInt(cursor.getColumnIndex("read")) == 1);
                String destinationId = cursor.getString(cursor.getColumnIndex("destinationId"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                Long timestamp = cursor.getLong(cursor.getColumnIndex("timestamp"));
                Massage massage = new Massage(id, fromSystem, send, read, destinationId, content, timestamp);
                arrayList.add(massage);
            } while (cursor.moveToNext());
        }
    }

    private void sortMassage(ArrayList<Massage> arrayList) {
        Collections.sort(arrayList);
    }
}
