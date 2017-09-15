package com.example.administrator.ccoupons.Connections;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.administrator.ccoupons.Tools.MessageType;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.util.HashMap;
import java.util.Map;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class ConnectionManager {


    private ZLoadingDialog dialog;
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


    /**
     *
     * @param url connect url
     * @param reqMap request key-value hashmap
     */
    public ConnectionManager(String url, HashMap<String, String> reqMap) {
        this.attributes = reqMap;
        this.url = url;
    }

    /**
     *
     * @param url
     * @param reqMap
     * @param dialog request with UI loading
     */
    public ConnectionManager(String url, HashMap<String,String> reqMap, ZLoadingDialog dialog) {
        this.attributes = reqMap;
        this.url = url;
        this.dialog = dialog;
    }


    /**
     *
     * @param listener
     */
    public void setConnectionListener(UHuiConnectionListener listener) {
        this.listener = listener;
    }


    //connect to server
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
                finally {
                    if (dialog != null)
                        dialog.dismiss();
                }
            }
        }).start();

    }


    //custom listener
    public interface UHuiConnectionListener {
        public void onConnectionSuccess(String response);

        public void onConnectionTimeOut();

        public void onConnectionFailed();
    }

}
