package com.example.administrator.ccoupons.Connections;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.ccoupons.Tools.MessageType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
public class UHuiConnection {

    //{"result":"success"} 登录成功
    //{"error": "\u7528\u6237\u4e0d\u5b58\u5728"} 登录失败
    //{"errno": "0", "message": "\u6ce8\u518c\u6210\u529f"} 注册成功
    //{"errno": "1", "message": "\u6635\u79f0\u5df2\u5b58\u5728"} 注册失败

    private static final int REQUEST_TIMEOUT = 3 * 1000;

    private String url;
    private HttpClient client;
    private HttpPost post;
    private String content;
    private List<NameValuePair> urlParameters;
    private Handler handler;


    /**
     *
     * @param url target URL
     * @param handler handler
     */
    public UHuiConnection(String url, Handler handler) {
        this.url = url;
        BasicHttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);
        this.client = new DefaultHttpClient(params);
        this.post = new HttpPost(url);
        this.urlParameters = new ArrayList<NameValuePair>();
        this.handler = handler;
    }


    //get Content
    public String getContent() {
        return this.content;
    }


    /**
     *
     * @param name header name
     * @param val header value
     */
    public void setHeader(String name, String val) {
        post.setHeader(name, val);
    }


    /**
     *
     * @param name add key
     * @param val add corresponding value to key
     */
    public void add(String name, String val) {
        urlParameters.add(new BasicNameValuePair(name, val));
    }


    //connect
    public void connect() {
        try {

            post.setEntity(new UrlEncodedFormEntity(urlParameters, HTTP.UTF_8));
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);

            if (handler != null) {
                Message msg = new Message();
                msg.what = MessageType.CONNECTION_SUCCESS;
                handler.sendMessage(msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (handler != null) {
                Message msg = new Message();
                msg.what = MessageType.CONNECTION_ERROR;
                handler.sendMessage(msg);
            }
        }
    }
}