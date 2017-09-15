package com.example.administrator.ccoupons.Register;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.ccoupons.Main.LoginActivity;
import com.example.administrator.ccoupons.Main.WelcomeActivity;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.AlertType;
import com.example.administrator.ccoupons.Tools.RegisterCheck;

public class RegisterActivity extends AppCompatActivity {

    TextView text_return;
    Toolbar toolbar;
    Button button_next;
    EditText phoneInput;
    RegisterCheck checker;
    TextInputLayout inputLayout;
    private String[] AlertStrings = "不能含有非法字符,长度必须为11位".split(",");


    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, WelcomeActivity.class));
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        checker = new RegisterCheck();
        //    getSupportActionBar().hide();
        toolbar = (Toolbar) findViewById(R.id.register_main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        phoneInput = (EditText) findViewById(R.id.register_phone_input);
        inputLayout = (TextInputLayout) findViewById(R.id.register_phone_inputlayout);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        text_return = (TextView) findViewById(R.id.text_return);
        text_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        phoneInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = phoneInput.getText().toString();
                if (str.length() == 11) {
                    inputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        button_next = (Button) findViewById(R.id.register_button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = phoneInput.getText().toString();
                int err_type = checker.alertPhoneNumber(str);
                if (err_type != AlertType.NO_ERROR) {
                    //有错误
                    inputLayout.setError(AlertStrings[err_type - 1]);
                }
                else {
                    Intent intent = new Intent(RegisterActivity.this, RegisterIdentifyActivity.class);
                    intent.putExtra("phone_number", phoneInput.getText().toString());
                    startActivity(intent);
                }
            }
        });

    }
}
