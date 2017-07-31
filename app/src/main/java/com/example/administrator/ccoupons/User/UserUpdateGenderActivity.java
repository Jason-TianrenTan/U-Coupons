package com.example.administrator.ccoupons.User;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Gender;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.UI.CustomDialog;

import org.json.JSONObject;

import java.util.HashMap;

public class UserUpdateGenderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView updateGender;
    private RadioGroup genderRadio;
    private CustomDialog customDialog = null;
    private int gender;
    private MyApp app;
    private final static String updateUserInformationURL = DataHolder.base_URL + DataHolder.updateUserInformation_URL;
    private final String[] genderStr = {"男", "女"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_gender);
        bindViews();
        setOnClinckListeners();
    }

    private void bindViews() {
        app = (MyApp) getApplicationContext();
        toolbar = (Toolbar) findViewById(R.id.user_update_gender_toolbar);
        updateGender = (TextView) findViewById(R.id.user_update_gender);
        genderRadio = (RadioGroup) findViewById(R.id.user_update_gender_radio);
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
        genderRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int buttonId = radioGroup.getCheckedRadioButtonId();
                if (buttonId == R.id.radio_button_male)
                    gender = Gender.MALE;
                else gender = Gender.FEMALE;
            }
        });

        if (app.getGender() == Gender.MALE) {
            genderRadio.check(R.id.radio_button_male);
        } else genderRadio.check(R.id.radio_button_female);

        updateGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gender == Gender.MALE) {
                    update(genderStr[0]);
                } else update(genderStr[1]);
            }
        });
    }

    private void update(String gender) {
        String url_str = updateUserInformationURL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userID", app.getUserId());
        map.put("gender", gender);
        ConnectionManager connectionManager = new ConnectionManager(url_str, map);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200);
                            customDialog.dismiss();//关闭ProgressDialog
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
                parseMessage(response);
            }

            @Override
            public void onConnectionTimeOut() {
                Toast.makeText(getApplicationContext(), "连接服务器超时，请稍后再试", Toast.LENGTH_SHORT).show();
                customDialog.dismiss();
            }

            @Override
            public void onConnectionFailed() {
                Toast.makeText(getApplicationContext(), "连接服务器遇到问题，请稍后再试", Toast.LENGTH_SHORT).show();
                customDialog.dismiss();
            }
        });
        connectionManager.connect();
        customDialog = new CustomDialog(this, R.style.CustomDialog);
        customDialog.show();
    }

    private void parseMessage(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String result = jsonObject.getString("result");
            if (result.equals("success")) {
                app.setGender(gender);
                Toast.makeText(UserUpdateGenderActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(UserUpdateGenderActivity.this, "好像出了点问题哟", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
