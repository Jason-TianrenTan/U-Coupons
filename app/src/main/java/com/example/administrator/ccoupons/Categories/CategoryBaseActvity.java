package com.example.administrator.ccoupons.Categories;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.administrator.ccoupons.R;

/**
 * Created by Administrator on 2017/7/30 0030.
 */

public class CategoryBaseActvity extends AppCompatActivity {


    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView(int resId) {
        super.setContentView(resId);
        initViews();
    }


    //init views
    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.common_coupons_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /**
     *
     * @param title
     */
    public void setTitle(String title) {

    }
}
