package com.example.administrator.ccoupons.Main;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.Gender;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.PasswordEncoder;
import com.example.administrator.ccoupons.Tools.PixelUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {


    public static final int ANIM_SHRINK = 0,
            ANIM_EXPAND = 1;
    boolean editTextFocus = false,
            stateShrinked = false;

    @BindView(R.id.login_toolbar)
    Toolbar loginToolbar;
    @BindView(R.id.imglayout)
    RelativeLayout imglayout;
    @BindView(R.id.Login_usernameEditText)
    EditText LoginUsernameEditText;
    @BindView(R.id.Login_usernameHolder)
    TextInputLayout LoginUsernameHolder;
    @BindView(R.id.Login_passwordEditText)
    EditText LoginPasswordEditText;
    @BindView(R.id.Login_passwordHolder)
    TextInputLayout LoginPasswordHolder;
    @BindView(R.id.Login_forgetTextView)
    TextView LoginForgetTextView;
    @BindView(R.id.Login_loginButton)
    Button LoginLoginButton;
    @BindView(R.id.text_root_view)
    LinearLayout textRootView;
    @BindView(R.id.rootLayout)
    LinearLayout rootLayout;

    @OnClick({R.id.Login_loginButton, R.id.Login_forgetTextView})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.Login_loginButton:
                boolean check = true;
                String username = LoginUsernameEditText.getText().toString(),
                        password = LoginPasswordEditText.getText().toString();

                if (username.length() == 0) {
                    check = false;
                    LoginUsernameHolder.setErrorEnabled(true);
                    LoginUsernameHolder.setError("请输入用户名!");
                } else if (password.length() == 0) {
                    check = false;
                    LoginPasswordHolder.setErrorEnabled(true);
                    LoginPasswordHolder.setError("请输入密码!");
                }

                if (check) {
                    //   requestLogin(GlobalConfig.base_URL + GlobalConfig.login_URL, username, password);
                    requestLogin(username, password);
                }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initToolbar();
        initEditText();
        initSoftKeyboard();
    }


    /**
     * Login to server
     * @param username
     * @param password
     */
    private void requestLogin(String username, String password) {
        String url = GlobalConfig.base_URL + GlobalConfig.login_URL;
        String md5pass = null;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        try {
            md5pass = new PasswordEncoder().EncodeByMd5(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("password", md5pass);
        ZLoadingDialog dialog = new ZLoadingDialog(LoginActivity.this);
        dialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)
                .setLoadingColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCanceledOnTouchOutside(false)
                .show();
        ConnectionManager connectionManager = new ConnectionManager(url, map, dialog);
        connectionManager.setConnectionListener(new ConnectionManager.UHuiConnectionListener() {
            @Override
            public void onConnectionSuccess(String response) {
                parseMessage(response);
            }

            @Override
            public void onConnectionTimeOut() {
                Toast.makeText(getApplicationContext(), "连接服务器超时，请检查网络连接!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onConnectionFailed() {
                Toast.makeText(getApplicationContext(), "连接服务器遇到问题，请检查网络连接!", Toast.LENGTH_LONG).show();
            }
        });
        connectionManager.connect();

    }


    /**
     * Initialize toolbar
     */
    private void initToolbar() {
        setSupportActionBar(loginToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        loginToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    /**
     * initialize edittext
     */
    private void initEditText() {
        LoginUsernameEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        LoginUsernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editTextFocus = true;
                if (!editTextFocus) {
                    hideKeyboard(v);
                }
            }
        });
        LoginPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editTextFocus = true;
                if (!editTextFocus) {
                    hideKeyboard(v);
                }
            }
        });
        LoginUsernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (LoginUsernameEditText.getText().toString().length() > 0) {
                    LoginUsernameHolder.setError("");
                    LoginUsernameHolder.setErrorEnabled(false);
                }
            }
        });
        LoginPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (LoginPasswordEditText.getText().toString().length() > 0) {
                    LoginPasswordHolder.setError("");
                    LoginPasswordHolder.setErrorEnabled(false);
                }
            }
        });
    }


    /**
     * Initialize soft-keyboard actions
     */
    private void initSoftKeyboard() {
        SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(findViewById(R.id.rootLayout));
        softKeyboardStateHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                //键盘打开
                if (!stateShrinked)
                    startAnimation(ANIM_SHRINK);
            }

            @Override
            public void onSoftKeyboardClosed() {
                //键盘关闭
                if (stateShrinked)
                    startAnimation(ANIM_EXPAND);
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * Start animation in specific type
     * @param anim_type type of animation
     */
    private void startAnimation(int anim_type) {

        int mergeHeight = PixelUtils.dp2px(this, 120);
        if (anim_type == ANIM_SHRINK) {

            LinearLayout textRoot = (LinearLayout) findViewById(R.id.text_root_view);
            ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(textRoot, "y", textRoot.getTop(), textRoot.getTop() - mergeHeight)
                    .setDuration(500);
            heightAnimator.start();

            RelativeLayout imgLayout = (RelativeLayout) findViewById(R.id.imglayout);
            Animation anim = new AnimationUtils().loadAnimation(this, R.anim.image_shrink);
            anim.setFillAfter(true);
            imgLayout.startAnimation(anim);
            stateShrinked = true;
        }
        if (anim_type == ANIM_EXPAND) {

            LinearLayout textRoot = (LinearLayout) findViewById(R.id.text_root_view);
            ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(textRoot, "y", textRoot.getTop() - mergeHeight, textRoot.getTop())
                    .setDuration(500);
            heightAnimator.start();

            RelativeLayout imgLayout = (RelativeLayout) findViewById(R.id.imglayout);
            Animation anim = new AnimationUtils().loadAnimation(this, R.anim.image_expand);
            anim.setFillAfter(true);
            imgLayout.startAnimation(anim);
            stateShrinked = false;
        }
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
        finish();
        super.onBackPressed();
    }


    /**
     * parse response from server
     * @param response
     */
    private void parseMessage(String response) {
        System.out.println("response = " + response);
        if (response.indexOf("result") != -1) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String userId = jsonObject.getString("userid");
                String nickname = jsonObject.getString("nickname");
                String avatar = jsonObject.getString("avatar");
                String sex = jsonObject.getString("gender");
                int UB = jsonObject.getInt("Ucoin");
                MyApp app = (MyApp) getApplicationContext();
                app.setUserId(userId);
                app.setNickname(nickname);
                app.setUcoin(UB);
                if (!avatar.equals("null")) {
                    app.setAvatar(GlobalConfig.base_URL + "/static/" + avatar);
                }

                app.setGender(Gender.MALE);
                if (sex.equals("女")) {
                    app.setGender(Gender.FEMALE);
                }
                app.setPhoneNumber("13111111111");

                Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getApplicationContext(), "用户名/密码错误", Toast.LENGTH_SHORT).show();
        }
    }

}

