package com.example.administrator.ccoupons.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Connections.UniversalPresenter;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.Gender;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Register.RegisterActivity;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.PasswordEncoder;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WelcomeActivity extends Activity {
    @BindView(R.id.Welcome_LoginButton)
    Button Welcome_LoginButton;
    @BindView(R.id.Welcome_RegisterButton)
    Button Welcome_RegisterButton;

    @OnClick({R.id.Welcome_LoginButton, R.id.Welcome_RegisterButton})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.Welcome_LoginButton:
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                break;
            case R.id.Welcome_RegisterButton:
                startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //startActivity(new Intent(WelcomeActivity.this, MainPageActivity.class));//Todo:for test
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
    }

}






















/*
public class WelcomeActivity extends AppCompatActivity {
    private static String url = DataHolder.base_URL + DataHolder.login_URL;
    private LoginInformationManager loginInformationManager;
    private boolean auto_login;
    private String username;
    private String password;

    Button login;
    Button register;

    //处理返回回来的json
    private void parseMessage(String response) {
        if (response.indexOf("result") != -1) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String userId = jsonObject.getString("userid");
                String nickname = jsonObject.getString("nickname");
                String avatar = jsonObject.getString("avatar");
                String sex = jsonObject.getString("gender");
                int UB = jsonObject.getInt("Ucoin");
                MyApp app = (MyApp) getApplicationContext();
                app.setUserId(userId);
                System.out.println("Response = " + response);
                System.out.println("登录成功\n账号:" + username + "\n密码:" + password);
                app.setNickname(nickname);
                app.setUcoin(UB);
                if (!avatar.equals("null")) {
                    app.setAvatar(DataHolder.base_URL + "/static/" + avatar);
                }

                app.setGender(Gender.MALE);
                if (sex.equals("女")) {
                    app.setGender(Gender.FEMALE);
                }
                app.setPhoneNumber(username);
                Intent intent = new Intent(WelcomeActivity.this, MainPageActivity.class);
                startActivity(intent);
                System.out.println("Login success");
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Login failed");
            Toast.makeText(getApplicationContext(), "用户名/密码错误", Toast.LENGTH_SHORT).show();
            startButtonAnimation();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //for test
        //startActivity(new Intent(WelcomeActivity.this, MainPageActivity.class));

        login = (Button) findViewById(R.id.welcome_login_button);
        register = (Button) findViewById(R.id.welcome_register_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
            }
        });
        loginInformationManager = new LoginInformationManager(this);
        auto_login = loginInformationManager.getAutoLogin();
        System.out.println("auto login = " + auto_login);
        if (auto_login == false) {
            startButtonAnimation();
        }
        if (auto_login == true) {
            username = loginInformationManager.getUsername();
            password = loginInformationManager.getPassword();
            requestLogin(url, username, password);
        }

    }

    //登录
    private boolean requestLogin(String url, String username, String password) {
        String md5pass = null;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        try {
            md5pass = new PasswordEncoder().EncodeByMd5(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("password", md5pass);
        ConnectionManager connectionManager = new ConnectionManager(url, map);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
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
        //TODO 播放动画
        return false;
    }

    private void startButtonAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        login.startAnimation(animation);
        register.startAnimation(animation);
    }
}*/
