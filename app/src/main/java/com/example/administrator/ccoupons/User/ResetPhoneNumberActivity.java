package com.example.administrator.ccoupons.User;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
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
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResetPhoneNumberActivity extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    public static final int COUNTDOWN_TIME = 30;
    public static final int SMS_FAILED = 1;//验证失败
    public static final int SMS_SUCCESS = 2;//验证通过
    private boolean verify_cord = false, valid = false;
    private String phoneString;
    //reset timer
    private int current = COUNTDOWN_TIME;
    private boolean reget_permission = false;
    private String url = GlobalConfig.base_URL + GlobalConfig.updatePhoneNumber_URL;
    //Todo:失败可能会跳转到设定密码的界面？ 尚未解决的未知BUG

    @BindView(R.id.update_phone_toolbar)
    Toolbar toolbar;
    @BindView(R.id.update_request_cord_button)
    TextView requestCordButton;
    @BindView(R.id.update_button_ok)
    Button button_next;
    @BindView(R.id.update_phone_edittext)
    EditText phoneText;
    @BindView(R.id.update_cord_edittext)
    EditText cordText;
    @BindView(R.id.update_phone_inputlayout)
    TextInputLayout phoneInputLayout;
    @BindView(R.id.update_cord_inputlayout)
    TextInputLayout cordInputLayout;
    @BindView(R.id.update_timer_text)
    TextView timerText;

    @OnClick({R.id.update_request_cord_button, R.id.update_button_ok})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.update_request_cord_button:
                ClickRequestCordButton();
                break;
            case R.id.update_button_ok:
                ClickOkButton();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_phone_number);
        ButterKnife.bind(this);
        initToolbar();
        initEditText();

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


    /**
     * init toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * init edit text view
     */
    private void initEditText() {
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
    }


    /**
     * init ClickRequestCordButton
     */
    private void ClickRequestCordButton() {
        phoneString = phoneText.getText().toString();
        if (phoneString.length() < 11) {
            phoneInputLayout.setError("长度必须为11位");
        } else {
            sendSMS();
            requestCordButton.setText("重新发送");
            requestCordButton.setEnabled(false);
            startCountDown();
        }
    }


    /**
     * init ClickOkButton
     */
    private void ClickOkButton() {
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


    /**
     * send SMS
     */
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
                    ZLoadingDialog dialog = new ZLoadingDialog(ResetPhoneNumberActivity.this);
                    dialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)
                            .setLoadingColor(ContextCompat.getColor(ResetPhoneNumberActivity.this, R.color.colorPrimary))
                            .setCanceledOnTouchOutside(false)
                            .show();
                    ConnectionManager connectionManager = new ConnectionManager(url, map, dialog);
                    connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
                        @Override
                        public void onConnectionSuccess(String response) {
                            System.out.println(response.toString());
                            parseMessage(response);
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
     * start timer
     */
    private void startCountDown() {
        current = COUNTDOWN_TIME;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new ResetPhoneNumberActivity.CountDownTask(), 0, 1000);
    }


    /**
     * update timer
     */
    private void updateTimer() {
        if (!reget_permission) {
            current--;
            timerText.setText(current + getResources().getString(R.string.register_indentify_timertext));
            if (current == 0) {
                timerText.setText("");
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


    /**
     * update saved uset information
     * @param newPhoneNum
     */
    private void updateUserInfo(String newPhoneNum) {
        LoginInformationManager loginInformationManager = new LoginInformationManager(this);
        UserInfoManager oldUserInfoManager = new UserInfoManager(this);
        ArrayList<String> old = oldUserInfoManager.getHistoryList();
        loginInformationManager.setUsername(newPhoneNum);
        UserInfoManager newUserInfoManager = new UserInfoManager(this);
        newUserInfoManager.setHistory(old);
    }


    /**
     * parse the massage json
     * @param response
     */
    private void parseMessage(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("result")) {
                String result = jsonObject.getString("result");
                if (result.equals("200")) {
                    Toast.makeText(getApplicationContext(), "修改手机号成功", Toast.LENGTH_SHORT).show();
                    updateUserInfo(phoneString);
                    Intent intent = new Intent(ResetPhoneNumberActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
            }
            if (jsonObject.has("error")) {
                String error = jsonObject.getString("error");
                if (error.equals("109")) {
                    Toast.makeText(ResetPhoneNumberActivity.this, "手机或邮箱已存在", Toast.LENGTH_SHORT).show();
                } else if (error.equals("111")) {
                    Toast.makeText(ResetPhoneNumberActivity.this, "该账户使用手机注册", Toast.LENGTH_SHORT).show();
                } else if (error.equals("112")) {
                    Toast.makeText(ResetPhoneNumberActivity.this, "该账户使用邮箱注册", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ResetPhoneNumberActivity.this, "好像出了点问题哟", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
