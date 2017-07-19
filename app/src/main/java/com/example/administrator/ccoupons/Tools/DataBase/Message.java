package com.example.administrator.ccoupons.Tools.DataBase;

import android.support.annotation.NonNull;

/**
 * Created by CZJ on 2017/7/16.
 */

public class Message implements Comparable<Message> {
    private String id;
    private boolean fromSystem;//true for from system and false for user
    private boolean send;//true for send and false for receive
    private boolean read;//true for read and false for unread
    private String destinationId;
    private String content;
    private Long timestamp;

    public Message(String id, boolean fromSystem, boolean send, boolean read, String destinationId, String content, Long timestamp) {
        this.id = id;
        this.fromSystem = fromSystem;
        this.send = send;
        this.read = read;
        this.destinationId = destinationId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean getFromSystem() {
        return fromSystem;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public boolean getSend() {
        return send;
    }

    public boolean getRead() {
        return read;
    }

    public Long getTimeStamp() {
        return timestamp;
    }

    @Override
    public int compareTo(@NonNull Message message) {
        return new Long(this.timestamp - message.timestamp).intValue();
    }
}
