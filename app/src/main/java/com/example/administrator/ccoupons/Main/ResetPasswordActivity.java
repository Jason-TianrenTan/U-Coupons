package com.example.administrator.ccoupons.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

<<<<<<< HEAD
import com.example.administrator.ccoupons.R;

public class ResetPasswordActivity extends AppCompatActivity {

    Toolbar toolbar;
=======
import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.CustomEditText.PasswordToggleEditText;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.AlertType;
import com.example.administrator.ccoupons.Tools.EditTextTools;
import com.example.administrator.ccoupons.Tools.PasswordEncoder;
import com.example.administrator.ccoupons.Tools.RegisterCheck;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ResetPasswordActivity extends AppCompatActivity {


    public static final int COUNTDOWN_TIME = 30;
    public static final int SMS_FAILED = 1;//验证失败
    public static final int SMS_SUCCESS = 2;//验证通过
    private Toolbar toolbar;
    private boolean verify_cord = false, valid = false;
    private String phoneString;
    private String password;

    private TextView requestCordButton;
    private Button button_next;
    private EditText phoneText, cordText;
    private PasswordToggleEditText passText, confirmText;
    private TextInputLayout phoneInputLayout, cordInputLayout, passInputLayout, confirmInputLayout;

    //reset timer
    private int current = COUNTDOWN_TIME;
    private boolean reget_permission = false;


    private String[] errorStrings = "不能含有非法字符,长度必须为6~16位,密码强度太弱".split(",");
    private RegisterCheck checker = new RegisterCheck();


    /**
     * bind views
     */
    private void bindViews() {
        requestCordButton = (TextView) findViewById(R.id.request_cord_button);
        button_next = (Button) findViewById(R.id.reset_button_ok);
        phoneText = (EditText) findViewById(R.id.reset_phone_edittext);
        cordText = (EditText) findViewById(R.id.reset_cord_edittext);
        phoneInputLayout = (TextInputLayout) findViewById(R.id.reset_phone_inputlayout);
        cordInputLayout = (TextInputLayout) findViewById(R.id.reset_cord_inputlayout);
        passText = (PasswordToggleEditText) findViewById(R.id.reset_newpass_edittext);
        passInputLayout = (TextInputLayout) findViewById(R.id.reset_newpass_inputlayout);
        confirmText = (PasswordToggleEditText) findViewById(R.id.reset_confirmpass_edittext);
        confirmInputLayout = (TextInputLayout) findViewById(R.id.reset_confirmpass_inputlayout);
    }


>>>>>>> ttr
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        toolbar = (Toolbar)findViewById(R.id.reset_pass_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Button button_next = (Button)findViewById(R.id.reset_button_ok);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
<<<<<<< HEAD
=======

        EventHandler eh = new EventHandler() {


            @Override

            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                SMShandler.sendMessage(msg);
            }


        };
        SMSSDK.registerEventHandler(eh);

        String intentString = getIntent().getStringExtra("phoneString");
        if (intentString != null)
            phoneText.setText(intentString);
    }


    /**
     * send SMS
     */
    private void sendSMS() {
        //发送验证码
        System.out.println("Sent SMS code to +86" + phoneString.trim());
        SMSSDK.getVerificationCode("86", phoneString.trim());//请求获取短信验证码
    }


    /**
     * handler for receiving SMS results
     */
    private Handler SMSVerifyHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SMS_FAILED:
                    Toast.makeText(getApplicationContext(), "验证码错误!", Toast.LENGTH_SHORT).show();
                    valid = false;
                    cordText.setText("");
                    verify_cord = false;
                    break;
                case SMS_SUCCESS:
                    valid = true;
                    verify_cord = false;
                    phoneString = phoneText.getText().toString();
                    password = passText.getText().toString();
                    String url = GlobalConfig.base_URL + GlobalConfig.resetPass_URL;
                    String passwordString;//new password
                    try {
                        passwordString = new PasswordEncoder().EncodeByMd5(password);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "遇到未知错误(MD5), 请稍后重试", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    HashMap<String, String> map = new HashMap<>();
                    map.put("phoneNum", phoneString);
                    map.put("password", passwordString);
                    ConnectionManager connectionManager = new ConnectionManager(url, map);
                    connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
                        @Override
                        public void onConnectionSuccess(String response) {
                            Toast.makeText(getApplicationContext(), "修改密码成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ResetPasswordActivity.this, MainPageActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onConnectionTimeOut() {
                            Toast.makeText(getApplicationContext(), "连接服务器超时，请检查网络连接!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onConnectionFailed() {
                            Toast.makeText(getApplicationContext(), "连接服务器遇到问题，请检查网络连接!", Toast.LENGTH_LONG).show();
                        }
                    });
                    connectionManager.connect();
                    break;

            }
        }
    };

    Handler SMShandler = new Handler() {


        @Override

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            System.out.println("Event = " + event + ", Result = " + result);
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功,验证通过
                    Toast.makeText(getApplicationContext(), "验证码校验成功", Toast.LENGTH_SHORT).show();
                    Message sms_msg = new Message();
                    sms_msg.what = SMS_SUCCESS;
                    SMSVerifyHandler.sendMessage(sms_msg);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) //服务器验证码发送成功
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {//返回支持发送验证码的国家列表
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();

                }
            } else {
                if (verify_cord) {
                    ((Throwable) data).printStackTrace();
                    Message sms_msg = new Message();
                    sms_msg.what = SMS_FAILED;
                    SMSVerifyHandler.sendMessage(sms_msg);
                } else {
                    Toast.makeText(getApplicationContext(), "验证码获取失败!", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };


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


    /**
     * begin countdown for re-request SMS Verification code
     */
    private void startCountDown() {
        current = COUNTDOWN_TIME;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new CountDownTask(), 0, 1000);
    }


    /**
     * update seconds in timer
     */
    private void updateTimer() {
        if (!reget_permission) {
            current--;
            requestCordButton.setText(current + "s");
            if (current == 0) {
                requestCordButton.setText("重新获取");
                reget_permission = true;
                requestCordButton.setEnabled(true);
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
>>>>>>> ttr
    }
}
