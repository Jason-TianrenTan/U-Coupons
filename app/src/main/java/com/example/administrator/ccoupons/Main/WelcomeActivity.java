package com.example.administrator.ccoupons.Main;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.LoginThread;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Register.RegisterActivity;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.MessageType;
import org.json.JSONObject;

public class WelcomeActivity extends AppCompatActivity {
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
            System.out.println("Login success");
            try {
                JSONObject jsonObject = new JSONObject(response);
                String userId = jsonObject.getString("userid");
                MyApp app = (MyApp) getApplicationContext();
                app.setUserId(userId);
                Toast.makeText(getApplicationContext(), "登录成功\n账号:" + username +
                        "\n密码:" + password, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WelcomeActivity.this, MainPageActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
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
            username = loginInformationManager.getUsername();
            password = loginInformationManager.getPassword();
            //向服务器发送账号密码并验证
            //判断
            //- 失败
            //- 网络无连接
            //- 成功，并收到服务器的消息\

            Toast.makeText(getApplicationContext(), "登录成功\n账号:" + username +
                    "\n密码:" + password, Toast.LENGTH_SHORT).show();    //fortest
            startActivity(new Intent(WelcomeActivity.this, MainPageActivity.class));
            finish();
        }
    }

    //登录
    private boolean requestLogin(String url, String username, String password) {
        thread = new LoginThread(url, username, password, handler, getApplicationContext());
        thread.start();
        //TODO 播放动画
        return false;
    }
}

