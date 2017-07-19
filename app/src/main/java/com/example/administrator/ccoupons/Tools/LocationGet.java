package com.example.administrator.ccoupons.Tools;

import android.content.Context;
import android.os.Handler;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class LocationGet {

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private Context mContext;
    public LocationGet(Context context, Handler handler) {
        this.mContext = context;
    }

    public void getLocation() {
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
                        String address = aMapLocation.getCity();

                    }
                }
            }
        };
        mLocationClient.setLocationListener(aMapLocationListener);

        mLocationClient.startLocation();
    }
}
