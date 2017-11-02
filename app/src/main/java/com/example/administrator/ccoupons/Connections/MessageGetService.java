package com.example.administrator.ccoupons.Connections;

import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.Tools.MessageType;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class MessageGetService extends IntentService {

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
                    intent.putExtra("content", messageThread.getResponse());
                    sendBroadcast(intent);
                    System.out.println("go message");
                    System.out.println("message broadcast sent");
                    break;
            }
        }
    };


    public MessageGetService() {
        super("MessageGetService");
    }


    @Override
    public void onHandleIntent(Intent i) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestMessage();
        return super.onStartCommand(intent,flags,startId);
    }

    private void requestMessage() {
        messageThread = new RequestMessageThread(handler, getApplicationContext());
        messageThread.start();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MessageGetService.class);
    }

    public static void setServiceAlarm(Context context) {
        Intent i = MessageGetService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60 * 1000, pi);
    }

}
