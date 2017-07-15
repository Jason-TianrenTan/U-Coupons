package com.example.administrator.ccoupons.Register;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.AlertType;
import com.example.administrator.ccoupons.Tools.RegisterCheck;

import java.util.Timer;
import java.util.TimerTask;

//注册界面 验证码界面
public class RegisterIdentifyActivity extends AppCompatActivity {

    TextView upperText, timerText;
    EditText editText;
    Button button_next,
            button_reget;

    private String[] AlertStrings = "不能含有非法字符,长度必须为6位".split(",");
    private boolean reget_permission = false;
    public static final int COUNTDOWN_TIME = 20;

    private RegisterCheck checker;
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

    int current = COUNTDOWN_TIME;
    private void updateTimer() {
        if (!reget_permission) {
            current--;
            timerText.setText(current + getResources().getString(R.string.register_indentify_timertext));
            if (current == 0) {
                timerText.setText("");
                reget_permission = true;
                button_reget.setEnabled(true);
            }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_identify);

        checker = new RegisterCheck();
        Toolbar toolbar = (Toolbar) findViewById(R.id.register_identify_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        upperText = (TextView) findViewById(R.id.register_identify_uppertext);
        editText = (EditText) findViewById(R.id.register_identify_edittext);
        button_next = (Button) findViewById(R.id.register_identify_button_next);
        button_reget = (Button) findViewById(R.id.register_identify_button_reget);
        timerText = (TextView) findViewById(R.id.register_timer_text);

        String phoneString = getIntent().getStringExtra("phone_number");
        upperText.setText(getResources().getString(R.string.register_identify_text) + " +86 " + phoneString);

        button_reget.setEnabled(false);
        button_reget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送验证码
                reget_permission = false;
                button_reget.setEnabled(false);
                startCountDown();
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = editText.getText().toString();
                int err_type = checker.alertIdentifyCode(str);
                if (err_type != AlertType.NO_ERROR) {
                    //有错误
                    editText.setError(AlertStrings[err_type - 1]);
                }
                else {
                    startActivity(new Intent(RegisterIdentifyActivity.this, RegisterPasswordActivity.class));
                }
            }
        });
        startCountDown();
    }

    private void startCountDown() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new CountDownTask(), 0, 1000);
    }
}
