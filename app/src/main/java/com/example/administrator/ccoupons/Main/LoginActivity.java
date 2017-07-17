package com.example.administrator.ccoupons.Main;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.PasswordEncoder;
import com.example.administrator.ccoupons.Tools.SlideBackActivity;

import java.security.NoSuchAlgorithmException;


public class LoginActivity extends AppCompatActivity {

    Button login;
    Toolbar toolbar;
    EditText signup_phone, signup_pass;
    private int mergeHeight;
    private boolean editTextFocus = false;
    private LoginInformationManager loginInformationManager;
    private boolean auto_login;
    private String rem_phonenumber;
    private String rem_pass;


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
        rem_phonenumber = loginInformationManager.getPhoneNumber();
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
                String phonenumber = signup_phone.getText().toString();
                String password = signup_pass.getText().toString();
                String passwordcode;
                //如果不存在保存的密码，则将密码加密
                if (rem_pass.equals("")) {
                    PasswordEncoder encoder = new PasswordEncoder();
                    try {
                        passwordcode = encoder.EncodeByMd5(password);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        passwordcode = "";
                    }
                } else passwordcode = password;
                //向后台发送手机号与密码并验证
                //判断
                //- 失败
                //- 网络无连接
                //- 成功，并收到服务器的消息

                //保存账号与密码
                loginInformationManager.setAutoLogin(true).setPhoneNumber(phonenumber).setPassword(passwordcode);
                Toast.makeText(getApplicationContext(), "登录成功\n账号:" + phonenumber +
                        "\n密码:" + passwordcode, Toast.LENGTH_SHORT).show();    //fortest
                startActivity(new Intent(LoginActivity.this, MainPageActivity.class));
                finish();
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