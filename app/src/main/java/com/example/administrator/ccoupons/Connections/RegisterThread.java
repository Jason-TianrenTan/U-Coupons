package com.example.administrator.ccoupons.Connections;

import com.example.administrator.ccoupons.Tools.StringEncoder;

import java.io.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * Created by Administrator on 2017/7/16 0016.
 */

public class RegisterThread extends Thread {

    private String username, password, nickname;
    private int gender;
    private String url;
    String[] GenderChars = {"男", "女"};

    public RegisterThread(String url, String name, String pass, String nick, int gender) {
        this.url = url;
        this.username = name;
        try {
            this.password = new StringEncoder(pass).getMD5();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.nickname = nick;
        this.gender = gender;
    }



    private void connect(String url) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.setHeader("User-Agent", USER_AGENT);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("username", username));
            urlParameters.add(new BasicNameValuePair("password", password));
            urlParameters.add(new BasicNameValuePair("nickname", nickname));
            urlParameters.add(new BasicNameValuePair("gender", GenderChars[gender]));
            post.setEntity(new UrlEncodedFormEntity(urlParameters, HTTP.UTF_8));
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            System.out.println(content);


        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void run() {
        connect(this.url);
    }
}

