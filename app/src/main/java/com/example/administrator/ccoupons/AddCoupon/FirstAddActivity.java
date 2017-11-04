package com.example.administrator.ccoupons.AddCoupon;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.R;

public class FirstAddActivity extends AddCouponBaseActivity {


    public static int REQUEST_CATEGORY = 6;
    public static int REQUEST_DATE = 999;

    EditText categoryEditText,
            productEditText, brandEditText,expireEditText;
    LinearLayout addImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                //TODO:select photo
            }
        });

    }

    @Override
    public void NextOnClick() {
        String cat = categoryEditText.getText().toString(),
                pro = productEditText.getText().toString(),
                brand = brandEditText.getText().toString(),
                exp = expireEditText.getText().toString();
        if (cat.length() * pro.length() * brand.length() * exp.length() == 0)
            makeToast("请填完完整信息！");
        else {
            Intent intent = new Intent(FirstAddActivity.this, SecondAddActivity.class);
            intent.putExtra("category", cat);
            intent.putExtra("product", pro);
            intent.putExtra("brand", brand);
            intent.putExtra("expire", exp);
            startActivity(intent);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CATEGORY) {
            categoryEditText.setText(GlobalConfig.Categories.nameList[resultCode]);
        } else if (requestCode == REQUEST_DATE) {
            String date = data.getStringExtra("date");
            expireEditText.setText(date);
        }
    }

}
