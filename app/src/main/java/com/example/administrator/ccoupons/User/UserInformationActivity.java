package com.example.administrator.ccoupons.User;

import android.app.Dialog;
import android.content.Intent;
<<<<<<< HEAD
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
=======
>>>>>>> ttr
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.ImageLruCache;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
=======
import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Connections.UploadTask;
import com.example.administrator.ccoupons.Gender;
import com.example.administrator.ccoupons.Main.ResetPasswordActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
>>>>>>> ttr
import com.example.administrator.ccoupons.Tools.SlideBackActivity;
import com.example.administrator.ccoupons.Tools.TakePhotoUtil;
import com.jph.takephoto.model.TResult;

<<<<<<< HEAD
<<<<<<< HEAD
import java.util.regex.Matcher;
import java.util.regex.Pattern;
=======
=======
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
>>>>>>> Czj
import de.hdodenhof.circleimageview.CircleImageView;
>>>>>>> ttr

public class UserInformationActivity extends SlideBackActivity {
<<<<<<< HEAD
    private TextView name;
    private TextView sex;
<<<<<<< HEAD
    private TextView age;
    private XCRoundImageView portrait;
    private TakePhotoUtil takePhotoUtil;
    private Toolbar toolbar;
    private LinearLayout changeportrait;
    private LoginInformationManager informationManager;
=======
    private CircleImageView portrait;
=======

>>>>>>> Czj
    private TakePhotoUtil takePhotoUtil;
    private MyApp app;
>>>>>>> ttr

    @BindView(R.id.user_name)
    TextView name;
    @BindView(R.id.user_sex)
    TextView sex;
    @BindView(R.id.uinf_portrait)
    CircleImageView portrait;
    @BindView(R.id.uinf_toolbar)
    Toolbar toolbar;
    @BindView(R.id.change_portrait)
    LinearLayout changeportrait;
    @BindView(R.id.uinf_to_resetpw)
    LinearLayout toResetPassword;
    @BindView(R.id.to_update_nickname)
    LinearLayout toUpdateNickname;
    @BindView(R.id.to_update_gender)
    LinearLayout toUpdateGender;
    @BindView(R.id.to_update_phone)
    LinearLayout toUpdatePhone;

    @OnClick({R.id.uinf_portrait, R.id.change_portrait, R.id.uinf_to_resetpw, R.id.to_update_nickname,
            R.id.to_update_gender, R.id.to_update_phone})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.uinf_portrait:
                startActivity(new Intent(UserInformationActivity.this, UserPortraitActivity.class));
                overridePendingTransition(R.anim.portrait_in, R.anim.noanim);
                initPortrait();
                break;
            case R.id.change_portrait:
                changePortrait();
                break;
            case R.id.uinf_to_resetpw:
                Intent intent = new Intent(UserInformationActivity.this, ResetPasswordActivity.class);
                intent.putExtra("phoneString", ((MyApp) getApplicationContext()).getPhoneNumber());
                startActivity(intent);
                break;
            case R.id.to_update_nickname:
                startActivity(new Intent(UserInformationActivity.this, UserUpdateNicknameActivity.class));
                break;
            case R.id.to_update_gender:
                startActivity(new Intent(UserInformationActivity.this, UserUpdateGenderActivity.class));
                break;
            case R.id.to_update_phone:
                startActivity(new Intent(UserInformationActivity.this, ResetPhoneNumberActivity.class));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
<<<<<<< HEAD
        initView();
        if (useTakePhoto()) {
            takePhotoUtil.onCreate(savedInstanceState);
        }

        initPortrait();
        //portrait.setImageResource(DataHolder.User.portraitId);

        setOnClickListeners();
=======
        ButterKnife.bind(this);
        initToolbar();
        initData();
        initPortrait();
        //portrait.setImageResource(GlobalConfig.User.portraitId);
        takePhotoUtil = new TakePhotoUtil(this);
        if (useTakePhoto()) {
            takePhotoUtil.onCreate(savedInstanceState);
        }
>>>>>>> Czj
    }

