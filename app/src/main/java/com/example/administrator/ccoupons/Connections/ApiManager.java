package com.example.administrator.ccoupons.Connections;

import com.example.administrator.ccoupons.Data.GlobalConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/8/17 0017.
 * Class for Retrofit APIs
 */


public class ApiManager {


    private RetrofitService myApi;
    private static ApiManager sApiManager;


    //get instance of api
    public static ApiManager getInstance() {
        if (sApiManager == null) {
            synchronized (ApiManager.class) {
                if (sApiManager == null) {
                    sApiManager = new ApiManager();
                }
            }
        }
        return sApiManager;
    }


    //request Retrofit service
    public RetrofitService getRetrofitService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpInterceptor())
                .build();
        if (myApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GlobalConfig.base_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            myApi = retrofit.create(RetrofitService.class);
        }
        return myApi;
    }

}
