package com.example.administrator.ccoupons.Connections;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/17 0017.
 */

public class HttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        System.out.println("Request chain : " + request.url().toString());
        Response response = chain.proceed(request);
        return response;
    }
}