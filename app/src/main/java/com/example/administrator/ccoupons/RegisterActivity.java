package com.example.administrator.ccoupons;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    EditText input_phone, input_password;
    TextInputLayout inputPhoneHolder, inputPassHolder;
    TextView text_return;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    //    getSupportActionBar().hide();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        text_return = (TextView)findViewById(R.id.text_return);
        text_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        input_phone = (EditText)findViewById(R.id.input_phone);
        input_phone.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        inputPhoneHolder = (TextInputLayout)findViewById(R.id.input_phone_holder);
        input_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (str.length() < 13) {
                    inputPhoneHolder.setErrorEnabled(true);
                    inputPhoneHolder.setError("请输入完整的手机号码");
                }
                else inputPhoneHolder.setErrorEnabled(false);
            }
        });

    }

}
