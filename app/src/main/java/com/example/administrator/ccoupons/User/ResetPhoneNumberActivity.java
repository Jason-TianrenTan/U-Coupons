package com.example.administrator.ccoupons.User;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.DataBase.UserInfoManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ResetPhoneNumberActivity extends AppCompatActivity {

    public static final int COUNTDOWN_TIME = 30;
    public static final int SMS_FAILED = 1;//验证失败
    public static final int SMS_SUCCESS = 2;//验证通过
    private Toolbar toolbar;
    private boolean verify_cord = false, valid = false;
    private String phoneString;

    private TextView requestCordButton;
    private Button button_next;
    private EditText phoneText, cordText;
    private TextInputLayout phoneInputLayout, cordInputLayout;

    //reset timer
    private int current = COUNTDOWN_TIME;
    private boolean reget_permission = false;

    private void bindViews() {
        requestCordButton = (TextView) findViewById(R.id.update_request_cord_button);
        button_next = (Button) findViewById(R.id.update_button_ok);
        phoneText = (EditText) findViewById(R.id.update_phone_edittext);
        cordText = (EditText) findViewById(R.id.update_cord_edittext);
        phoneInputLayout = (TextInputLayout) findViewById(R.id.update_phone_inputlayout);
        cordInputLayout = (TextInputLayout) findViewById(R.id.update_cord_inputlayout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_phone_number);

        bindViews();
        toolbar = (Toolbar) findViewById(R.id.update_phone_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        requestCordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneString = phoneText.getText().toString();
                if (phoneString.length() < 11) {
                    phoneInputLayout.setError("长度必须为11位");
                } else {
                    sendSMS();
                    requestCordButton.setEnabled(false);
                    startCountDown();
                }
            }
        });

        phoneText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = phoneText.getText().toString();
                if (str.length() == 11) {
                    phoneInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valid = true;
                String cord = cordText.getText().toString();
                if (cord.length() < 4)
                    cordInputLayout.setError("验证码必须为4位");
                if (valid) {
                    String iCord = cordText.getText().toString().trim();
                    SMSSDK.submitVerificationCode("86", phoneString, iCord);//验证验证码
                    verify_cord = true;
                }
            }
        });

        cordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = cordText.getText().toString();
                if (str.length() == 4) {
                    cordInputLayout.setError("");
                    cordInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
    }


    private void sendSMS() {
        //发送验证码
        System.out.println("Sent SMS code to +86" + phoneString.trim());
        SMSSDK.getVerificationCode("86", phoneString.trim());//请求获取短信验证码
    }

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
                    String url = GlobalConfig.base_URL + GlobalConfig.updatePhoneNumber_URL;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("userID", ((MyApp) getApplicationContext()).getUserId());
                    map.put("username", phoneString);
                    ConnectionManager connectionManager = new ConnectionManager(url, map);
                    connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
                        @Override
                        public void onConnectionSuccess(String response) {
                            Toast.makeText(getApplicationContext(), "修改手机号成功", Toast.LENGTH_SHORT).show();
                            updateUserInfo(phoneString);
                            Intent intent = new Intent(ResetPhoneNumberActivity.this, MainPageActivity.class);
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

    private void startCountDown() {
        current = COUNTDOWN_TIME;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new ResetPhoneNumberActivity.CountDownTask(), 0, 1000);
    }

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
    }

    private void updateUserInfo(String newPhoneNum){
        LoginInformationManager loginInformationManager = new LoginInformationManager(this);
        UserInfoManager oldUserInfoManager = new UserInfoManager(this);
        ArrayList<String> old = oldUserInfoManager.getHistoryList();
        loginInformationManager.setUsername(newPhoneNum);
        UserInfoManager newUserInfoManager = new UserInfoManager(this);
        newUserInfoManager.setHistory(old);
    }
}
