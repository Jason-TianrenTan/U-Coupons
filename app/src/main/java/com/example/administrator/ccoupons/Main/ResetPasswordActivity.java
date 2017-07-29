package com.example.administrator.ccoupons.Main;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.CustomEditText.PasswordToggleEditText;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.AlertType;
import com.example.administrator.ccoupons.Tools.EditTextTools;
import com.example.administrator.ccoupons.Tools.RegisterCheck;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ResetPasswordActivity extends AppCompatActivity {

    private RegisterCheck checker = new RegisterCheck();
    public static final int SMS_FAILED = 1;//验证失败
    public static final int SMS_SUCCESS = 2;//验证通过
    Toolbar toolbar;
    boolean verify_cord = false, valid = false;
    String phoneString;
    String password;

    TextView requestCordButton;
    Button button_next;
    EditText phoneText, cordText;
    PasswordToggleEditText passText, confirmText;
    TextInputLayout phoneInputLayout, cordInputLayout, passInputLayout, confirmInputLayout;
    private String[] errorStrings = "不能含有非法字符,长度必须为6~16位,密码强度太弱".split(",");
    
    private void bindViews() {
        requestCordButton = (TextView) findViewById(R.id.request_cord_button);
        button_next = (Button)findViewById(R.id.reset_button_ok);
        phoneText = (EditText)findViewById(R.id.reset_phone_edittext);
        cordText = (EditText)findViewById(R.id.reset_cord_edittext);
        phoneInputLayout = (TextInputLayout)findViewById(R.id.reset_phone_inputlayout);
        cordInputLayout = (TextInputLayout) findViewById(R.id.reset_cord_inputlayout);
        passText = (PasswordToggleEditText)findViewById(R.id.reset_newpass_edittext);
        passInputLayout = (TextInputLayout)findViewById(R.id.reset_newpass_inputlayout);
        confirmText = (PasswordToggleEditText)findViewById(R.id.reset_confirmpass_edittext);
        confirmInputLayout = (TextInputLayout)findViewById(R.id.reset_confirmpass_inputlayout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        bindViews();
        toolbar = (Toolbar)findViewById(R.id.reset_pass_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        requestCordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneString = phoneText.getText().toString();
                if (phoneString.length() < 11) {
                    phoneInputLayout.setError("长度必须为11位");
                }
                else
                    sendSMS();
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

                String cord = cordText.getText().toString();
                if (cord.length() < 4)
                    cordInputLayout.setError("验证码必须为4位");
                else {
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
                if (str.length() ==4) {
                    cordInputLayout.setError("");
                    cordInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = passText.getText().toString();
                int err_type = checker.alertPassword(password);
                if (err_type != AlertType.NO_ERROR && err_type!=  AlertType.TOO_SIMPLE) {
                    EditTextTools.setCursorColor(passText, getResources().getColor(R.color.red));
                    passInputLayout.setErrorEnabled(true);
                    passInputLayout.setError(errorStrings[err_type - 1]);
                    valid = false;
                } else {
                    if (err_type == AlertType.NO_ERROR) {
                        //密码合格
                        EditTextTools.setCursorColor(passText, getResources().getColor(R.color.colorAccent));
                        passInputLayout.setError("");
                        passInputLayout.setErrorEnabled(false);
                        valid = true;
                    }
                    else {
                        //强度不够
                        EditTextTools.setCursorColor(passText, getResources().getColor(R.color.skyblue));
                        passInputLayout.setErrorEnabled(true);
                        passInputLayout.setError(errorStrings[err_type - 1]);
                    }
                }
            }
        });

        confirmText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = confirmText.getText().toString();
                int err_type = checker.alertPassword(password);
                if (err_type != AlertType.NO_ERROR && err_type!=  AlertType.TOO_SIMPLE) {
                    EditTextTools.setCursorColor(confirmText, getResources().getColor(R.color.red));
                    confirmInputLayout.setError(errorStrings[err_type - 1]);
                    valid = false;
                } else {
                    if (passText.getText().toString().equals(confirmText.getText().toString())) {
                        EditTextTools.setCursorColor(confirmText, getResources().getColor(R.color.colorAccent));
                        confirmInputLayout.setErrorEnabled(false);
                        valid = true;
                    } else {
                        EditTextTools.setCursorColor(confirmText, getResources().getColor(R.color.red));
                        confirmInputLayout.setError("两次密码不匹配");
                        valid = false;
                    }
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
                    password = passText.getText().toString();
                    String url = DataHolder.base_URL + DataHolder.resetPass_URL;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("phoneNum", phoneString);
                    map.put("password", password);
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
}
