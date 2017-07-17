package com.example.administrator.ccoupons.Connections;

import com.example.administrator.ccoupons.Tools.StringEncoder;
import com.github.kevinsawicki.http.HttpRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * Created by Administrator on 2017/7/16 0016.
 */

public class RegisterThread extends Thread {

    private String username, password, nickname;
    private int gender;
    private String url;
    char[] GenderChars = {'男', '女'};

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
            String body = "username=" + username + "&password=" + password + "&nickname=" + nickname + "&gender=" + GenderChars[gender];


            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.setHeader("User-Agent", USER_AGENT);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("username", username));
            urlParameters.add(new BasicNameValuePair("password", password));
            urlParameters.add(new BasicNameValuePair("nickname", nickname));
            urlParameters.add(new BasicNameValuePair("gender", "男"));
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
            client.getParams().setBooleanParameter("http.protocol.expect-continue",false);
            HttpResponse response = client.execute(post);
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + post.getEntity());
            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result.toString());
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void run() {
        connect(this.url);
    }
}

