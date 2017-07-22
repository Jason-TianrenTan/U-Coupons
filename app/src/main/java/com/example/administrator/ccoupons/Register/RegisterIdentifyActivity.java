package com.example.administrator.ccoupons.Register;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.AlertType;
import com.example.administrator.ccoupons.Tools.RegisterCheck;
import com.mob.MobSDK;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.mob.tools.utils.ResHelper.getStringRes;

//注册界面 验证码界面
public class RegisterIdentifyActivity extends AppCompatActivity {

    TextView upperText, timerText;
    EditText editText;
    Button button_next,
            button_reget;
    TextInputLayout inputLayout;

    private String phoneString;
    private String[] AlertStrings = "不能含有非法字符,长度必须为4位".split(",");
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

    Handler handler = new Handler() {


        @Override

        public void handleMessage(Message msg) {

            // TODO Auto-generated method stub

            super.handleMessage(msg);

            int event = msg.arg1;

            int result = msg.arg2;

            Object data = msg.obj;

            Log.e("event", "event=" + event);

            if (result == SMSSDK.RESULT_COMPLETE) {

                //短信注册成功后，返回MainActivity,然后提示新好友

                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功,验证通过

                    Toast.makeText(getApplicationContext(), "验证码校验成功", Toast.LENGTH_SHORT).show();


                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//服务器验证码发送成功

                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();

                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {//返回支持发送验证码的国家列表

                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();

                }

            } else {


                ((Throwable) data).printStackTrace();

                int resId = getStringRes(RegisterIdentifyActivity.this, "smssdk_network_error");

                Toast.makeText(RegisterIdentifyActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();


                if (resId > 0) {

                    Toast.makeText(RegisterIdentifyActivity.this, resId, Toast.LENGTH_SHORT).show();

                }


            }


        }
    };

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
        inputLayout = (TextInputLayout) findViewById(R.id.indentify_inputlayout);
        phoneString = getIntent().getStringExtra("phone_number");
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
                    inputLayout.setError(AlertStrings[err_type - 1]);
                } else {
                    SMSSDK.getVerificationCode("86", phoneString.trim());//请求获取短信验证码
                    /*
                    Intent intent = new Intent(RegisterIdentifyActivity.this, RegisterPasswordActivity.class);
                    intent.putExtra("phone_number",phoneString);
                    startActivity(intent);*/
                }
            }
        });


        EventHandler eh = new EventHandler() {


            @Override

            public void afterEvent(int event, int result, Object data) {


                Message msg = new Message();

                msg.arg1 = event;

                msg.arg2 = result;

                msg.obj = data;

                handler.sendMessage(msg);

            }


        };
        SMSSDK.registerEventHandler(eh);

        startCountDown();
    }

    private void startCountDown() {
        current = COUNTDOWN_TIME;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new CountDownTask(), 0, 1000);
    }
}
