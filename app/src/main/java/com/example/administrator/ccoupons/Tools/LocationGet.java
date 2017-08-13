package com.example.administrator.ccoupons.Tools;

import android.content.Context;
import android.os.*;
import android.os.Message;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.administrator.ccoupons.Fragments.*;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class LocationGet {

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private Context mContext;
    private String city;
    private TextView target;

    public LocationGet(Context context, TextView textview) {
        this.mContext = context;
        this.target = textview;
        target.setText("定位");
    }

    public void requestLocation() {
        mLocationClient = new AMapLocationClient(mContext.getApplicationContext());
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setHttpTimeOut(20 * 1000);
        mLocationOption.setNeedAddress(true);

        AMapLocationListener aMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        city = aMapLocation.getCity();
                        target.setText(city);
                        System.out.println("获取定位: " + city);
                    }
                }
            }
        };
        mLocationClient.setLocationListener(aMapLocationListener);

        mLocationClient.startLocation();
    }

    public String getCity() {
        return this.city;
    }
}
