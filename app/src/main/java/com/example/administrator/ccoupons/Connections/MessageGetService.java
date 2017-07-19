package com.example.administrator.ccoupons.Connections;

import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.widget.Toast;

import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.Tools.MessageType;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class MessageGetService extends Service {

    private RequestMessageThread messageThread;
    private static final String MESSAGE_BROADCAST = "com.example.administrator.ccoupons.MESSAGE_BROADCAST";
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessageType.CONNECTION_ERROR:
                    Toast.makeText(getApplicationContext(), "连接服务器遇到问题，请检查网络连接!", Toast.LENGTH_LONG).show();
                    break;
                case MessageType.CONNECTION_TIMEOUT:
                    Toast.makeText(getApplicationContext(), "连接服务器超时，请检查网络连接!", Toast.LENGTH_LONG).show();
                    break;
                case MessageType.CONNECTION_SUCCESS:
                    Intent intent = new Intent(MESSAGE_BROADCAST);
                    intent.putExtra("content",messageThread.getResponse());
                    break;
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        requestMessage();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = 120 * 1000;
        long triggerTime = SystemClock.elapsedRealtime() + time;
        Intent i = new Intent(this, MainPageActivity.AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);
        return super.onStartCommand(intent,flags,startId);
    }

    private void requestMessage() {
        messageThread = new RequestMessageThread(getApplicationContext());
        messageThread.start();
    }

}
