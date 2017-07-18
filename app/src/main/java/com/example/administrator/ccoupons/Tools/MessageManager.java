package com.example.administrator.ccoupons.Tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.ccoupons.Tools.DatabaseHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by CZJ on 2017/7/16.
 */

public class MessageManager {
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    public MessageManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        databaseHelper = new DatabaseHelper(context, name, factory, version);
        db = databaseHelper.getWritableDatabase();
    }

    public MessageManager addMessage(Message Message) {
        ContentValues values = new ContentValues();
        values.put("id", Message.getId());
        values.put("fromSystem", Message.getFromSystem());
        values.put("destinationId", Message.getDestinationId());
        values.put("read", Message.getRead());
        values.put("send", Message.getSend());
        values.put("content", Message.getContent());
        values.put("timestamp", Message.getTimeStamp());
        db.insert("Message", null, values);
        return this;
    }

    public ArrayList<Message> findSystemMessage() {
        ArrayList<Message> arrayList = new ArrayList<Message>();
        Cursor cursor = db.rawQuery("select * from Message where fromSystem = ?", new String[]{"1"});
        collectMessage(cursor, arrayList);
        cursor.close();
        sortMessage(arrayList);
        return arrayList;
    }

    public ArrayList<Message> findUserMessage(String userId) {
        ArrayList<Message> arrayList = new ArrayList<Message>();
        Cursor cursor = db.rawQuery("select * from Message where fromId = ?", new String[]{userId});
        collectMessage(cursor, arrayList);
        cursor.close();
        sortMessage(arrayList);
        return arrayList;
    }

    public ArrayList<Message> findAll() {
        ArrayList<Message> arrayList = new ArrayList<Message>();
        Cursor cursor = db.rawQuery("select * from Message", null);
        collectMessage(cursor, arrayList);
        cursor.close();
        sortMessage(arrayList);
        return arrayList;
    }

    public void setToRead(String destinationId) {
        db.execSQL("update Message set read = ? where destinationId = ?", new String[]{"1", destinationId});
    }

    public void deleteMessage(String destinationId) {
        db.execSQL("delete from Message where destinationId = ?", new String[]{destinationId});
    }

    private void collectMessage(Cursor cursor, ArrayList<Message> arrayList) {
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                boolean fromSystem = (cursor.getInt(cursor.getColumnIndex("fromSystem")) == 1);
                boolean send = (cursor.getInt(cursor.getColumnIndex("send")) == 1);
                boolean read = (cursor.getInt(cursor.getColumnIndex("read")) == 1);
                String destinationId = cursor.getString(cursor.getColumnIndex("destinationId"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                Long timestamp = cursor.getLong(cursor.getColumnIndex("timestamp"));
                Message Message = new Message(id, fromSystem, send, read, destinationId, content, timestamp);
                arrayList.add(Message);
            } while (cursor.moveToNext());
        }
    }

    private void sortMessage(ArrayList<Message> arrayList) {
        Collections.sort(arrayList);
    }
}
