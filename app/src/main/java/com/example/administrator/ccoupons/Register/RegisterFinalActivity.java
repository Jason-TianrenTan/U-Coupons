package com.example.administrator.ccoupons.Register;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.RegisterThread;
import com.example.administrator.ccoupons.Connections.UHuiConnection;
import com.example.administrator.ccoupons.CustomEditText.ClearableEditText;
import com.example.administrator.ccoupons.Gender;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.MessageType;
import com.example.administrator.ccoupons.Tools.PasswordEncoder;
import com.example.administrator.ccoupons.UI.CustomDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import static org.apache.http.protocol.HTTP.USER_AGENT;

public class RegisterFinalActivity extends AppCompatActivity {

    //127.0.0.1

    private CustomDialog customDialog = null;
    private LoginInformationManager loginInformationManager;
    private RegisterThread thread;
    private final static String requestURL = "http://192.168.204.83:8000/post_signUpForAndroid";
    private Button button_next;
    private RadioGroup radioGroup;
    private int gender;
    private ClearableEditText nickname_edittext;
    private String phoneString,password;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            //节目效果
            Thread t = new Thread(new Runnable() {
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
            });
            t.start();

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

    //处理返回回来的json
    private void parseMessage(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String errno = jsonObject.getString("errno");
            if (errno.equals("1")) {
                //注册失败
                Toast.makeText(getApplicationContext(), "账号已经存在", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterFinalActivity.this, RegisterActivity.class));
            }
            else {
                loginInformationManager.setAutoLogin(true).setPhoneNumber(phoneString).setPassword(password);
                Intent intent = new Intent(RegisterFinalActivity.this, MainPageActivity.class);
                intent.putExtra("username", phoneString).putExtra("password", password);
                startActivity(intent);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_final);

        Toolbar toolbar = (Toolbar)findViewById(R.id.register_final_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nickname_edittext = (ClearableEditText)findViewById(R.id.register_final_username);

        phoneString = getIntent().getStringExtra("phone_number");
        password = getIntent().getStringExtra("password");
        gender = Gender.MALE;
        System.out.println(phoneString + "," + password + "," + gender);
        button_next = (Button)findViewById(R.id.register_final_button_next);
        radioGroup = (RadioGroup)findViewById(R.id.register_final_radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int buttonId = radioGroup.getCheckedRadioButtonId();
                if (buttonId == R.id.radio_button_male)
                    gender = Gender.MALE;
                else gender = Gender.FEMALE;
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname = nickname_edittext.getText().toString();
                Register(nickname);
            }
        });
    }

    private void Login() {
        Intent intent = new Intent(RegisterFinalActivity.this, MainPageActivity.class);
        intent.putExtra("phone_number",phoneString)
                .putExtra("nickname",nickname_edittext.getText().toString());
        startActivity(intent);
    }

    private void Register(String nickname) {
        //phone/email
        //password
        //nickname
        //gender
        String url_str =requestURL;
        thread = new RegisterThread(url_str, phoneString,password,nickname,gender, handler,getApplicationContext());
     //   RequestDataThread thread = new RequestDataThread();
        thread.start();
        customDialog = new CustomDialog(this, R.style.CustomDialog);
        customDialog.show();
    }


}
