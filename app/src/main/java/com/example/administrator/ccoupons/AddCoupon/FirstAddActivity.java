package com.example.administrator.ccoupons.AddCoupon;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.R;

public class FirstAddActivity extends AddCouponBaseActivity {


    public static int REQUEST_CATEGORY = 6;

    EditText categoryEditText,
            productEditText, brandEditText;
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
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.arrow);
        drawable.setBounds(0, 0, 40, 40);
        categoryEditText.setCompoundDrawables(null, null, drawable, null);
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
        System.out.println("init views finished");

    }

    @Override
    public void NextOnClick() {
        String cat = categoryEditText.getText().toString(),
                pro = productEditText.getText().toString(),
                brand = brandEditText.getText().toString();
        if (cat.length() * pro.length() * brand.length() == 0)
            makeToast("请填完完整信息！");
        else {
            Intent intent = new Intent(FirstAddActivity.this, SecondAddActivity.class);
            intent.putExtra("category", cat);
            intent.putExtra("product", pro);
            intent.putExtra("brand", brand);
            startActivity(intent);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CATEGORY) {
            categoryEditText.setText(GlobalConfig.Categories.nameList[resultCode]);
        }
    }

}
