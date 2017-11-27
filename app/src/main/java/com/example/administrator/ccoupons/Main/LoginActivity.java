package com.example.administrator.ccoupons.Main;

import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
import android.os.Handler;
import android.os.Message;
=======
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
>>>>>>> ttr
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
<<<<<<< HEAD
import android.util.TypedValue;
=======
import android.text.Editable;
import android.text.TextWatcher;
>>>>>>> ttr
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import com.example.administrator.ccoupons.Connections.LoginThread;
import com.example.administrator.ccoupons.Data.DataHolder;
=======
import com.example.administrator.ccoupons.Connections.ConnectionManager;
import com.example.administrator.ccoupons.Data.GlobalConfig;
>>>>>>> ttr
import com.example.administrator.ccoupons.Fragments.MainPageActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
<<<<<<< HEAD
<<<<<<< HEAD
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.MessageType;
import com.example.administrator.ccoupons.Tools.PixelUtils;
import com.example.administrator.ccoupons.UI.CustomDialog;

import org.json.JSONObject;

=======
=======
import com.example.administrator.ccoupons.Register.RegisterNewActivity;
>>>>>>> Czj
import com.example.administrator.ccoupons.Tools.PasswordEncoder;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

>>>>>>> ttr

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

<<<<<<< HEAD
    private static String url = DataHolder.base_URL + DataHolder.login_URL;
    private LoginThread thread;
    private Button login;
    private Toolbar toolbar;
    private EditText signup_phone, signup_pass;
    private String myUsername, myPassword;
    private Handler handler = new Handler() {
=======
    public static final int ANIM_SHRINK = 0,
            ANIM_EXPAND = 1;
    boolean editTextFocus = false,
            stateShrinked = false;
>>>>>>> ttr

    @BindView(R.id.login_toolbar)
    Toolbar loginToolbar;
    //    @BindView(R.id.imglayout)
//    RelativeLayout imglayout;
    @BindView(R.id.Login_usernameEditText)
    EditText LoginUsernameEditText;
    @BindView(R.id.Login_usernameHolder)
    TextInputLayout LoginUsernameHolder;
    @BindView(R.id.Login_passwordEditText)
    EditText LoginPasswordEditText;
    @BindView(R.id.Login_passwordHolder)
    TextInputLayout LoginPasswordHolder;
    @BindView(R.id.Login_registerTextView)
    TextView LoginRegisterTextView;
    @BindView(R.id.Login_forgetTextView)
    TextView LoginForgetTextView;
    @BindView(R.id.Login_loginButton)
    Button LoginLoginButton;
    @BindView(R.id.text_root_view)
    LinearLayout textRootView;
    @BindView(R.id.rootLayout)
    LinearLayout rootLayout;

    @OnClick({R.id.Login_loginButton, R.id.Login_forgetTextView, R.id.Login_registerTextView})
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

<<<<<<< HEAD
<<<<<<< HEAD
            switch (msg.what) {
                case MessageType.CONNECTION_ERROR:
                    Toast.makeText(getApplicationContext(), "连接服务器遇到问题，请检查网络连接!", Toast.LENGTH_LONG).show();
                    login.setEnabled(true);
                    break;
                case MessageType.CONNECTION_TIMEOUT:
                    Toast.makeText(getApplicationContext(), "连接服务器超时，请检查网络连接!", Toast.LENGTH_LONG).show();
                    login.setEnabled(true);
                    break;
                case MessageType.CONNECTION_SUCCESS:
                    parseMessage(thread.getResponse());
                    break;
                case MessageType.REENABLE_LOGIN:
                    login.setEnabled(true);
                    break;
            }
=======
        if (check) {
            //   requestLogin(GlobalConfig.base_URL + GlobalConfig.login_URL, username, password);
            requestLogin(username, password);
>>>>>>> ttr
=======
                if (check) {
                    //   requestLogin(GlobalConfig.base_URL + GlobalConfig.login_URL, username, password);
                    requestLogin(username, password);
                }
                break;
            case R.id.Login_forgetTextView:
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                break;
            case R.id.Login_registerTextView:
                startActivity(new Intent(LoginActivity.this, RegisterNewActivity.class));
                break;
>>>>>>> Czj
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initToolbar();
        initEditText();
//        initSoftKeyboard();
    }

<<<<<<< HEAD
    //处理返回回来的json
    private void parseMessage(String response) {
        if (response.indexOf("result") != -1) {
            System.out.println("Login success");
            try {
                JSONObject jsonObject = new JSONObject(response);
                String userId = jsonObject.getString("userid");
                System.out.println("Response = " + response);
                MyApp app = (MyApp) getApplicationContext();
                app.setUserId(userId);
                Toast.makeText(getApplicationContext(), "登录成功\n账号:" + myUsername +
                        "\n密码:" + myPassword, Toast.LENGTH_SHORT).show();
                saveUserLoginInfo();//缓存密码


                Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                intent.putExtra("username", myUsername);
                intent.putExtra("password", myPassword);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                Message msg = new Message();
                msg.what = MessageType.REENABLE_LOGIN;
                handler.sendMessage(msg);
                e.printStackTrace();
            }

        }
        else {
            if (response.indexOf("error") != -1) {
                System.out.println("Login failed");
                Message msg = new Message();
                msg.what = MessageType.REENABLE_LOGIN;
                handler.sendMessage(msg);
                Toast.makeText(getApplicationContext(), "用户名/密码错误", Toast.LENGTH_SHORT).show();
            }
            else {
                Message msg = new Message();
                msg.what = MessageType.CONNECTION_ERROR;
                handler.sendMessage(msg);
=======

    /**
     * Login to server
     *
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
>>>>>>> ttr
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    /**
     * initialize edittext
     */
    private void initEditText() {
//        LoginUsernameEditText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
//        LoginUsernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    editTextFocus = true;
//                if (!editTextFocus) {
//                    hideKeyboard(v);
//                }
//            }
//        });
//        LoginPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    editTextFocus = true;
//                if (!editTextFocus) {
//                    hideKeyboard(v);
//                }
//            }
//        });
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
<<<<<<< HEAD
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
=======
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (LoginPasswordEditText.getText().toString().length() > 0) {
                    LoginPasswordHolder.setError("");
                    LoginPasswordHolder.setErrorEnabled(false);
                }
>>>>>>> ttr
            }
        });
    }


<<<<<<< HEAD
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

<<<<<<< HEAD
    //登录
    private void requestLogin(String url, String username, String password) {
        myUsername = username;
        myPassword = password;
        thread = new LoginThread(url, username, password, handler, getApplicationContext());
        thread.start();
        //TODO 播放动画
    }

=======
>>>>>>> ttr
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
=======
//    /**
//     * Initialize soft-keyboard actions
//     */
//    private void initSoftKeyboard() {
//        SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(findViewById(R.id.rootLayout));
//        softKeyboardStateHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
//            @Override
//            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
//                //键盘打开
//                if (!stateShrinked)
//                    startAnimation(ANIM_SHRINK);
//            }
//
//            @Override
//            public void onSoftKeyboardClosed() {
//                //键盘关闭
//                if (stateShrinked)
//                    startAnimation(ANIM_EXPAND);
//            }
//        });
//    }
//
//    private void hideKeyboard(View view) {
//        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }
//
//
//    /**
//     * Start animation in specific type
//     * @param anim_type type of animation
//     */
//    private void startAnimation(int anim_type) {
//
//        int mergeHeight = PixelUtils.dp2px(this, 80);
//        if (anim_type == ANIM_SHRINK) {
//
//            LinearLayout textRoot = (LinearLayout) findViewById(R.id.text_root_view);
//            ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(textRoot, "y", textRoot.getTop(), textRoot.getTop() - mergeHeight)
//                    .setDuration(500);
//            heightAnimator.start();
//
////            RelativeLayout imgLayout = (RelativeLayout) findViewById(R.id.imglayout);
////            Animation anim = new AnimationUtils().loadAnimation(this, R.anim.image_shrink);
////            anim.setFillAfter(true);
////            imgLayout.startAnimation(anim);
//            stateShrinked = true;
//        }
//        if (anim_type == ANIM_EXPAND) {
//
//            LinearLayout textRoot = (LinearLayout) findViewById(R.id.text_root_view);
//            ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(textRoot, "y", textRoot.getTop() - mergeHeight, textRoot.getTop())
//                    .setDuration(500);
//            heightAnimator.start();
//
////            RelativeLayout imgLayout = (RelativeLayout) findViewById(R.id.imglayout);
////            Animation anim = new AnimationUtils().loadAnimation(this, R.anim.image_expand);
////            anim.setFillAfter(true);
////            imgLayout.startAnimation(anim);
//            stateShrinked = false;
//        }
//    }
>>>>>>> Czj


    @Override
    public void onBackPressed() {
        finish();
    }


    /**
     * parse response from server
     *
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

