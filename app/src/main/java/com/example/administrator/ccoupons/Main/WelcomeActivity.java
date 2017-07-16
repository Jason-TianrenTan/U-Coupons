package com.example.administrator.ccoupons.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Register.RegisterActivity;
import com.example.administrator.ccoupons.Tools.LoginInformationManager;

public class WelcomeActivity extends AppCompatActivity {
    private LoginInformationManager loginInformationManager;
    private boolean auto_login;
    private String phonenumber;
    private String password;
    Button login;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        login = (Button) findViewById(R.id.welcome_login_button);
        register = (Button) findViewById(R.id.welcome_register_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
                finish();
            }
        });
        loginInformationManager = new LoginInformationManager(this.getSharedPreferences("UserInfomation", MODE_PRIVATE));
        auto_login = loginInformationManager.getAutoLogin();
        if (auto_login == false) {
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);
        }
        if (auto_login == true) {
            phonenumber = loginInformationManager.getPhoneNumber();
            password = loginInformationManager.getPassword();
            //向服务器发送账号密码并验证
            //判断
            //- 失败
            //- 网络无连接
            //- 成功，并收到服务器的消息
            Toast.makeText(getApplicationContext(), "登录成功\n账号:" + phonenumber +
                    "\n密码:" + password, Toast.LENGTH_SHORT).show();    //fortest
            startActivity(new Intent(WelcomeActivity.this, MainPageActivity.class));
            finish();
        }
    }
}

