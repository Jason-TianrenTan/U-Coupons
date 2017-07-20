package com.example.administrator.ccoupons.Fragments;

import android.os.*;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Register.RegisterIdentifyActivity;
import com.example.administrator.ccoupons.Tools.LocationGet;
import com.example.administrator.ccoupons.Tools.MessageType;
import com.example.administrator.ccoupons.UI.CustomDialog;

import java.util.Timer;
import java.util.TimerTask;

public class LocationSelectActivity extends AppCompatActivity {

    private String location = null;
    private LocationGet locationFetchr;
    private CustomDialog customDialog;
    private TextView locationText;
    private static final int COUNTDOWN_TIME = 3;//3s
    private int current;
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MessageType.LOCATION_GET:
                    location = locationFetchr.getCity();
                    customDialog.dismiss();
                    locationText.setText(location);
                    break;
                case MessageType.LOCATION_FAILED:
                    Toast.makeText(getApplicationContext(), "获取当前定位失败，请检查设置或者网络连接", Toast.LENGTH_SHORT).show();
                    customDialog.dismiss();
                    break;
            }
        }
    };

    private Handler TimerHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    updateTimer();
                    break;
            }
        }
    };

    private void updateTimer() {
        current--;
        if (current == 0) {
            Message msg = new Message();
            msg.what = MessageType.LOCATION_FAILED;
            handler.sendMessage(msg);
        }
    }

    private class CountDownTask extends TimerTask {// public abstract class TimerTask implements Runnable{}

        @Override
        public void run() {
            android.os.Message msg = new Message();
            msg.what = 1;
            TimerHandler.sendMessage(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_select);
        locationText = (TextView)findViewById(R.id.location_select_textview);
        String localCity = getIntent().getStringExtra("location");
        if (localCity != null) {
            location = localCity;
            locationText.setText(localCity);
        }
        else {
            locationFetchr = new LocationGet(this, handler);
            locationFetchr.requestLocation();
            customDialog = new CustomDialog(this, R.style.CustomDialog);
            customDialog.show();
        }

    }

    private void startCountDown() {
        current = COUNTDOWN_TIME;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new CountDownTask(), 0, 1000);
    }


}
