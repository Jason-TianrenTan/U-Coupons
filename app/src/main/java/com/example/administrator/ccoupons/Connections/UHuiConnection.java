package com.example.administrator.ccoupons.Connections;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.ccoupons.Tools.MessageType;

import org.apache.http.Header;
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
 * Created by Administrator on 2017/7/17 0017.
 */

public class UHuiConnection {


    private static final int REQUEST_TIMEOUT = 5 * 1000;

    private String url;
    private HttpClient client;
    private HttpPost post;
    private String content;
    private List<NameValuePair> urlParameters;
    private Handler handler;
    public UHuiConnection(String url, Handler handler) {
        this.url = url;
        client = new DefaultHttpClient();
        post = new HttpPost(url);
        urlParameters = new ArrayList<NameValuePair>();
        this.handler = handler;
    }

    public String getContent() {
        return this.content;
    }

    public void setHeader(String name, String val) {
        post.setHeader(name, val);
    }

    public void add(String name, String val) {
        urlParameters.add(new BasicNameValuePair(name, val));
    }

    public void connect(){
        try {
            System.out.println("ON connect");
            BasicHttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params,REQUEST_TIMEOUT);
            post.setEntity(new UrlEncodedFormEntity(urlParameters, HTTP.UTF_8));
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);
            System.out.println(content);
            Message msg = new Message();
            msg.what = MessageType.CONNECTION_SUCCESS;
            handler.sendMessage(msg);
        }catch (Exception e) {
            e.printStackTrace();
            Message msg = new Message();
            msg.what = MessageType.CONNECTION_ERROR;
            handler.sendMessage(msg);
        }
    }
}
