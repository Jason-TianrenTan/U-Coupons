package com.example.administrator.ccoupons.AddCoupon;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.TakePhotoUtil;
import com.jph.takephoto.model.TResult;

public class FirstAddActivity extends AddCouponBaseActivity {



    private boolean hasImage = false;
    private TakePhotoUtil takePhotoUtil;
    private String path = "null";

    public static int REQUEST_CATEGORY = 6;
    public static int REQUEST_DATE = 999;

    EditText categoryEditText,
            productEditText, brandEditText,expireEditText;
    LinearLayout addImage;
    ImageView couponAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takePhotoUtil = new TakePhotoUtil(this);
        takePhotoUtil.onCreate(savedInstanceState);

    }


    @Override
    public void initViews() {
        super.setTopImage(0);
        super.inflateView(R.layout.first_add_view);

        categoryEditText = inflate_View.findViewById(R.id.et_constraint_category);
        productEditText = inflate_View.findViewById(R.id.et_constraint_productname);
        brandEditText = inflate_View.findViewById(R.id.et_constraint_brandname);
        expireEditText = inflate_View.findViewById(R.id.et_constraint_expiredate);
        addImage  = (LinearLayout) findViewById(R.id.btn_select_image);
        couponAvatar = inflate_View.findViewById(R.id.add_coupon_avatar);

        Drawable drawable1 = ContextCompat.getDrawable(this, R.drawable.arrow);
        drawable1.setBounds(0, 0, 40, 40);
        categoryEditText.setCompoundDrawables(null, null, drawable1, null);
        categoryEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Intent intent = new Intent(FirstAddActivity.this, ChooseCategoryActivity.class);
                    //TODO: intialize intent
                    startActivityForResult(intent, REQUEST_CATEGORY);
                }
            }
        });

        Drawable drawable2 = ContextCompat.getDrawable(this, R.drawable.ic_calendar);
        drawable2.setBounds(0, 0, 40, 40);
        expireEditText.setCompoundDrawables(null, null, drawable2, null);
        expireEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Intent intent = new Intent(FirstAddActivity.this, SelectDateActivity.class);
                    //TODO: intialize intent
                    startActivityForResult(intent, REQUEST_DATE);
                }
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.portrait_bottom_dialog, null);
                TextView tv_account = (TextView) view.findViewById(R.id.tv_take_photo);
                TextView tv_compare = (TextView) view.findViewById(R.id.tv_from_album);
                final Dialog mBottomSheetDialog = new Dialog(FirstAddActivity.this, R.style.MaterialDialogSheet);
                mBottomSheetDialog.setContentView(view);
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
                mBottomSheetDialog.show();
                tv_account.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(FirstAddActivity.this, "拍照", Toast.LENGTH_SHORT).show();
                        takePhotoUtil.takePhoto(TakePhotoUtil.Select_type.PICK_BY_TAKE_NOT_CROP, new TakePhotoUtil.SimpleTakePhotoListener() {
                            @Override
                            public void takeSuccess(TResult result) {
                                path = result.getImage().getCompressPath();
                                hasImage = true;
                                updatePic();
                            }
                        });
                        mBottomSheetDialog.dismiss();
                    }
                });
                tv_compare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(FirstAddActivity.this, "从相册中选择", Toast.LENGTH_SHORT).show();
                        takePhotoUtil.takePhoto(TakePhotoUtil.Select_type.PICK_BY_SELECT_NOT_CROP, new TakePhotoUtil.SimpleTakePhotoListener() {
                            @Override
                            public void takeSuccess(TResult result) {
                                path = result.getImage().getCompressPath();
                                hasImage = true;
                                updatePic();
                            }
                        });
                        mBottomSheetDialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public void NextOnClick() {
        String cat = categoryEditText.getText().toString(),
                pro = productEditText.getText().toString(),
                brand = brandEditText.getText().toString(),
                exp = expireEditText.getText().toString();
        if (cat.length() * pro.length() * brand.length() * exp.length() == 0 || path.equals("null"))
            makeToast("请填完完整信息！");
        else {
            Intent intent = new Intent(FirstAddActivity.this, SecondAddActivity.class);
            intent.putExtra("category", cat);
            intent.putExtra("product", pro);
            intent.putExtra("brand", brand);
            intent.putExtra("expire", exp);
            intent.putExtra("picture", path);
            startActivity(intent);
        }

    }


    //load picture into imageview
    private void updatePic() {
        Glide.with(this)
                .load(path)
                .into(couponAvatar);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        takePhotoUtil.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        takePhotoUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CATEGORY) {
            categoryEditText.setText(GlobalConfig.Categories.nameList[resultCode]);
        } else if (requestCode == REQUEST_DATE) {
            String date = data.getStringExtra("date");
            expireEditText.setText(date);
        } else {
            takePhotoUtil.onActivityResult(requestCode, resultCode, data);
        }
    }

}
