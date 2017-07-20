package com.example.administrator.ccoupons.Fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.*;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Register.RegisterIdentifyActivity;
import com.example.administrator.ccoupons.Tools.LocationGet;
import com.example.administrator.ccoupons.Tools.MessageType;
import com.example.administrator.ccoupons.UI.CustomDialog;
import com.example.administrator.ccoupons.UI.CustomLoader;
import com.example.administrator.ccoupons.UI.QuickIndexBar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LocationSelectActivity extends AppCompatActivity {

    private String location = null;
    private LocationGet locationFetchr;
    private TextView locationText;
    private CustomLoader customLoader;
    private ArrayList<String> cityList = new ArrayList<>();


    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MessageType.LOCATION_GET:
                    location = locationFetchr.getCity();
                    locationText.setText(location);
                    customLoader.finish();
                    break;
                case MessageType.LOCATION_FAILED:
                    makeErrorToast();
                    break;
            }
        }
    };

    private void makeErrorToast() {
        Toast.makeText(this, "获取当前定位失败，请检查设置或者网络连接", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_select);

        Toolbar toolbar = (Toolbar)findViewById(R.id.location_select_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        locationText = (TextView) findViewById(R.id.location_select_textview);
        String localCity = getIntent().getStringExtra("location");
        if (localCity != null) {
            location = localCity;
            locationText.setText(localCity);
        } else {
            locationFetchr = new LocationGet(this, handler);
            locationFetchr.requestLocation();
            startCountDown();
        }

        cityList = getCityList();
        LocationAdapter adapter = new LocationAdapter(cityList);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.citylist_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    private void startCountDown() {
        customLoader = new CustomLoader(5, handler, this);
        customLoader.setLoaderListener(new CustomLoader.CustomLoaderListener() {
            @Override
            public void onTimeChanged() {

            }
            @Override
            public void onTimeFinish() {
                Message msg = new Message();
                msg.what = MessageType.LOCATION_FAILED;
                handler.sendMessage(msg);
            }

        });
        customLoader.start();
    }


    private ArrayList<String> getCityList() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i =0;i<20;i++) {
            String str = "City No." + i;
            arrayList.add(str);
        }
        return arrayList;
    }

}
