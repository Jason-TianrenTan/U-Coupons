package com.example.administrator.ccoupons.Main;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.LoginThread;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.MessageType;
import com.example.administrator.ccoupons.UI.CustomDialog;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {


    private static String url = "http://192.168.204.83:8000/post_loginForAndroid";
    private LoginThread thread;
    private Button login;
    private Toolbar toolbar;
    private EditText signup_phone, signup_pass;
    private String myUsername, myPassword;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessageType.CONNECTION_ERROR:
                    Toast.makeText(getApplicationContext(), "连接服务器遇到问题，请检查网络连接!", Toast.LENGTH_LONG).show();
                    login.setEnabled(true);
                    break;
                case MessageType.CONNECTION_TIMEOUT:
                    Toast.makeText(getApplicationContext(), "连接服务器超时，请检查网络连接!", Toast.LENGTH_LONG).show();
                    login.setEnabled(true);
                    break;
                case MessageType.CONNECTION_SUCCESS:
                    parseMessage(thread.getResponse());
                    break;
            }
        }
    };

    private int mergeHeight;
    private boolean editTextFocus = false;
    private LoginInformationManager loginInformationManager;
    //    private boolean auto_login;
    private String rem_phonenumber;
    private String rem_pass;

    private void saveUserLoginInfo() {
        loginInformationManager.setAutoLogin(true).setUsername(myUsername).setPassword(myPassword);
    }

    //处理返回回来的json
    private void parseMessage(String response) {
        if (response.indexOf("result") != -1) {
            System.out.println("Login success");
            try {
                JSONObject jsonObject = new JSONObject(response);
                String userId = jsonObject.getString("userid");
                MyApp app = (MyApp) getApplicationContext();
                app.setUserId(userId);
                Toast.makeText(getApplicationContext(), "登录成功\n账号:" + myUsername +
                        "\n密码:" + myPassword, Toast.LENGTH_SHORT).show();
                saveUserLoginInfo();//缓存密码


                Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                intent.putExtra("username", myUsername);
                intent.putExtra("password", myPassword);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Login failed");
            Toast.makeText(getApplicationContext(), "用户名/密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //    getSupportActionBar().hide();

        toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                finish();
            }
        });

        login = (Button) findViewById(R.id.button_login);
        signup_phone = (EditText) findViewById(R.id.signup_phone);
        signup_phone.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        signup_pass = (EditText) findViewById(R.id.signup_password);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        loginInformationManager = new LoginInformationManager(this.getSharedPreferences("UserInfomation", MODE_PRIVATE));

        //读取记忆的账号
        rem_phonenumber = loginInformationManager.getUsername();
        rem_pass = loginInformationManager.getPassword();
        signup_phone.setText(rem_phonenumber);
        signup_pass.setText(rem_pass);

        signup_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editTextFocus = true;
                if (!editTextFocus) {
                    hideKeyboard(v);
                }
            }
        });
        signup_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editTextFocus = true;
                if (!editTextFocus) {
                    hideKeyboard(v);
                }
            }
        });

        //登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = signup_phone.getText().toString();
                String password = signup_pass.getText().toString();

                login.setEnabled(false);
                if (password != null) {
                    requestLogin(url, username, password);
                }
                //finish();
            }
        });

        TextView text_forget = (TextView) findViewById(R.id.text_forget);
        text_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.rootLayout);
        mergeHeight = dp2px(120);

        //
        SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(findViewById(R.id.rootLayout));
        softKeyboardStateHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                //键盘打开
                if (!stateShrinked)
                    startAnimation(ANIM_SHRINK);
            }

            @Override
            public void onSoftKeyboardClosed() {
                //键盘关闭
                if (stateShrinked)
                    startAnimation(ANIM_EXPAND);
            }
        });
    }

    //登录
    private void requestLogin(String url, String username, String password) {
        myUsername = username;
        myPassword = password;
        thread = new LoginThread(url, username, password, handler, getApplicationContext());
        thread.start();
        //TODO 播放动画
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private boolean stateShrinked = false;
    private static final int ANIM_SHRINK = 0,
            ANIM_EXPAND = 1;

    private void startAnimation(int anim_type) {

        if (anim_type == ANIM_SHRINK) {

            LinearLayout textRoot = (LinearLayout) findViewById(R.id.text_root_view);
            ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(textRoot, "y", textRoot.getTop(), textRoot.getTop() - mergeHeight)
                    .setDuration(500);
            heightAnimator.start();

            RelativeLayout imgLayout = (RelativeLayout) findViewById(R.id.imglayout);
            Animation anim = new AnimationUtils().loadAnimation(this, R.anim.image_shrink);
            anim.setFillAfter(true);
            imgLayout.startAnimation(anim);
            stateShrinked = true;
        }
        if (anim_type == ANIM_EXPAND) {

            LinearLayout textRoot = (LinearLayout) findViewById(R.id.text_root_view);
            ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(textRoot, "y", textRoot.getTop() - mergeHeight, textRoot.getTop())
                    .setDuration(500);
            heightAnimator.start();

            RelativeLayout imgLayout = (RelativeLayout) findViewById(R.id.imglayout);
            Animation anim = new AnimationUtils().loadAnimation(this, R.anim.image_expand);
            anim.setFillAfter(true);
            imgLayout.startAnimation(anim);
            stateShrinked = false;
        }

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
        super.onBackPressed();
    }
}
