package com.example.administrator.ccoupons.UI;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Register.RegisterIdentifyActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class CustomLoader {
    private Timer timer;
    private CustomDialog customDialog;
    private int total;
    private int current;
    private CustomLoaderListener listener;
    private Context mContext;
    public CustomLoader(int time, Context context) {
        this.total = time;
        this.mContext = context;
    }
    public void setLoaderListener(CustomLoaderListener listener) {
        this.listener = listener;
    }

    public void start() {
        current = total;
        customDialog = new CustomDialog(mContext, R.style.CustomDialog);
        customDialog.show();
        timer = new Timer();
        timer.scheduleAtFixedRate(new CountDownTask(), 0, 1000);
    }

    public void finish() {
        timer.cancel();
        customDialog.dismiss();
    }

    private Handler TimerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    updateTimer();
                    break;
            }
        }
    };

    private void updateTimer() {
        current--;
        listener.onTimeChanged();
        if (current ==0) {
            this.finish();
            listener.onTimeFinish();
        }
    }
    private class CountDownTask extends TimerTask {// public abstract class TimerTask implements Runnable{}
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = 1;
            TimerHandler.sendMessage(msg);
        }
    }

    public static abstract class CustomLoaderListener {

        public abstract void onTimeChanged();//计时进行中
        public abstract void onTimeFinish();//计时结束

    }
}
