package com.example.administrator.ccoupons.Connections;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.administrator.ccoupons.Tools.MessageType;

import java.util.HashMap;
import java.util.Map;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class ConnectionManager {

    private UHuiConnection connection;
    private String url;
    private HashMap<String, String> attributes;
    private UHuiConnectionListener listener;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MessageType.CONNECTION_ERROR:
                    listener.onConnectionFailed();
                    break;
                case MessageType.CONNECTION_TIMEOUT:
                    listener.onConnectionTimeOut();
                    break;
                case MessageType.CONNECTION_SUCCESS:
                    listener.onConnectionSuccess(connection.getContent());
                    break;
            }
        }
    };

    public ConnectionManager(String url, HashMap<String, String> reqMap) {
        this.attributes = reqMap;
        this.url = url;
    }

    public void setConnectionListener(UHuiConnectionListener listener) {
        this.listener = listener;
    }

    public void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //连接
                try {
                    connection = new UHuiConnection(url, handler);
                    connection.setHeader("User-Agent", USER_AGENT);
                    connection.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                    for (Map.Entry<String, String> entry : attributes.entrySet()) {
                        String key = entry.getKey(),
                                value = entry.getValue();
                        connection.add(key, value);
                    }
                    connection.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public interface UHuiConnectionListener {
        public void onConnectionSuccess(String response);

        public void onConnectionTimeOut();

        public void onConnectionFailed();
    }

}
