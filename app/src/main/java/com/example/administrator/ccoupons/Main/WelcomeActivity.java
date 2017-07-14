package com.example.administrator.ccoupons.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.ccoupons.MainPageActivity;
import com.example.administrator.ccoupons.R;

public class WelcomeActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private boolean auto_login;
    String account;
    String key;
    Button login;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        login = (Button) findViewById(R.id.welcome_login_button);
        register = (Button) findViewById(R.id.welcome_register_button);
        preferences = this.getSharedPreferences("UserInfomation", MODE_PRIVATE);
        auto_login = preferences.getBoolean("auto_login", false);
        if (auto_login == false) {
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);
        }
        if (auto_login == true) {
            account = preferences.getString("account", "");
            key = preferences.getString("key", "");
            //向服务器发送账号密码并验证
            //判断
            //失败
            //网络无连接
            //成功，并收到服务器的消息
            startActivity(new Intent(WelcomeActivity.this, MainPageActivity.class));
        }
    }
}

