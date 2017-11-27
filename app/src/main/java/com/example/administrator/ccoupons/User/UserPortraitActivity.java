package com.example.administrator.ccoupons.User;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
=======
import android.os.Bundle;
>>>>>>> ttr
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.DataBase.LoginInformationManager;
import com.example.administrator.ccoupons.Tools.TakePhotoUtil;
import com.jph.takephoto.model.TResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

=======
import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Connections.UploadTask;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.TakePhotoUtil;
import com.jph.takephoto.model.TResult;

<<<<<<< HEAD
>>>>>>> ttr
=======
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

>>>>>>> Czj
public class UserPortraitActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private TakePhotoUtil takePhotoUtil;
<<<<<<< HEAD
    private LoginInformationManager informationManager;
    private ImageView portrait;
    private LinearLayout bg;
=======
    @BindView(R.id.user_portrait_view)
    ImageView portrait;
    @BindView(R.id.portrait_bg)
    LinearLayout bg;

    @OnClick({R.id.portrait_bg})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.portrait_bg:
                finish();
                overridePendingTransition(R.anim.noanim, R.anim.portrait_out);//Todo:动画需要调整
        }
    }
>>>>>>> Czj

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_portrait);
        takePhotoUtil = new TakePhotoUtil(this);
        if (useTakePhoto()) {
            takePhotoUtil.onCreate(savedInstanceState);
        }
        setOnLongClickListeners();
        initPortrait();
    }


    /**
     * set OnLongClickListener
     */
    private void setOnLongClickListeners() {

        bg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.portrait_bottom_dialog, null);
                TextView tv_account = (TextView) view.findViewById(R.id.tv_take_photo);
                TextView tv_compare = (TextView) view.findViewById(R.id.tv_from_album);
                final Dialog mBottomSheetDialog = new Dialog(UserPortraitActivity.this, R.style.MaterialDialogSheet);
                mBottomSheetDialog.setContentView(view);
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();
                tv_account.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(UserPortraitActivity.this, "拍照", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(UserPortraitActivity.this, "从相册中选择", Toast.LENGTH_SHORT).show();
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
                return true;
            }
        });
    }

<<<<<<< HEAD
    private void initView() {
        informationManager = new LoginInformationManager(this);
        portrait = (ImageView) findViewById(R.id.user_portrait_view);
        //portrait.setImageResource(DataHolder.User.portraitId);
        bg = (LinearLayout) findViewById(R.id.portrait_bg);
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

<<<<<<< HEAD
    public void updatePortrait(String path) {
        Pattern pat = Pattern.compile("(portrait_)([0-9]+)(.jpg)");
        Matcher mat = pat.matcher(path);
        boolean rs = mat.find();
        Long millis = Long.parseLong(mat.group(2));
        informationManager.setPortraitPath(path);
        //上传Millis和图片到服务器
    }

    public void initPortrait() {
        String s = informationManager.getPortraitPath();
        if (s != "") {
            Bitmap bitmap = BitmapFactory.decodeFile(s);
            portrait.setImageBitmap(bitmap);
=======
=======

    /**
     * update user portrait
     * @param path
     */
>>>>>>> Czj
    public void updatePortrait(final String path) {
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


    /**
     * init user portrait
     */
    public void initPortrait() {
        MyApp app = (MyApp) this.getApplicationContext();
        String url = app.getAvatar();
        if (url != "") {
            Glide.with(this)
                    .load(url)
                    .into(portrait);
>>>>>>> ttr
        } else portrait.setImageResource(R.drawable.testportrait);
    }


    /**
     * init the camera
     * @return
     */
    protected boolean useTakePhoto() {
        return true;
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
}