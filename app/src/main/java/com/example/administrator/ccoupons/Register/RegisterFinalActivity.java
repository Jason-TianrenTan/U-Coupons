package com.example.administrator.ccoupons.Register;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.RegisterThread;
import com.example.administrator.ccoupons.CustomEditText.ClearableEditText;
import com.example.administrator.ccoupons.Gender;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.R;

import java.net.URL;

public class RegisterFinalActivity extends AppCompatActivity {

    //127.0.0.1

    private final static String requestURL = "http://192.168.203.205:8000/post_signup";
    Button button_next;
    RadioGroup radioGroup;
    int gender;
    ClearableEditText nickname_edittext;
    String phoneString,password;
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

    private void LogIn() {
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
        RegisterThread thread = new RegisterThread(url_str, phoneString,password,nickname,gender);
     //   RequestDataThread thread = new RequestDataThread();
        System.out.println("On Thread Start");
        thread.start();
    }

}
