package com.example.administrator.ccoupons.User;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Gender;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.ImageLruCache;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.DataBase.UserInfoManager;
import com.example.administrator.ccoupons.Tools.ImageManager;
import com.example.administrator.ccoupons.Tools.SlideBackActivity;
import com.example.administrator.ccoupons.Tools.TakePhotoUtil;
import com.example.administrator.ccoupons.Tools.XCRoundImageView;
import com.jph.takephoto.model.TResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mob.MobSDK.getContext;


public class UserInformationActivity extends SlideBackActivity {
    private TextView name;
    private TextView sex;
    private XCRoundImageView portrait;
    private TakePhotoUtil takePhotoUtil;
    private Toolbar toolbar;
    private LinearLayout changeportrait;
    private MyApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        initView();
        if (useTakePhoto()) {
            takePhotoUtil.onCreate(savedInstanceState);
        }

        initPortrait();
        //portrait.setImageResource(DataHolder.User.portraitId);

        setOnClickListeners();
    }

    private void initView() {
        name = (TextView) findViewById(R.id.user_name);
        sex = (TextView) findViewById(R.id.user_sex);
        portrait = (XCRoundImageView) findViewById(R.id.uinf_portrait);
        toolbar = (Toolbar) findViewById(R.id.uinf_toolbar);
        changeportrait = (LinearLayout) findViewById(R.id.change_portrait);
        app = (MyApp) this.getApplicationContext();
        takePhotoUtil = new TakePhotoUtil(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name.setText(app.getNickname());
/*
        if (app.getGender() == Gender.MALE)
            sex.setText("男");
        else
*/
        sex.setText("无性别");
    }

    private void setOnClickListeners() {
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
        });
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

    public void updatePortrait(String path) {
        //Todo:上传图片到服务器 并返回图片对应的url
        //Todo:更新头像  更新本地储存的url

        //ImageManager.GlideImage(url, portrait, getContext());
    }

    public void initPortrait() {
        String url = app.getAvatar();
        if (url != "") {
            ImageManager.GlideImage(url, portrait, getContext());
        } else portrait.setImageResource(R.drawable.testportrait);
    }
}