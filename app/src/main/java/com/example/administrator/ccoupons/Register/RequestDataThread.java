package com.example.administrator.ccoupons.Register;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * Created by Administrator on 2017/7/16 0016.
 */

public class RequestDataThread extends Thread {

    private String url = "http://192.168.207.242:8000/login";

    @Override
    public void run() {
        connect();
    }

    private void connect() {
        System.out.println("on Connect");
        try {

            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);
            get.setHeader("User-Agent", USER_AGENT);
            HttpResponse response = client.execute(get);
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line + '\n');
            }
            System.out.println(result.toString());
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
