package com.example.administrator.ccoupons.Register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.Gender;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.AlertType;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.PasswordEncoder;
import com.example.administrator.ccoupons.Tools.RegisterCheck;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

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

public class RegisterNewActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public static final int SMS_FAILED = 1;//验证失败
    public static final int SMS_SUCCESS = 2;//验证通过
    private boolean verify_cord = false;
    private boolean reget_permission = false;
    public static final int COUNTDOWN_TIME = 30;
    int current = COUNTDOWN_TIME;

    private RegisterCheck checker;

    private String[] GenderChars = {"男", "女"};
    private LoginInformationManager loginInformationManager;
    private final static String requestURL = GlobalConfig.base_URL + GlobalConfig.register_URL;

    @BindView(R.id.register_main_toolbar_new)
    Toolbar toolbar;

    //EditTexts and Holder
    @BindView(R.id.register_phone_input_new)
    EditText registerPhoneInputNew;
    @BindView(R.id.register_phone_inputlayout_new)
    TextInputLayout registerPhoneInputlayoutNew;
    @BindView(R.id.register_identify_input_new)
    EditText registerIdentifyInputNew;
    @BindView(R.id.register_identify_inputlayout_new)
    TextInputLayout registerIdentifyInputlayoutNew;
    @BindView(R.id.register_firstpassword_input_new)
    EditText registerFirstpasswordInputNew;
    @BindView(R.id.register_firstpassword_inputlayout_new)
    TextInputLayout registerFirstpasswordInputlayoutNew;
    @BindView(R.id.register_confirmpass_input_new)
    EditText registerConfirmpassInputNew;
    @BindView(R.id.register_confirmpassword_inputlayout_new)
    TextInputLayout registerConfirmpasswordInputlayoutNew;
    @BindView(R.id.register_nickname_input_new)
    EditText registerNicknameInputNew;
    @BindView(R.id.register_nickname_inputlayout_new)
    TextInputLayout registerNicknameInputlayoutNew;

    //RadioButtons
    @BindView(R.id.register_final_radiogroup_new)
    RadioGroup registerFinalRadiogroupNew;
    @BindView(R.id.radio_button_male_new)
    RadioButton radioButtonMaleNew;
    @BindView(R.id.radio_button_female_new)
    RadioButton radioButtonFemaleNew;

    //buttons
    @BindView(R.id.register_button_finish_new)
    Button button_finish;
    @BindView(R.id.register_identify_button_new)
    Button registerIdentifyButtonNew;
    @BindView(R.id.text_return_new)
    TextView text_return;

    @OnClick({R.id.text_return_new, R.id.register_button_finish_new, R.id.register_identify_button_new})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.text_return_new:
                finish();
                break;
            case R.id.register_button_finish_new:
                Submit();
                break;
            case R.id.register_identify_button_new:
                sendSMS();
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);
        ButterKnife.bind(this);
        checker = new RegisterCheck();
        initToolbar();
        initEditText();
        initRadioButton();
        initSMS();
    }


    /**
     * init edit text view
     */
    private void initEditText() {
        registerPhoneInputNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = registerPhoneInputNew.getText().toString();
                switch (checker.alertPhoneNumber(str)) {
                    case AlertType.LENGTH_ERROR:
                        registerPhoneInputlayoutNew.setErrorEnabled(true);
                        registerPhoneInputlayoutNew.setError(AlertType.LENGTH_ERROR_PHONE_STR);
                        break;
                    case AlertType.ILLEGAL_CHAR:
                        registerPhoneInputlayoutNew.setErrorEnabled(true);
                        registerPhoneInputlayoutNew.setError(AlertType.ILLEGAL_CHAR_STR);
                        break;
                    case AlertType.NO_ERROR:
                        registerPhoneInputlayoutNew.setErrorEnabled(false);
                        break;
                }
            }
        });

        registerIdentifyInputNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = registerIdentifyInputNew.getText().toString();
                switch (checker.alertIdentifyCode(str)) {
                    case AlertType.LENGTH_ERROR:
                        registerIdentifyInputlayoutNew.setErrorEnabled(true);
                        registerIdentifyInputlayoutNew.setError(AlertType.LENGTH_ERROR_CODE_STR);
                        break;
                    case AlertType.ILLEGAL_CHAR:
                        registerIdentifyInputlayoutNew.setErrorEnabled(true);
                        registerIdentifyInputlayoutNew.setError(AlertType.ILLEGAL_CHAR_STR);
                        break;
                    case AlertType.NO_ERROR:
                        registerIdentifyInputlayoutNew.setErrorEnabled(false);
                        break;
                }
            }
        });

        registerFirstpasswordInputNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                registerConfirmpassInputNew.setText("");
                registerConfirmpasswordInputlayoutNew.setError("");
                registerConfirmpasswordInputlayoutNew.setErrorEnabled(false);
                String str = registerFirstpasswordInputNew.getText().toString();
                switch (checker.alertPassword(str)) {
                    case AlertType.LENGTH_ERROR:
                        registerFirstpasswordInputlayoutNew.setErrorEnabled(true);
                        registerFirstpasswordInputlayoutNew.setError(AlertType.LENGTH_ERROR_PASSWORD_STR);
                        break;
                    case AlertType.ILLEGAL_CHAR:
                        registerFirstpasswordInputlayoutNew.setErrorEnabled(true);
                        registerFirstpasswordInputlayoutNew.setError(AlertType.ILLEGAL_CHAR_STR);
                        break;
                    case AlertType.TOO_SIMPLE:
                        registerFirstpasswordInputlayoutNew.setErrorEnabled(true);
                        registerFirstpasswordInputlayoutNew.setError(AlertType.TOO_SIMPLE_STR);
                        break;
                    case AlertType.NO_ERROR:
                        registerFirstpasswordInputlayoutNew.setErrorEnabled(false);
                        break;
                }
            }
        });

        registerConfirmpassInputNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pass = registerFirstpasswordInputNew.getText().toString();
                String confirm = registerConfirmpassInputNew.getText().toString();
                if (!pass.equals(confirm)) {
                    registerConfirmpasswordInputlayoutNew.setErrorEnabled(true);
                    registerConfirmpasswordInputlayoutNew.setError("两次密码不匹配");
                } else {
                    registerConfirmpasswordInputlayoutNew.setErrorEnabled(false);
                }
            }
        });
    }

    /**
     * init RadioButton
     */
    private void initRadioButton() {
        radioButtonMaleNew.setChecked(true);
        gender = Gender.MALE;
        registerFinalRadiogroupNew.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int buttonId = radioGroup.getCheckedRadioButtonId();
                if (buttonId == R.id.radio_button_male)
                    gender = Gender.MALE;
                else gender = Gender.FEMALE;
            }
        });
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
     * init SMS
     */
    private void initSMS() {
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
     * check the entered info
     *
     * @return true for pass, false for error
     */
    private boolean checkEnter() {
        EditText[] editTexts = {registerPhoneInputNew, registerIdentifyInputNew, registerFirstpasswordInputNew, registerConfirmpassInputNew, registerNicknameInputNew};
        ButterKnife.Action<View> checkEmpty = new ButterKnife.Action<View>() {
            @Override
            public void apply(@NonNull View view, int index) {
                EditText v = (EditText) view;
                if (v.getText().toString().equals("")) {
                    TextInputLayout parent = (TextInputLayout) view.getParentForAccessibility();
                    parent.setErrorEnabled(true);
                    parent.setError("请输入" + parent.getHint().toString());
                }
            }
        };
        ButterKnife.apply(editTexts, checkEmpty);
        if (registerPhoneInputlayoutNew.isErrorEnabled() ||
                registerIdentifyInputlayoutNew.isErrorEnabled() ||
                registerFirstpasswordInputlayoutNew.isErrorEnabled() ||
                registerConfirmpasswordInputlayoutNew.isErrorEnabled() ||
                registerNicknameInputlayoutNew.isErrorEnabled()) {
            Toast.makeText(RegisterNewActivity.this, "信息有误", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /**
     * request SMS Code from server
     */
    private void sendSMS() {
        //发送验证码
        if (registerPhoneInputlayoutNew.isErrorEnabled()) {
            registerIdentifyInputlayoutNew.setErrorEnabled(true);
            registerIdentifyInputlayoutNew.setError("请正确输入手机号");
        } else {
            registerIdentifyInputlayoutNew.setErrorEnabled(false);
            String phoneString = registerPhoneInputNew.getText().toString();
            System.out.println("Sent SMS code to +86" + phoneString.trim());
            SMSSDK.getVerificationCode("86", phoneString.trim());//请求获取短信验证码

            reget_permission = false;
            registerIdentifyButtonNew.setEnabled(false);
            startCountDown();
        }
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


    /**
     * SMS timer
     */
    private void updateTimer() {
        if (!reget_permission) {
            current--;
            registerIdentifyButtonNew.setText(current + getResources().getString(R.string.register_indentify_timertext));
            if (current == 0) {
                registerIdentifyButtonNew.setText(getResources().getString(R.string.register_indentify_resent_text));
                reget_permission = true;
                registerIdentifyButtonNew.setEnabled(true);
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
     * start countdown
     */
    private void startCountDown() {
        current = COUNTDOWN_TIME;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new RegisterNewActivity.CountDownTask(), 0, 1000);
    }


    /**
     * check the SMS
     *
     * @return true for pass, false for error
     */
    private void checkSMS() {
        String iCord = registerIdentifyInputNew.getText().toString().trim();
        String phoneString = registerPhoneInputNew.getText().toString();
        SMSSDK.submitVerificationCode("86", phoneString, iCord);//验证验证码
    }


    private Handler SMSVerifyHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SMS_FAILED:
                    registerIdentifyInputlayoutNew.setErrorEnabled(true);
                    registerIdentifyInputlayoutNew.setError("验证码错误!");
                    verify_cord = false;
                    break;
                case SMS_SUCCESS:
                    verify_cord = true;
                    System.out.println("SMS验证成功");
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
                    //Toast.makeText(getApplicationContext(), "验证码校验成功", Toast.LENGTH_SHORT).show();
                    Message sms_msg = new Message();
                    sms_msg.what = SMS_SUCCESS;
                    SMSVerifyHandler.sendMessage(sms_msg);
                    Register();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) //服务器验证码发送成功
                    Toast.makeText(RegisterNewActivity.this, getResources().getString(R.string.register_identify_text) + " +86 " + registerPhoneInputNew.getText().toString(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
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


    /**
     * Submit
     */
    private void Submit() {
        if (checkEnter()) {
            checkSMS();
        }
    }


    private int gender;
    private String phoneString, password, nickname;


    /**
     * set register information
     */
    private void setRegisterInfo(){
        phoneString = registerPhoneInputNew.getText().toString();
        password = registerFirstpasswordInputNew.getText().toString();
        nickname = registerNicknameInputNew.getText().toString();
    }

    /**
     * Register
     */
    private void Register() {
        setRegisterInfo();

        String url_str = requestURL;
        String md5pass = null;
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            md5pass = new PasswordEncoder().EncodeByMd5(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("username", phoneString);
        map.put("password", md5pass);
        map.put("nickname", nickname);
        map.put("gender", GenderChars[gender]);

        ZLoadingDialog dialog = new ZLoadingDialog(RegisterNewActivity.this);
        dialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)
                .setLoadingColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCanceledOnTouchOutside(false)
                .show();
        ConnectionManager connectionManager = new ConnectionManager(url_str, map, dialog);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
                parseMessage(response);
            }

            @Override
            public void onConnectionTimeOut() {
                Toast.makeText(getApplicationContext(), "连接服务器超时，请稍后再试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onConnectionFailed() {
                Toast.makeText(getApplicationContext(), "连接服务器遇到问题，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
        connectionManager.connect();
    }


    /**
     * parse response string from server
     * @param response
     */
    private void parseMessage(String response) {
        System.out.println("response = " + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String errno = jsonObject.getString("errno");
            if (errno.equals("1")) {
                //注册失败
                Toast.makeText(getApplicationContext(), "手机号已经存在", Toast.LENGTH_SHORT).show();
                registerPhoneInputlayoutNew.setErrorEnabled(true);
                registerPhoneInputlayoutNew.setError("手机号已经存在");
            } else {
                loginInformationManager.setAutoLogin(true).setUsername(phoneString).setPassword(password);
                Intent intent = new Intent(RegisterNewActivity.this, MainPageActivity.class);
                intent.putExtra("username", phoneString).putExtra("password", password);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

