package com.example.administrator.ccoupons.User;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.LoginActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.json.JSONObject;

import java.util.HashMap;

public class UserUpdateNicknameActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView updatenick;
    private EditText nicknameEdit;
    private String nickname;
    private MyApp app;
    private final static String updateUserInformationURL = DataHolder.base_URL + DataHolder.updateUserInformation_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_nickname);
        bindViews();
        setOnClinckListeners();
    }

    private void bindViews() {
        app = (MyApp) getApplicationContext();
        toolbar = (Toolbar) findViewById(R.id.user_update_nickname_toolbar);
        updatenick = (TextView) findViewById(R.id.user_update_nickname);
        nicknameEdit = (EditText) findViewById(R.id.update_nickname_edit);
        nicknameEdit.setHint(app.getNickname());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setOnClinckListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        updatenick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nickname = nicknameEdit.getText().toString();
                update(nickname);
            }
        });
    }

    private void update(String nickname) {
        String url_str = updateUserInformationURL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userID", app.getUserId());
        map.put("nickname", nickname);

        ZLoadingDialog dialog = new ZLoadingDialog(UserUpdateNicknameActivity.this);
        dialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)
                .setLoadingColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCanceledOnTouchOutside(false)
                .setHintText("正在修改...")
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

    private void parseMessage(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String result = jsonObject.getString("result");
            if (result.equals("success")) {
                app.setNickname(nickname);
                Toast.makeText(UserUpdateNicknameActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            } else if (result.equals("nickname exist")) {
                Toast.makeText(UserUpdateNicknameActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}