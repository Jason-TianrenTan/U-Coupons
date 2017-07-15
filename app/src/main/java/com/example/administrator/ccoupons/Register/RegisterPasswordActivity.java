package com.example.administrator.ccoupons.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private PasswordToggleEditText inputPass,confirmPass;
    private RegisterCheck checker = new RegisterCheck();
    private String[] errorStrings = "不能含有非法字符,长度必须为6~16位,两次密码不匹配".split(",");
    private boolean valid = false;
    private String first_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_password);

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
        Button button_next = (Button)findViewById(R.id.register_password_button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valid) {
                    Intent intent = new Intent(RegisterPasswordActivity.this, RegisterFinalActivity.class);
                    startActivity(intent);
                }
            }
        });

        inputPass = (PasswordToggleEditText)findViewById(R.id.register_input_pass);
        confirmPass = (PasswordToggleEditText)findViewById(R.id.register_input_confirmpass);

        inputPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pass = inputPass.getText().toString();
                int err_type = checker.alertPassword(pass);
                if (err_type != AlertType.NO_ERROR) {
                    EditTextTools.setCursorColor(inputPass, getResources().getColor(R.color.red));
                    inputPass.setError(errorStrings[err_type - 1]);
                    valid = false;
                }
                else {
                    EditTextTools.setCursorColor(inputPass, getResources().getColor(R.color.colorAccent));
                    valid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        confirmPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pass = confirmPass.getText().toString();
                int err_type = checker.alertPassword(pass);
                if (err_type != AlertType.NO_ERROR) {
                    EditTextTools.setCursorColor(confirmPass, getResources().getColor(R.color.red));
                    confirmPass.setError(errorStrings[err_type - 1]);
                    valid = false;
                }
                else {
                    if (inputPass.getText().toString().equals(confirmPass.getText().toString())) {
                        EditTextTools.setCursorColor(confirmPass, getResources().getColor(R.color.colorAccent));
                        valid = true;
                    }
                    else {
                        EditTextTools.setCursorColor(confirmPass, getResources().getColor(R.color.red));
                        confirmPass.setError(errorStrings[2]);
                        valid = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
