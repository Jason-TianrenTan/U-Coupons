package com.example.administrator.ccoupons.AddCoupon;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ccoupons.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class AddCouponBaseActivity extends AppCompatActivity {


    private static final int[] imageID = {R.drawable.add_page1, R.drawable.add_page2, R.drawable.add_page3};

    public void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected View inflate_View;
    @BindView(R.id.tv_addcoupon_back)
    TextView tvAddcouponBack;
    @BindView(R.id.tv_addcoupon_next)
    TextView tvAddcouponNext;
    @BindView(R.id._toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_progress_img)
    ImageView progImg;
    @BindView(R.id.tv_addcoupon_step1)
    TextView tvStep1;
    @BindView(R.id.tv_addcoupon_step2)
    TextView tvStep2;
    @BindView(R.id.tv_addcoupon_step3)
    TextView tvStep3;
    @BindView(R.id.add_coupon_root)
    LinearLayout addCouponRoot;

    @OnClick(R.id.tv_addcoupon_next)
    public void click() {
        this.NextOnClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon_base);
        ButterKnife.bind(this);

        initToolbar();
        initViews();
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    public abstract void NextOnClick();


    public void inflateView(int id) {
        inflate_View = LayoutInflater.from(this).inflate(id, null);
        LinearLayout rootView = (LinearLayout) findViewById(R.id.add_coupon_root);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        inflate_View.setLayoutParams(params);
        rootView.addView(inflate_View);
    }


    public abstract void initViews();


    public void setTopImage(int index) {
        progImg.setImageDrawable(ContextCompat.getDrawable(this, imageID[index]));
        TextView[] textViews = {tvStep1, tvStep2, tvStep3};
        for (int i=0;i<3;i++) {
            textViews[i].setTextColor(ContextCompat.getColor(this, R.color.step_light));
            if (i == index)
                textViews[i].setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }

}
