package com.example.administrator.ccoupons.AddCoupon;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.Data.DataHolder;
import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.MyApp;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Tools.ImageManager;

import org.json.JSONException;
import org.json.JSONObject;

public class AddCouponActivity extends AppCompatActivity {

    private Coupon coupon;
    private TextView couponEvalText, couponNameText, couponDiscountText, couponBrandText, couponCatText,
            couponConstraintsText;
    private EditText couponListPriceText;
    private ImageView couponImg;
    //for test
    Coupon tcoupon = new Coupon();
    String result;

    private void setCoupon() {
        tcoupon.setEvaluatePrice(2314);
        tcoupon.setBrandName("KFC");
        tcoupon.setName("JI");
        tcoupon.setCategory(DataHolder.Categories.nameList[0]);
        tcoupon.setDiscount("30");
        tcoupon.setConstraints(new String[] {"fasfhkjas","dsjfhaosidig"});
        result = tcoupon.generateJSON("12345").toString();
        System.out.println(result);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon);
        bindViews();
        getCouponInfo();
        if (coupon != null) {
            couponEvalText.setText(coupon.getEvaluatePrice() + "");
            //TODO:如果一定要添加图片的话 请修改
            if (coupon.getImgURL()!= null && !coupon.getImgURL().equals("")) {
                ImageManager.GlideImage(coupon.getImgURL(), couponImg, getApplicationContext());
            }
            couponNameText.setText(coupon.getName());
            couponBrandText.setText(coupon.getBrandName());
            couponDiscountText.setText(coupon.getDiscount());
            couponCatText.setText(coupon.getCategory());
            StringBuilder sb = new StringBuilder("");
            String[] constraints = coupon.getConstraints();
            for (int i = 0; i < constraints.length; i++) {
                sb.append((i + 1) + ". " + constraints[i] + "\n");
            }
            couponConstraintsText.setText(sb.toString());
        }
    }

    private void getCouponInfo() {
        //coupon = (Coupon) getIntent().getSerializableExtra("coupon");
        setCoupon();
        //coupon = Coupon.decodeFromQRJSON(result);
        coupon = Coupon.decodeFromQRJSON(result);
        //coupon = new Coupon();
    }

    private void bindViews() {
        couponEvalText = (TextView) findViewById(R.id.textview_coupon_eval);
        couponListPriceText = (EditText) findViewById(R.id.edittext_coupon_listprice);
        couponImg = (ImageView) findViewById(R.id.purchase_coupon_img);
        couponNameText = (TextView) findViewById(R.id.purchase_coupon_name_text);
        couponDiscountText = (TextView) findViewById(R.id.coupon_discount_text);
        couponBrandText = (TextView) findViewById(R.id.coupon_brand_textview);
        couponCatText = (TextView) findViewById(R.id.coupon_cat_textview);
        couponConstraintsText = (TextView) findViewById(R.id.purchase_constraints_text);
    }
}
