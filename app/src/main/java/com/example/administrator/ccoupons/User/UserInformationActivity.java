package com.example.administrator.ccoupons.User;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Connections.UploadTask;
import com.example.administrator.ccoupons.Gender;
import com.example.administrator.ccoupons.Main.ResetPasswordActivity;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.SlideBackActivity;
import com.example.administrator.ccoupons.Tools.TakePhotoUtil;
import com.jph.takephoto.model.TResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInformationActivity extends SlideBackActivity {
    private TakePhotoUtil takePhotoUtil;
    private MyApp app;

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
        ButterKnife.bind(this);
        initToolbar();
        initData();
        initPortrait();
        //portrait.setImageResource(DataHolder.User.portraitId);
        takePhotoUtil = new TakePhotoUtil(this);
        if (useTakePhoto()) {
            takePhotoUtil.onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        initData();
        initPortrait();
        super.onStart();
    }

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
    }

    private void initData() {
        app = (MyApp) this.getApplicationContext();
        name.setText(app.getNickname());
        if (app.getGender() == Gender.MALE)
            sex.setText("男");
        else sex.setText("女");
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

    private void updatePortrait(String path) {
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
    }

    private void initPortrait() {
        String url = app.getAvatar();
        if (url != "") {
            Glide.with(this)
                    .load(url)
                    .into(portrait);
        } else portrait.setImageResource(R.drawable.testportrait);
    }

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