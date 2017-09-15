package com.example.administrator.ccoupons.AddCoupon;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.ccoupons.CustomEditText.ClearableEditText;
import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.TakePhotoUtil;
import com.jph.takephoto.model.TResult;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FillFormActivity extends AppCompatActivity {


    public static int REQUEST_CATEGORY = 6;
    public static int REQUEST_DATE = 999;
    @BindView(R.id._toolbar)
    Toolbar toolbar;
    @BindView(R.id.coupon_picture)
    ImageView couponPicture;
    @BindView(R.id.form_product_edittext)
    ClearableEditText productNameText;
    @BindView(R.id.form_product_inputlayout)
    TextInputLayout productInputLayout;
    @BindView(R.id.form_brand_edittext)
    ClearableEditText brandNameText;
    @BindView(R.id.form_brand_inputlayout)
    TextInputLayout brandInputLayout;
    @BindView(R.id.form_category_edittext)
    EditText categoryEditText;
    @BindView(R.id.form_discount_edittext)
    ClearableEditText discountText;
    @BindView(R.id.form_discount_inputlayout)
    TextInputLayout discountInputLayout;
    @BindView(R.id.form_expire_edittext)
    EditText expireText;
    @BindView(R.id.form_expire_inputlayout)
    TextInputLayout expireInputLayout;
    @BindView(R.id.form_scrollview)
    NestedScrollView scrollview;

    @OnClick(R.id.coupon_add_picture)
    public void onClick() {
        View view = getLayoutInflater().inflate(R.layout.portrait_bottom_dialog, null);
        TextView tv_account = (TextView) view.findViewById(R.id.tv_take_photo);
        TextView tv_compare = (TextView) view.findViewById(R.id.tv_from_album);
        final Dialog mBottomSheetDialog = new Dialog(FillFormActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        tv_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FillFormActivity.this, "拍照", Toast.LENGTH_SHORT).show();
                takePhotoUtil.takePhoto(TakePhotoUtil.Select_type.PICK_BY_TAKE_NOT_CROP, new TakePhotoUtil.SimpleTakePhotoListener() {
                    @Override
                    public void takeSuccess(TResult result) {
                        path = result.getImage().getCompressPath();
                        System.out.println("success:" + path);
                        System.out.println("success");
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
                Toast.makeText(FillFormActivity.this, "从相册中选择", Toast.LENGTH_SHORT).show();
                takePhotoUtil.takePhoto(TakePhotoUtil.Select_type.PICK_BY_SELECT_NOT_CROP, new TakePhotoUtil.SimpleTakePhotoListener() {
                    @Override
                    public void takeSuccess(TResult result) {
                        path = result.getImage().getCompressPath();
                        System.out.println("success:" + path);
                        System.out.println("success");
                        hasImage = true;
                        updatePic();
                    }
                });
                mBottomSheetDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.form_category_edittext)
    public void onClick1() {
        Intent intent = new Intent(FillFormActivity.this, ChooseCategoryActivity.class);
        //TODO: intialize intent
        startActivityForResult(intent, REQUEST_CATEGORY);
    }

    @OnClick(R.id.form_expire_edittext)
    public void onClick2() {
        Intent intent = new Intent(FillFormActivity.this, SelectDateActivity.class);
        startActivityForResult(intent, REQUEST_DATE);
    }

    @OnClick(R.id.form_next_button)
    public void onClick3() {
        valid = true;
        Intent intent = new Intent(FillFormActivity.this, AddConstraintsActivity.class);
        Coupon coupon = new Coupon();
        String productName = productNameText.getText().toString(),
                brandName = brandNameText.getText().toString(),
                discount = discountText.getText().toString(),
                category = categoryEditText.getText().toString(),
                expireDate = expireText.getText().toString();

        if (!hasImage) {
            valid = false;
            Toast.makeText(getApplicationContext(), "必须上传一张优惠券的图片!", Toast.LENGTH_SHORT).show();
        }

        if (productName.length() == 0) {
            valid = false;
            productInputLayout.setError("请输入商品名");
        }
        if (brandName.length() == 0) {
            valid = false;
            brandInputLayout.setError("请输入品牌名");
        }
        if (discount.length() == 0) {
            valid = false;
            discountInputLayout.setError("请输入优惠详情");
        }
        if (category.length() == 0) {
            valid = false;
            Toast.makeText(getApplicationContext(), "请选择种类", Toast.LENGTH_SHORT).show();
        }
        if (expireDate.length() == 0) {
            valid = false;
            expireInputLayout.setError("请输添加过期时间");
        }

        if (valid) {
            coupon.setProduct(productName);
            coupon.setPic(path);
            coupon.setExpiredtime(expireDate);
            coupon.setBrandName(brandName);
            coupon.setDiscount(discount);
            for (int i = 0; i < GlobalConfig.Categories.nameList.length; i++) {
                if (category.equals(GlobalConfig.Categories.nameList[i]))
                    coupon.setCategory((i + 1) + "");
            }
            intent.putExtra("coupon", coupon);
            startActivity(intent);
        }

    }

    private boolean hasImage = false;
    private TakePhotoUtil takePhotoUtil;
    private String path = "";
    private boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_form);
        ButterKnife.bind(this);

        takePhotoUtil = new TakePhotoUtil(this);
        takePhotoUtil.onCreate(savedInstanceState);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.arrow);
        drawable.setBounds(0, 0, 40, 40);
        categoryEditText.setCompoundDrawables(null, null, drawable, null);
        categoryEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Intent intent = new Intent(FillFormActivity.this, ChooseCategoryActivity.class);
                    //TODO: intialize intent
                    startActivityForResult(intent, REQUEST_CATEGORY);
                }
            }
        });

        expireText.setCompoundDrawables(null, null, drawable, null);
        expireText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Intent intent = new Intent(FillFormActivity.this, SelectDateActivity.class);
                    //TODO: intialize intent
                    startActivityForResult(intent, REQUEST_DATE);
                }
            }
        });
    }


    private void updatePic() {
        Glide.with(this)
                .load(path)
                .into(couponPicture);
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
            System.out.println("选择了类别" + resultCode);
            categoryEditText.setText(GlobalConfig.Categories.nameList[resultCode]);
        } else if (requestCode == REQUEST_DATE) {
            String date = data.getStringExtra("date");
            System.out.println("Chose date at " + date);
            expireText.setText(date);
        } else {
            takePhotoUtil.onActivityResult(requestCode, resultCode, data);
        }
    }
}
