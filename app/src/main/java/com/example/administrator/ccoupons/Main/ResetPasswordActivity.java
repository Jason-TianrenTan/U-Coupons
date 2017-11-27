package com.example.administrator.ccoupons.Main;

<<<<<<< HEAD
=======
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
>>>>>>> Czj
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
import com.example.administrator.ccoupons.User.UserUpdateGenderActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResetPasswordActivity extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    public static final int COUNTDOWN_TIME = 30;
    public static final int SMS_FAILED = 1;//验证失败
    public static final int SMS_SUCCESS = 2;//验证通过
    private boolean verify_cord = false, valid = false;
    private String phoneString;
    private String password;

    @BindView(R.id.reset_pass_toolbar)
    Toolbar resetPassToolbar;
    @BindView(R.id.reset_phone_edittext)
    EditText resetPhoneEdittext;
    @BindView(R.id.reset_phone_inputlayout)
    TextInputLayout resetPhoneInputlayout;
    @BindView(R.id.reset_cord_edittext)
    EditText resetCordEdittext;
    @BindView(R.id.reset_cord_inputlayout)
    TextInputLayout resetCordInputlayout;
    @BindView(R.id.request_cord_button)
    TextView requestCordButton;
    @BindView(R.id.reset_newpass_edittext)
    PasswordToggleEditText resetNewpassEdittext;
    @BindView(R.id.reset_newpass_inputlayout)
    TextInputLayout resetNewpassInputlayout;
    @BindView(R.id.reset_confirmpass_edittext)
    PasswordToggleEditText resetConfirmpassEdittext;
    @BindView(R.id.reset_confirmpass_inputlayout)
    TextInputLayout resetConfirmpassInputlayout;
    @BindView(R.id.reset_button_ok)
    Button resetButtonOk;

    @OnClick({R.id.request_cord_button, R.id.reset_button_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.request_cord_button:
                phoneString = resetPhoneEdittext.getText().toString();
                if (phoneString.length() < 11) {
                    resetPhoneInputlayout.setError("长度必须为11位");
                } else {
                    sendSMS();
                    requestCordButton.setEnabled(false);
                    startCountDown();
                }
                break;
            case R.id.reset_button_ok:
                String cord = resetCordEdittext.getText().toString();
                if (cord.length() < 4)
                    resetCordInputlayout.setError("验证码必须为4位");
                if (valid) {
                    String iCord = resetCordEdittext.getText().toString().trim();
                    SMSSDK.submitVerificationCode("86", phoneString, iCord);//验证验证码
                    verify_cord = true;
                }
                break;
        }
    }

    //reset timer
    private int current = COUNTDOWN_TIME;
    private boolean reget_permission = false;


    private String[] errorStrings = "不能含有非法字符,长度必须为6~16位,密码强度太弱".split(",");
    private RegisterCheck checker = new RegisterCheck();


<<<<<<< HEAD
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
=======
>>>>>>> Czj
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);

<<<<<<< HEAD
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
=======
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

        String intentString = getIntent().getStringExtra("phoneString");
        if (intentString != null)
            resetPhoneEdittext.setText(intentString);
    }

    private void initEditText() {
        resetPhoneEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = resetPhoneEdittext.getText().toString();
                if (str.length() == 11) {
                    resetPhoneInputlayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        resetCordEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = resetCordEdittext.getText().toString();
                if (str.length() == 4) {
                    resetCordInputlayout.setError("");
                    resetCordInputlayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        resetNewpassEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = resetNewpassEdittext.getText().toString();
                int err_type = checker.alertPassword(password);
                if (err_type != AlertType.NO_ERROR && err_type != AlertType.TOO_SIMPLE) {
                    EditTextTools.setCursorColor(resetNewpassEdittext, getResources().getColor(R.color.red));
                    resetNewpassInputlayout.setErrorEnabled(true);
                    resetNewpassInputlayout.setError(errorStrings[err_type - 1]);
                    valid = false;
                } else {
                    if (err_type == AlertType.NO_ERROR) {
                        //密码合格
                        EditTextTools.setCursorColor(resetNewpassEdittext, getResources().getColor(R.color.colorAccent));
                        resetNewpassInputlayout.setError("");
                        resetNewpassInputlayout.setErrorEnabled(false);
                        valid = true;
                    } else {
                        //强度不够
                        EditTextTools.setCursorColor(resetNewpassEdittext, getResources().getColor(R.color.skyblue));
                        resetNewpassInputlayout.setErrorEnabled(true);
                        resetNewpassInputlayout.setError(errorStrings[err_type - 1]);
                    }
                }
            }
        });

        resetConfirmpassEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = resetConfirmpassEdittext.getText().toString();
                int err_type = checker.alertPassword(password);
                if (err_type != AlertType.NO_ERROR && err_type != AlertType.TOO_SIMPLE) {
                    EditTextTools.setCursorColor(resetConfirmpassEdittext, getResources().getColor(R.color.red));
                    resetConfirmpassInputlayout.setError(errorStrings[err_type - 1]);
                    valid = false;
                } else {
                    if (resetNewpassEdittext.getText().toString().equals(resetConfirmpassEdittext.getText().toString())) {
                        EditTextTools.setCursorColor(resetConfirmpassEdittext, getResources().getColor(R.color.colorAccent));
                        resetConfirmpassInputlayout.setErrorEnabled(false);
                        valid = true;
                    } else {
                        EditTextTools.setCursorColor(resetConfirmpassEdittext, getResources().getColor(R.color.red));
                        resetConfirmpassInputlayout.setError("两次密码不匹配");
                        valid = false;
                    }
                }
            }
        });
    }
>>>>>>> Czj

    private void initToolbar() {
        setSupportActionBar(resetPassToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        resetPassToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                    resetCordEdittext.setText("");
                    verify_cord = false;
                    break;
                case SMS_SUCCESS:
                    valid = true;
                    verify_cord = false;
                    phoneString = resetPhoneEdittext.getText().toString();
                    password = resetNewpassEdittext.getText().toString();
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

    private void parseMessage(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("result")) {
                String result = jsonObject.getString("result");
                if (result.equals("200")) {
                    Toast.makeText(getApplicationContext(), "修改密码成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResetPasswordActivity.this, MainPageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
            if (jsonObject.has("error")) {
                String error = jsonObject.getString("error");
                if (error.equals("108")) {
                    Toast.makeText(ResetPasswordActivity.this, "旧密码与新密码相同", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "好像出了点问题哟", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
