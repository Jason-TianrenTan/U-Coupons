package com.example.administrator.ccoupons.Main;

import android.app.Activity;
import android.content.Intent;
<<<<<<< HEAD
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
=======
import android.os.Bundle;
>>>>>>> ttr
import android.view.View;
import android.widget.Button;

<<<<<<< HEAD
import com.example.administrator.ccoupons.Connections.LoginThread;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Register.RegisterActivity;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.MessageType;
import com.mob.MobApplication;
import com.mob.MobSDK;
=======
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Register.RegisterActivity;
>>>>>>> ttr

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

<<<<<<< HEAD
=======

public class WelcomeActivity extends Activity {


    @BindView(R.id.Welcome_LoginButton)
    Button Welcome_LoginButton;
    @BindView(R.id.Welcome_RegisterButton)
    Button Welcome_RegisterButton;

    @OnClick({R.id.Welcome_LoginButton,R.id.Welcome_RegisterButton})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.Welcome_LoginButton:
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.Welcome_RegisterButton:
                startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
    }

}






















/*
>>>>>>> ttr
public class WelcomeActivity extends AppCompatActivity {
    private static String url = GlobalConfig.base_URL + GlobalConfig.login_URL;
    private LoginInformationManager loginInformationManager;
    private boolean auto_login;
    private String username;
    private String password;
    private LoginThread thread;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessageType.CONNECTION_ERROR:
                    Toast.makeText(getApplicationContext(), "连接服务器遇到问题，请检查网络连接!", Toast.LENGTH_LONG).show();
                    break;
                case MessageType.CONNECTION_TIMEOUT:
                    Toast.makeText(getApplicationContext(), "连接服务器超时，请检查网络连接!", Toast.LENGTH_LONG).show();
                    break;
                case MessageType.CONNECTION_SUCCESS:
                    parseMessage(thread.getResponse());
                    break;
            }
        }
    };
    Button login;
    Button register;

    //处理返回回来的json
    private void parseMessage(String response) {
        if (response.indexOf("result") != -1) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String userId = jsonObject.getString("userid");
                MyApp app = (MyApp) getApplicationContext();
                app.setUserId(userId);
                System.out.println("Response = " + response);
<<<<<<< HEAD
                Toast.makeText(getApplicationContext(), "登录成功\n账号:" + username +
                        "\n密码:" + password, Toast.LENGTH_SHORT).show();
=======
                System.out.println("登录成功\n账号:" + username + "\n密码:" + password);
                app.setNickname(nickname);
                app.setUcoin(UB);
                if (!avatar.equals("null")) {
                    app.setAvatar(GlobalConfig.base_URL + "/static/" + avatar);
                }

                app.setGender(Gender.MALE);
                if (sex.equals("女")) {
                    app.setGender(Gender.FEMALE);
                }
                app.setPhoneNumber(username);
>>>>>>> ttr
                Intent intent = new Intent(WelcomeActivity.this, MainPageActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
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
        loginInformationManager = new LoginInformationManager(this);
        auto_login = loginInformationManager.getAutoLogin();
        System.out.println("auto login = " + auto_login);
        if (auto_login == false) {
            /*
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);*/
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
        thread = new LoginThread(url, username, password, handler, getApplicationContext());
        thread.start();
        //TODO 播放动画
        return false;
    }

    private void startButtonAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        login.startAnimation(animation);
        register.startAnimation(animation);
    }
<<<<<<< HEAD




}

=======
}*/
>>>>>>> ttr
