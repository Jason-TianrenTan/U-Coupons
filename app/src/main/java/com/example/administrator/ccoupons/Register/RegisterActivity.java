package com.example.administrator.ccoupons.Register;

import android.content.Context;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private RegisterCheck checker;
    private String[] AlertStrings = "不能含有非法字符,长度必须为11位".split(",");

    @BindView(R.id.text_return)
    TextView text_return;
    @BindView(R.id.register_main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.register_button_next)
    Button button_next;
    @BindView(R.id.register_phone_input)
    EditText phoneInput;
    @BindView(R.id.register_phone_inputlayout)
    TextInputLayout inputLayout;

    @OnClick({R.id.text_return, R.id.register_button_next})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.text_return:
                finish();
                break;
            case R.id.register_button_next:
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
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        checker = new RegisterCheck();
        //    getSupportActionBar().hide();
        initToolbar();
        initEditText();
    }


    /**
     * init edit text view
     */
    private void initEditText(){
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
    }


    /**
     * init toolbar
     */
    private void initToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