<<<<<<< HEAD
    private void initView(){
        name = (TextView) findViewById(R.id.user_name);
        sex = (TextView) findViewById(R.id.user_sex);
        age = (TextView) findViewById(R.id.user_age);
        portrait = (XCRoundImageView) findViewById(R.id.uinf_portrait);
=======
    @Override
    protected void onStart() {
        initData();
        initPortrait();
        super.onStart();
    }


    /**
     * Initially Toolbar
     */
<<<<<<< HEAD
    private void bindViews() {
        name = (TextView) findViewById(R.id.user_name);
        sex = (TextView) findViewById(R.id.user_sex);
        portrait = (CircleImageView) findViewById(R.id.uinf_portrait);
>>>>>>> ttr
        toolbar = (Toolbar) findViewById(R.id.uinf_toolbar);
        changeportrait = (LinearLayout) findViewById(R.id.change_portrait);
        informationManager = new LoginInformationManager(this);
        takePhotoUtil = new TakePhotoUtil(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
<<<<<<< HEAD
=======
        toResetPassword = (LinearLayout) findViewById(R.id.uinf_to_resetpw);
        toUpdateNickname = (LinearLayout) findViewById(R.id.to_update_nickname);
        toUpdateGender = (LinearLayout) findViewById(R.id.to_update_gender);
        toUpdatePhone = (LinearLayout) findViewById(R.id.to_update_phone);
=======
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
>>>>>>> Czj
    }


    /**
     * Init nickname, gender and camera
     */
    private void initData() {
        app = (MyApp) this.getApplicationContext();
        name.setText(app.getNickname());
        if (app.getGender() == Gender.MALE)
            sex.setText("男");
        else sex.setText("女");
<<<<<<< HEAD
    }


    /**
     * set listeners
     */
    private void setOnClickListeners() {
>>>>>>> ttr
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name.setText(DataHolder.User.username);
        age.setText(Integer.toString(DataHolder.User.age));
        if (DataHolder.User.sex)
            sex.setText("男");
        else
            sex.setText("女");
    }

    private void setOnClickListeners(){
        changeportrait = (LinearLayout) findViewById(R.id.change_portrait);
        portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserInformationActivity.this, UserPortraitActivity.class));
                overridePendingTransition(R.anim.portrait_in, R.anim.noanim);
                initPortrait();
            }
        });

        changeportrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.portrait_bottom_dialog, null);
                TextView tv_account = (TextView) view.findViewById(R.id.tv_take_photo);
                TextView tv_compare = (TextView) view.findViewById(R.id.tv_from_album);
                final Dialog mBottomSheetDialog = new Dialog(UserInformationActivity.this, R.style.MaterialDialogSheet);
                mBottomSheetDialog.setContentView(view);
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();
                tv_account.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(UserInformationActivity.this, "拍照", Toast.LENGTH_SHORT).show();
                        takePhotoUtil.takePhoto(TakePhotoUtil.Select_type.PICK_BY_TAKE, new TakePhotoUtil.SimpleTakePhotoListener() {
                            @Override
                            public void takeSuccess(TResult result) {
                                String s = result.getImage().getCompressPath();
                                System.out.println(s);
<<<<<<< HEAD
                                Bitmap bitmap = BitmapFactory.decodeFile(s);
                                portrait.setImageBitmap(bitmap);
=======
>>>>>>> ttr
                                updatePortrait(s);
                            }
                        });
                        mBottomSheetDialog.dismiss();
                    }
                });
                tv_compare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(UserInformationActivity.this, "从相册中选择", Toast.LENGTH_SHORT).show();
                        takePhotoUtil.takePhoto(TakePhotoUtil.Select_type.PICK_BY_SELECT, new TakePhotoUtil.SimpleTakePhotoListener() {
                            @Override
                            public void takeSuccess(TResult result) {
                                String s = result.getImage().getCompressPath();
                                System.out.println(s);
<<<<<<< HEAD
                                Bitmap bitmap = BitmapFactory.decodeFile(s);
                                portrait.setImageBitmap(bitmap);
=======
>>>>>>> ttr
                                updatePortrait(s);
                            }
                        });
                        mBottomSheetDialog.dismiss();
                    }
                });
            }
        });
=======
        takePhotoUtil = new TakePhotoUtil(this);
>>>>>>> Czj
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (useTakePhoto()) {
            takePhotoUtil.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (useTakePhoto()) {
            takePhotoUtil.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (useTakePhoto()) {
            takePhotoUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    protected boolean useTakePhoto() {
        return true;
    }


    /**
     * update user portrait at path
     * @param path file path
     */
    public void updatePortrait(String path) {
<<<<<<< HEAD
        Pattern pat = Pattern.compile("(portrait_)([0-9]+)(.jpg)");
        Matcher mat = pat.matcher(path);
        boolean rs = mat.find();
        Long millis = Long.parseLong(mat.group(2));
        informationManager.setPortraitPath(path);
        //Todo:上传Millis和图片到服务器
=======
        try {
            MyApp app = (MyApp) getApplicationContext();
            String userId = app.getUserId();
            new UploadTask(userId, path).execute();
            app.setAvatar(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Todo:上传图片到服务器 并返回图片对应的url
        //Todo:更新头像 更新本地储存的url

        Glide.with(this)
                .load(path)
                .into(portrait);
>>>>>>> ttr
    }


    /**
     * Initialize user portrait
     */
    public void initPortrait() {
<<<<<<< HEAD
        String s = informationManager.getPortraitPath();
        if (s != "") {
            Bitmap bitmap = BitmapFactory.decodeFile(s);
            portrait.setImageBitmap(bitmap);
=======
        String url = app.getAvatar();
        if (url != "") {
            Glide.with(this)
                    .load(url)
                    .into(portrait);
>>>>>>> ttr
        } else portrait.setImageResource(R.drawable.testportrait);
    }


    /**
     * update user portrait
     */
    private void changePortrait() {
        View view = getLayoutInflater().inflate(R.layout.portrait_bottom_dialog, null);
        TextView tv_account = (TextView) view.findViewById(R.id.tv_take_photo);
        TextView tv_compare = (TextView) view.findViewById(R.id.tv_from_album);
        final Dialog mBottomSheetDialog = new Dialog(UserInformationActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        tv_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserInformationActivity.this, "拍照", Toast.LENGTH_SHORT).show();
                takePhotoUtil.takePhoto(TakePhotoUtil.Select_type.PICK_BY_TAKE, new TakePhotoUtil.SimpleTakePhotoListener() {
                    @Override
                    public void takeSuccess(TResult result) {
                        String s = result.getImage().getCompressPath();
                        System.out.println(s);
                        updatePortrait(s);
                    }
                });
                mBottomSheetDialog.dismiss();
            }
        });
        tv_compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserInformationActivity.this, "从相册中选择", Toast.LENGTH_SHORT).show();
                takePhotoUtil.takePhoto(TakePhotoUtil.Select_type.PICK_BY_SELECT, new TakePhotoUtil.SimpleTakePhotoListener() {
                    @Override
                    public void takeSuccess(TResult result) {
                        String s = result.getImage().getCompressPath();
                        System.out.println(s);
                        updatePortrait(s);
                    }
                });
                mBottomSheetDialog.dismiss();
            }
        });
    }
}