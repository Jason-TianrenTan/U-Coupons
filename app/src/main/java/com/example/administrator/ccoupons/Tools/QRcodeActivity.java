package com.example.administrator.ccoupons.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

import com.example.administrator.ccoupons.AddCoupon.AddCouponActivity;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.jph.takephoto.model.TResult;
import com.liji.takephoto.MainActivity;

import java.lang.ref.WeakReference;

public class QRcodeActivity extends AppCompatActivity implements QRCodeView.Delegate {
    private static final String TAG = QRcodeActivity.class.getSimpleName();
    private boolean light = false;

    private Button flashlight;
    private Toolbar toolbar;
    private QRCodeView mQRCodeView;
    private LinearLayout gallery;
    private TakePhotoUtil takePhotoUtil;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        bindViews();
        takePhotoUtil = new TakePhotoUtil(this);
        if (useTakePhoto()) {
            takePhotoUtil.onCreate(savedInstanceState);
        }
        setOnClickListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//      mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        System.out.println("result:" + result);
        vibrate();
        //返回结果
        Intent intent = new Intent(QRcodeActivity.this, AddCouponActivity.class);
        //TEST:intent.putExtra("coupon", new Coupon(result));
        startActivity(intent);
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    private void bindViews() {
        flashlight = (Button) findViewById(R.id.flashlight);
        toolbar = (Toolbar) findViewById(R.id.qr_toolbar);
        gallery = (LinearLayout) findViewById(R.id.qr_gallery);
        setSupportActionBar(toolbar);
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setOnClickListeners() {
        flashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!light) {
                    mQRCodeView.openFlashlight();
                    flashlight.setBackgroundResource(R.drawable.flashlight_on);
                    light = true;
                } else {
                    mQRCodeView.closeFlashlight();
                    flashlight.setBackgroundResource(R.drawable.flashlight_off);
                    light = false;
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QRcodeActivity.this, "从相册中选择", Toast.LENGTH_SHORT).show();
                takePhotoUtil.takePhoto(TakePhotoUtil.Select_type.PICK_BY_SELECT_NOT_CROP, new TakePhotoUtil.SimpleTakePhotoListener() {
                    @Override
                    public void takeSuccess(TResult result) {
                        final String path = result.getImage().getCompressPath();
                        System.out.println(path);
                        new MyAsyncTask(QRcodeActivity.this, path).execute();
                    }
                });
            }
        });
    }

    static class MyAsyncTask extends AsyncTask<Void, Void, String> {

        private WeakReference<Context> weakReference;
        String picturePath;

        public MyAsyncTask(Context context, String picturePath) {
            weakReference = new WeakReference<>(context);
            this.picturePath = picturePath;
        }

        @Override
        protected String doInBackground(Void... params) {
            return QRCodeDecoder.syncDecodeQRCode(picturePath);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            QRcodeActivity activity = (QRcodeActivity) weakReference.get();
            if (TextUtils.isEmpty(result)) {
                Toast.makeText(activity, "未发现二维码", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println("result:" + result);
                Intent intent = new Intent(activity, AddCouponActivity.class);
                //TEST:intent.putExtra("coupon", new Coupon(result));
                activity.startActivity(intent);
                activity.finish();
            }
        }
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

    /*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            mQRCodeView.showScanRect();

            if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
                final String picturePath = BGAPhotoPickerActivity.getSelectedImages(data).get(0);

                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        return QRCodeDecoder.syncDecodeQRCode(picturePath);
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        if (TextUtils.isEmpty(result)) {
                            Toast.makeText(QRcodeActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(QRcodeActivity.this, result, Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();
            }
        }
    */
    protected boolean useTakePhoto() {
        return true;
    }
}

