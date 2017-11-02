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

import com.example.administrator.ccoupons.CustomEditText.PasswordToggleEditText;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.AlertType;
import com.example.administrator.ccoupons.Tools.EditTextTools;
import com.example.administrator.ccoupons.Tools.RegisterCheck;

public class RegisterPasswordActivity extends AppCompatActivity {


    private PasswordToggleEditText inputPass, confirmPass;
    private RegisterCheck checker = new RegisterCheck();
    private String[] errorStrings = "不能含有非法字符,长度必须为6~16位,密码强度太弱".split(",");
    private boolean valid = false;
    private TextInputLayout firstlayout, confirmLayout;
    private String phoneString,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_password);

        phoneString = getIntent().getStringExtra("phone_number");
        Toolbar toolbar = (Toolbar) findViewById(R.id.register_password_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button button_next = (Button) findViewById(R.id.register_password_button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valid) {
                    password = inputPass.getText().toString();
                    Intent intent = new Intent(RegisterPasswordActivity.this, RegisterFinalActivity.class);
                    intent.putExtra("phone_number",phoneString);
                    intent.putExtra("password",password);
                    startActivity(intent);
                }
            }
        });

        inputPass = (PasswordToggleEditText) findViewById(R.id.register_input_pass);
        confirmPass = (PasswordToggleEditText) findViewById(R.id.register_input_confirmpass);
        firstlayout = (TextInputLayout)findViewById(R.id.register_firstpassword_inputlayout);
        confirmLayout = (TextInputLayout)findViewById(R.id.register_confirmpassword_inputlayout);

        inputPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                confirmPass.setText("");
                confirmLayout.setError("");
                confirmLayout.setErrorEnabled(false);
                String pass = inputPass.getText().toString();
                int err_type = checker.alertPassword(pass);
                if (err_type != AlertType.NO_ERROR && err_type!=  AlertType.TOO_SIMPLE) {
                    EditTextTools.setCursorColor(inputPass, getResources().getColor(R.color.red));
                    firstlayout.setErrorEnabled(true);
                    firstlayout.setError(errorStrings[err_type - 1]);
                    valid = false;
                } else {
                    if (err_type == AlertType.NO_ERROR) {
                        //密码合格
                        EditTextTools.setCursorColor(inputPass, getResources().getColor(R.color.colorAccent));
                        firstlayout.setError("");
                        firstlayout.setErrorEnabled(false);
                        valid = true;
                    }
                    else {
                        //强度不够
                        EditTextTools.setCursorColor(inputPass, getResources().getColor(R.color.skyblue));

                        firstlayout.setErrorEnabled(true);
                        firstlayout.setError(errorStrings[err_type - 1]);
                    }
                }
            }
        });

        confirmPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pass = confirmPass.getText().toString();
                int err_type = checker.alertPassword(pass);
                if (err_type != AlertType.NO_ERROR && err_type!=  AlertType.TOO_SIMPLE) {
                    EditTextTools.setCursorColor(confirmPass, getResources().getColor(R.color.red));
                    confirmLayout.setError(errorStrings[err_type - 1]);
                    valid = false;
                } else {
                    if (inputPass.getText().toString().equals(confirmPass.getText().toString())) {
                        EditTextTools.setCursorColor(confirmPass, getResources().getColor(R.color.colorAccent));
                        confirmLayout.setErrorEnabled(false);
                        valid = true;
                    } else {
                        EditTextTools.setCursorColor(confirmPass, getResources().getColor(R.color.red));
                        confirmLayout.setError("两次密码不匹配");
                        valid = false;
                    }
                }
            }
        });
    }
}
